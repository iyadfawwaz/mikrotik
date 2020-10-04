package sy.iyad.idlib;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import sy.iyad.idlib.Ready.Api;
import sy.iyad.idlib.Ready.PreReady.ConnectEventListener;
import sy.iyad.idlib.Ready.PreReady.ExecuteEventListener;
import sy.iyad.idlib.Ready.PreReady.Executor;
import sy.iyad.idlib.Ready.PreReady.Connector;
import sy.iyad.idlib.Roots.Result;
import sy.iyad.idlib.Ready.PreReady.ConnectionResult;
import sy.iyad.idlib.Ready.PreReady.*;
import android.annotation.*;

public class MikrotikServer {

    public static final int DEFAULT_PORT=8728;
    public static final int DEFAULT_IMEOUT=6000;
    private static Api api;
    private static List<Map<String, String>> mapList;
    private static Exception internalException;
    
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
	
	public static MikrotikServer connect(){
		return MikrotikServer.connect("2.2.2.2","admin","995x",DEFAULT_PORT,DEFAULT_IMEOUT);
	}
	
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

    public ConnectEventListener addConnectEventListener(@NonNull ConnectEventListener<ConnectionResult> listener) {

		LatestResult<ConnectionResult> task = new LatestResult<>();
        if (api != null){
			task.setOperationBool(true);
			ConnectionResult res = new ConnectionResult();
			res.setApi(api);
            task.setResult(res);
        }else if (internalException!=null){
			task.setOperationBool(false);
            task.setException(internalException);
       } else if (Connector.externalExceptionFromConnector!= null){
            task.setOperationBool(false);
			task.setException(Connector.externalExceptionFromConnector);
       } else{
		   task.setOperationBool(false);
            task.setException(new Exception("unknown Error in Connector syriaLink"));
    }
	listener.onCompleted(task);
	return listener;
	}

    public ExecuteEventListener addExecuteEventListener(ExecuteEventListener<ExecutionResult> listener) {

		LatestResult<ExecutionResult> lat= new LatestResult<>();
        if (mapList != null){
            ExecutionResult res = new ExecutionResult();
			res.setList(mapList);
			lat.setResult(res);
			lat.setOperationBool(true);
      }  else if (internalException!=null){
		  lat.setOperationBool(false);
            lat.setException(internalException);
        }else if (Executor.externalExceptionFromExecutor!=null){
			lat.setOperationBool(false);
			lat.setException(Executor.externalExceptionFromExecutor);
            
        }else{
			lat.setOperationBool(false);
            lat.setException(new Exception("unknown Error in Executor StriaLink"));
			}
			listener.onCompleted(lat);
			return listener;
    }
	
}
