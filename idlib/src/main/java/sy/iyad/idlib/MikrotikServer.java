package sy.iyad.idlib;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import sy.iyad.idlib.Ready.Api;
import sy.iyad.idlib.Ready.PreReady.Commander;
import sy.iyad.idlib.Ready.PreReady.Connection;
import sy.iyad.idlib.Ready.PreReady.OnConnectListener;
import sy.iyad.idlib.Ready.PreReady.OnExecuteListener;

public class MikrotikServer {
    public static Api api;
    public String helloWorld(){
      return "hello World";
    }
    public static int defaultPort=0;
    public static List<Map<String, String>> mapList;
    public static Exception exception;
    public OnConnectListener onConnectListener;
    public OnExecuteListener onExecuteListener;
    public void connect(String ip,String username,String password){
        String[] strings = new String[]{ip,username,password};
        try {
            api = new Connection().execute(strings).get();
        } catch (ExecutionException e) {
            exception = e;
        } catch (InterruptedException e) {
           exception = e;
        }
    }
    public void execute(String cmd){
        try {
            mapList = new Commander(cmd).execute(api).get();
        } catch (ExecutionException e) {
            exception = e;
        } catch (InterruptedException e) {
          exception = e;
        }
    }
    public void execute(Api readyApi,String cmd){
        try {
            mapList = new Commander(cmd).execute(readyApi).get();
        } catch (ExecutionException e) {
            exception = e;
        } catch (InterruptedException e) {
           exception = e;
        }
    }

    public void setOnConnectListener(OnConnectListener listener) {
        this.onConnectListener = listener;
        if (api != null)
            listener.onConnectionSuccess(api);
        else
            listener.onConnectionFailed(exception);
    }

    public void setOnExecuteListener(OnExecuteListener listener) {
        this.onExecuteListener = listener;
        if (mapList != null)
            listener.onExecutionSuccess(mapList);
        else
            listener.onExecutionFailed(exception);
    }
}
