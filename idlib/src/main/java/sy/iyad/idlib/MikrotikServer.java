package sy.iyad.idlib;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import sy.iyad.idlib.Ready.Api;
import sy.iyad.idlib.Ready.PreReady.Executor;
import sy.iyad.idlib.Ready.PreReady.Connector;
import sy.iyad.idlib.Ready.PreReady.OnConnectListener;
import sy.iyad.idlib.Ready.PreReady.OnExecuteListener;
@SuppressWarnings("unused")
public class MikrotikServer {
    private static Api api;
    private static List<Map<String, String>> mapList;
    private static Exception internalException;
    private static Exception externalException;
    private  OnConnectListener onConnectListener;
    private OnExecuteListener onExecuteListener;
    public void connect(String ip,String username,String password,int port,int timeout){
        String[] strings = new String[]{ip,username,password};
        try {
            api = new Connector(port,timeout).execute(strings).get();
        } catch (ExecutionException e) {
            internalException = e;
        } catch (InterruptedException e) {
           internalException = e;
        }
    }
    public void connect(String ip,String admin,String password,int port){
        connect(ip,admin,password,port,3000);
    }
    public void connect(String ip,String admin,String password){
      connect(ip,admin,password,8728);
    }
    public void execute(String cmd){
       execute(api,cmd);
    }
    public void execute(Api readyApi,String cmd){
        try {
            mapList = new Executor(cmd).execute(readyApi).get();
        } catch (ExecutionException e) {
            internalException = e;
        } catch (InterruptedException e) {
           internalException = e;
        }
    }

    public void setOnConnectListener(OnConnectListener listener) {
        onConnectListener = listener;
        if (api != null)
            listener.onConnectionSuccess(api);
        else if (internalException!=null)
            listener.onConnectionFailed(internalException);
        else if (Connector.externalExceptionFromConnector!= null)
            listener.onConnectionFailed(Connector.externalExceptionFromConnector);
        else
            listener.onConnectionFailed(new Exception("unknown Error in Connector syriaLink"));
    }

    public void setOnExecuteListener(OnExecuteListener listener) {
        this.onExecuteListener = listener;
        if (mapList != null)
            listener.onExecutionSuccess(mapList);
        else if (internalException!=null)
            listener.onExecutionFailed(internalException);
        else if (Executor.externalExceptionFromExecutor!=null)
            listener.onExecutionFailed(Executor.externalExceptionFromExecutor);
        else
            listener.onExecutionFailed(new Exception("unknown Error in Executor StriaLink"));


    }
}
