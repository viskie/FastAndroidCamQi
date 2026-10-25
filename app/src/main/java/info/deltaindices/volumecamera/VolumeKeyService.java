package info.deltaindices.volumecamera;
import android.accessibilityservice.AccessibilityService;import android.content.*;import android.view.KeyEvent;import android.view.accessibility.AccessibilityEvent;import java.util.ArrayDeque;
public final class VolumeKeyService extends AccessibilityService {
 private final ArrayDeque<Long> hits=new ArrayDeque<>();
 @Override protected boolean onKeyEvent(KeyEvent event){if(!Settings.armed(this)||event.getKeyCode()!=KeyEvent.KEYCODE_VOLUME_UP||event.getAction()!=KeyEvent.ACTION_DOWN||event.getRepeatCount()!=0)return false;long now=System.currentTimeMillis();hits.addLast(now);while(!hits.isEmpty()&&now-hits.peekFirst()>5000)hits.removeFirst();if(hits.size()>=Settings.presses(this)){hits.clear();Intent i=new Intent(this,CaptureActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP).setAction(CaptureActivity.ACTION_TOGGLE);startActivity(i);return true;}return false;}
 @Override public void onAccessibilityEvent(AccessibilityEvent event){}@Override public void onInterrupt(){}
}
