package info.deltaindices.volumecamera;
import android.content.*;import android.graphics.Color;import android.os.*;import android.view.*;import android.widget.*;import androidx.appcompat.app.AppCompatActivity;

public final class CaptureActivity extends AppCompatActivity {
 static final String ACTION_TOGGLE="info.deltaindices.volumecamera.TOGGLE";private Button toggle;private TextView count;
 private final BroadcastReceiver updates=new BroadcastReceiver(){@Override public void onReceive(Context c,Intent i){render(i.getBooleanExtra("active",false),i.getIntExtra("count",0));}};
 @Override protected void onCreate(Bundle b){super.onCreate(b);if(Build.VERSION.SDK_INT>=27){setShowWhenLocked(true);setTurnScreenOn(true);}else getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);setContentView(build());if(ACTION_TOGGLE.equals(getIntent().getAction()))toggle();else refresh();}
 @Override protected void onNewIntent(Intent i){super.onNewIntent(i);setIntent(i);if(ACTION_TOGGLE.equals(i.getAction()))toggle();}
 @Override protected void onStart(){super.onStart();ContextCompatHelper.register(this,updates,new IntentFilter(CaptureService.ACTION_COUNT));refresh();}
 @Override protected void onStop(){unregisterReceiver(updates);super.onStop();}
 private View build(){LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setGravity(Gravity.CENTER);root.setBackgroundColor(Color.BLACK);count=new TextView(this);count.setTextSize(56);count.setTextColor(Color.WHITE);count.setGravity(Gravity.CENTER);root.addView(count,new LinearLayout.LayoutParams(-1,0,1));toggle=new Button(this);toggle.setTextSize(28);toggle.setOnClickListener(v->toggle());root.addView(toggle,new LinearLayout.LayoutParams(-1,180));return root;}
 private void toggle(){boolean active=Settings.prefs(this).getBoolean(Settings.CAPTURE_ACTIVE,false);Intent service=new Intent(this,CaptureService.class).setAction(active?CaptureService.ACTION_STOP:CaptureService.ACTION_START);if(active)startService(service);else startForegroundService(service);render(!active,active?Settings.prefs(this).getInt(Settings.SESSION_COUNT,0):0);}
 private void refresh(){render(Settings.prefs(this).getBoolean(Settings.CAPTURE_ACTIVE,false),Settings.prefs(this).getInt(Settings.SESSION_COUNT,0));}
 private void render(boolean active,int value){count.setText(String.valueOf(value));toggle.setText(active?"STOP":"START");toggle.setTextColor(Color.WHITE);toggle.setBackgroundColor(active?Color.rgb(190,35,35):Color.rgb(20,150,70));}
}
