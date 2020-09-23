package sy.iyad.idlib;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import sy.iyad.idlib.Ready.Api;
import sy.iyad.idlib.Ready.PreReady.ConnectEventListener;
import sy.iyad.idlib.Ready.PreReady.ExecuteEventListener;
import sy.iyad.idlib.Ready.PreReady.Executor;
import sy.iyad.idlib.Ready.PreReady.Connector;

public class MikrotikServer {

    public static final int DEFAULT_PORT=8728;
    public static final int DEFAULT_IMEOUT=3000;
    private static Api api;
    private static List<Map<String, String>> mapList;
    private static Exception internalException;
    private static Exception externalException;
   // private ConnectEventListener onConnectListener;
    //private ExecuteEventListener onExecuteListener;
    private void setupConnect(String ip,String username,String password,int port,int timeout){
        String[] strings = new String[]{ip,username,password};
        try {
            api = new Connector(port,timeout).execute(strings).get();
        } catch (ExecutionException e) {
            internalException = e;
        } catch (InterruptedException e) {
           internalException = e;
        }
    }
    public static MikrotikServer connect(String ip,String username,String password,int port,int timeout){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect(ip,username,password,port,timeout);
        return mikrotikServer;
    }
    public static MikrotikServer connect(String ip,String admin,String password,int port){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect(ip,admin,password,port,DEFAULT_IMEOUT);
        return mikrotikServer;
    }
    public static MikrotikServer connect(String ip,String admin,String password){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect(ip,admin,password,DEFAULT_PORT,DEFAULT_IMEOUT);
        return mikrotikServer;
    }
    public static MikrotikServer execute(Api api,String cmd){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupExecute(api,cmd);
        return mikrotikServer;
    }
    public static MikrotikServer execute(String cmd){
        MikrotikServer mikrotikServer = new MikrotikServer();
      if(api!=null){
       mikrotikServer.setupExecute(api,cmd);
      }else{
        try{
        throw new Exception("لا يوجد اتصال مسبق يرجى طلب connect");
      }catch(Exception ex){
        internalException =ex;
      }
    }
      return mikrotikServer;
    }
    private void setupExecute(Api readyApi,String cmd){
        try {
            mapList = new Executor(cmd).execute(readyApi).get();
        } catch (ExecutionException e) {
            internalException = e;
        } catch (InterruptedException e) {
           internalException = e;
        }
    }

    public void addConnectEventListener(ConnectEventListener listener) {
        //onConnectListener = listener;
        if (api != null)
            listener.onConnectionSuccess(api);
        else if (internalException!=null)
            listener.onConnectionFailed(internalException);
        else if (Connector.externalExceptionFromConnector!= null)
            listener.onConnectionFailed(Connector.externalExceptionFromConnector);
        else
            listener.onConnectionFailed(new Exception("unknown Error in Connector syriaLink"));
    }

    public void addExecuteEventListener(ExecuteEventListener listener) {
       // this.onExecuteListener = listener;
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
