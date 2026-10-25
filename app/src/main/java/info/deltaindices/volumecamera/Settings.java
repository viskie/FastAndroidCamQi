package info.deltaindices.volumecamera;
import android.content.*;
import android.net.Uri;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
final class Settings {
 static final String PREFS="volume_camera", ARMED="armed", INTERVAL="interval_seconds", PRESSES="presses", DRIVE_TOKEN="drive_token", DRIVE_FOLDER="drive_folder", DRIVE_FOLDER_ID="drive_folder_id", DRIVE_AUTH_REQUIRED="drive_auth_required", CAPTURE_ACTIVE="capture_active", SESSION_COUNT="session_count";
 static android.content.SharedPreferences prefs(Context c){return c.getSharedPreferences(PREFS,Context.MODE_PRIVATE);}
 static boolean armed(Context c){return prefs(c).getBoolean(ARMED,false);}
 static int interval(Context c){return Math.max(2,Math.min(3600,prefs(c).getInt(INTERVAL,5)));}
 static int presses(Context c){return prefs(c).getInt(PRESSES,3)==5?5:3;}
 static android.content.SharedPreferences securePrefs(Context c){try{MasterKey key=new MasterKey.Builder(c).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build();return EncryptedSharedPreferences.create(c,"volume_camera_secure",key,EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);}catch(Exception e){throw new IllegalStateException("Secure credential storage unavailable",e);}}
 static String driveToken(Context c){return securePrefs(c).getString(DRIVE_TOKEN,null);}
 static void setDriveToken(Context c,String token){securePrefs(c).edit().putString(DRIVE_TOKEN,token).apply();}
 static void clearDriveToken(Context c){securePrefs(c).edit().remove(DRIVE_TOKEN).apply();}
 static String driveFolder(Context c){return prefs(c).getString(DRIVE_FOLDER,"FastAndroidCamQi");}
 private Settings(){}
}
