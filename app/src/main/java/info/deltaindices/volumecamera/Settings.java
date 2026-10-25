package info.deltaindices.volumecamera;
import android.content.*;
import android.net.Uri;
final class Settings {
 static final String PREFS="volume_camera", ARMED="armed", INTERVAL="interval_seconds", PRESSES="presses", DRIVE="drive_tree";
 static android.content.SharedPreferences prefs(Context c){return c.getSharedPreferences(PREFS,Context.MODE_PRIVATE);}
 static boolean armed(Context c){return prefs(c).getBoolean(ARMED,false);}
 static int interval(Context c){return Math.max(2,Math.min(3600,prefs(c).getInt(INTERVAL,5)));}
 static int presses(Context c){return prefs(c).getInt(PRESSES,3)==5?5:3;}
 static Uri drive(Context c){String raw=prefs(c).getString(DRIVE,null);return raw==null?null:Uri.parse(raw);}
 private Settings(){}
}
