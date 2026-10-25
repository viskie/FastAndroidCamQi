package info.deltaindices.volumecamera;
import android.content.*;import org.json.*;import java.io.*;import java.text.SimpleDateFormat;import java.util.*;
final class ErrorLog {
 private static final String KEY="error_journal";private static final int LIMIT=100;
 static synchronized void record(Context c,String source,Throwable error){StringWriter text=new StringWriter();error.printStackTrace(new PrintWriter(text));record(c,source,error.getClass().getSimpleName()+": "+String.valueOf(error.getMessage())+"\n"+text);}
 static synchronized void record(Context c,String source,String detail){JSONArray old=read(c),next=new JSONArray();JSONObject row=new JSONObject();try{String safe=detail==null?"No details":detail;row.put("at",System.currentTimeMillis());row.put("source",source);row.put("detail",safe.length()>3000?safe.substring(0,3000):safe);next.put(row);for(int i=0;i<Math.min(old.length(),LIMIT-1);i++)next.put(old.opt(i));}catch(JSONException ignored){}Settings.prefs(c).edit().putString(KEY,next.toString()).commit();}
 static synchronized JSONArray read(Context c){try{return new JSONArray(Settings.prefs(c).getString(KEY,"[]"));}catch(JSONException e){return new JSONArray();}}
 static synchronized String display(Context c){JSONArray rows=read(c);StringBuilder out=new StringBuilder();SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);for(int i=rows.length()-1;i>=0;i--){JSONObject row=rows.optJSONObject(i);if(row!=null)out.append(format.format(new Date(row.optLong("at")))).append(" · ").append(row.optString("source")).append('\n').append(row.optString("detail")).append("\n\n");}return out.toString();}
 static synchronized void clear(Context c){Settings.prefs(c).edit().remove(KEY).commit();}
 static int count(Context c){return read(c).length();}
 private ErrorLog(){}
}
