package sy.iyad.idlib;

import java.util.List;
import java.util.Map;
import sy.iyad.idlib.Ready.Api;
import sy.iyad.idlib.Ready.NewPreReady.Helper;
import sy.iyad.idlib.Ready.NewPreReady.OnHelperComplete;
import sy.iyad.idlib.Ready.NewPreReady.ServerConnector;
import sy.iyad.idlib.Ready.NewPreReady.ServerExecutor;

public class Mikrotik {
    private static final Helper helper = new Helper();
    private static Api api;
    public static List<Map<String, String>> mapList;
    private static Exception internalException;

    public static Exception getInternalException() {
        return internalException;
    }

    public Api connect(String ip, String username, String password){
       return connect(ip,username,password,8728,3000);
    }

    public Api connect(String ip,String username,String password,int port){
       return connect(ip,username,password,port,3000);
    }

    public Api connect(String ip,String username,String password,int port,int timeout){
        ServerConnector connector = new ServerConnector(ip,username,password,port,timeout);
        helper.startHelper(connector, new OnHelperComplete<Api>() {
            @Override
            public void onHelperSuccess(Api result) {
                api = result;
            }

            @Override
            public void onHelperFailed(Exception exception) {
                internalException = exception;
            }
        });
        return api;
    }

    public List<Map<String,String>> execute(String cmd){
       return execute(api,cmd);
    }

    public List<Map<String,String>> execute(ServerConnector currentConnection,String cmd){
       return execute(currentConnection.getCurrentConnection(),cmd);
    }

    public List<Map<String,String>> execute(Api api  ,String cmd){
        ServerExecutor executor = new ServerExecutor(api,cmd);
        helper.startHelper(executor, new OnHelperComplete<List<Map<String, String>>>() {
            @Override
            public void onHelperSuccess(List<Map<String, String>> result) {
                mapList = result;
            }

            @Override
            public void onHelperFailed(Exception exception) {
                internalException = exception;
            }
        });
        return mapList;
    }
}
