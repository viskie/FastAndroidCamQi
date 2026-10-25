package info.deltaindices.volumecamera;
import android.app.*;import android.content.*;import android.os.Build;
final class Notifications {
 static final String CHANNEL="volume_camera_status";static final int ARMED_ID=41,CAPTURE_ID=42;
 static void channel(Context c){if(Build.VERSION.SDK_INT>=26)c.getSystemService(NotificationManager.class).createNotificationChannel(new NotificationChannel(CHANNEL,"Volume Camera status",NotificationManager.IMPORTANCE_DEFAULT));}
 static Notification build(Context c,String title,String text,boolean ongoing){channel(c);Intent i=new Intent(c,MainActivity.class);PendingIntent p=PendingIntent.getActivity(c,1,i,PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);return new Notification.Builder(c,CHANNEL).setSmallIcon(android.R.drawable.ic_menu_camera).setContentTitle(title).setContentText(text).setContentIntent(p).setOngoing(ongoing).setOnlyAlertOnce(true).build();}
 private Notifications(){}
}
