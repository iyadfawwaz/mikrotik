package sy.iyad.idlib.Ready.NewPreReady;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Helper {
    private static final Executor executor = new ThreadPoolExecutor(1,128
            ,1, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
    Handler handler = new Handler(Looper.getMainLooper());
    public <Iyad> void startHelper(Callable<Iyad> callable, OnHelperComplete<Iyad> listener){
        executor.execute(() -> {
            try {
                final Iyad result = callable.call();
                handler.post(() -> {
                    listener.onHelperSuccess(result);
                });
            } catch (Exception e) {
                handler.post(() -> {
                   listener.onHelperFailed(e);
                });
            }
        });
    }
}
