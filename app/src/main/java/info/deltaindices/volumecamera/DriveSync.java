package info.deltaindices.volumecamera;
import android.content.*;import android.net.Uri;import androidx.work.*;import org.json.*;import java.util.concurrent.TimeUnit;
final class DriveSync {
 static final String QUEUE="drive_queue",TAKEN="pictures_taken",SYNCED="pictures_synced",FAILED="pictures_failed",LAST="drive_last_status";
 static synchronized void enqueue(Context c,Uri uri,String name){JSONArray queue=read(c);JSONObject item=new JSONObject();try{item.put("uri",uri.toString());item.put("name",name);item.put("attempts",0);queue.put(item);}catch(JSONException ignored){}Settings.prefs(c).edit().putString(QUEUE,queue.toString()).putInt(TAKEN,Settings.prefs(c).getInt(TAKEN,0)+1).apply();schedule(c);}
 static void schedule(Context c){Constraints constraints=new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();OneTimeWorkRequest work=new OneTimeWorkRequest.Builder(DriveUploadWorker.class).setConstraints(constraints).setBackoffCriteria(BackoffPolicy.EXPONENTIAL,10,TimeUnit.SECONDS).build();WorkManager.getInstance(c).enqueueUniqueWork("drive-photo-sync",ExistingWorkPolicy.APPEND_OR_REPLACE,work);}
 static synchronized JSONArray read(Context c){try{return new JSONArray(Settings.prefs(c).getString(QUEUE,"[]"));}catch(JSONException e){return new JSONArray();}}
 static synchronized JSONObject first(Context c){JSONArray q=read(c);return q.length()==0?null:q.optJSONObject(0);}
 static synchronized void success(Context c){JSONArray old=read(c),next=new JSONArray();for(int i=1;i<old.length();i++)next.put(old.opt(i));Settings.prefs(c).edit().putString(QUEUE,next.toString()).putInt(SYNCED,Settings.prefs(c).getInt(SYNCED,0)+1).putString(LAST,"Last upload successful").apply();}
 static synchronized void failed(Context c,String reason,boolean permanent){JSONArray q=read(c);if(q.length()>0){JSONObject item=q.optJSONObject(0);try{if(item!=null)item.put("attempts",item.optInt("attempts",0)+1);}catch(JSONException ignored){}}Settings.prefs(c).edit().putString(QUEUE,q.toString()).putInt(FAILED,Settings.prefs(c).getInt(FAILED,0)+1).putString(LAST,reason).apply();}
 static int pending(Context c){return read(c).length();}
 private DriveSync(){}
}
