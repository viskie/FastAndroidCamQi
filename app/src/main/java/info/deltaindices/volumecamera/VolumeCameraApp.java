package info.deltaindices.volumecamera;
import android.app.Application;
public final class VolumeCameraApp extends Application {
 @Override public void onCreate(){super.onCreate();Thread.UncaughtExceptionHandler previous=Thread.getDefaultUncaughtExceptionHandler();Thread.setDefaultUncaughtExceptionHandler((thread,error)->{try{ErrorLog.record(this,"uncaught/"+thread.getName(),error);}catch(Throwable ignored){}if(previous!=null)previous.uncaughtException(thread,error);});}
}
