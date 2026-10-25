package info.deltaindices.volumecamera;
import android.content.*;import android.os.Build;
final class ContextCompatHelper {static void register(Context c,BroadcastReceiver receiver,IntentFilter filter){if(Build.VERSION.SDK_INT>=33)c.registerReceiver(receiver,filter,Context.RECEIVER_NOT_EXPORTED);else c.registerReceiver(receiver,filter);}private ContextCompatHelper(){}}
