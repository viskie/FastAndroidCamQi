package info.deltaindices.volumecamera;
import android.app.*;import android.content.*;import android.os.IBinder;
public final class ArmedService extends Service {
 public static void sync(Context c){Intent i=new Intent(c,ArmedService.class);if(Settings.armed(c))c.startForegroundService(i);else c.stopService(i);}
 @Override public void onCreate(){super.onCreate();startForeground(Notifications.ARMED_ID,Notifications.build(this,"Volume trigger armed",Settings.presses(this)+" Volume Up presses within 5 seconds",true));}
 @Override public int onStartCommand(Intent i,int flags,int id){if(!Settings.armed(this)){stopSelf();return START_NOT_STICKY;}return START_STICKY;}
 @Override public IBinder onBind(Intent i){return null;}
}
