package sy.iyad.idlib.Ready.NewPreReady;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import sy.iyad.idlib.Ready.Api;
import sy.iyad.idlib.Ready.NewPreReady.ServerConnector;

public class ServerExecutor implements Callable<List<Map<String,String>>> {
    private String cmd;
    private Api api;
    public ServerExecutor(Api api,String cmd){
        this.cmd =cmd;
        this.api = api;
    }
    public ServerExecutor(ServerConnector currentConnection, String cmd){
        this.api = currentConnection.getCurrentConnection();
        this.cmd = cmd;
    }
    public ServerExecutor(String cmd){
        this.cmd = cmd;
        this.api = ServerConnector.getCurrentConnection();
    }
    @Override
    public List<Map<String, String>> call() throws Exception {
        List<Map<String,String>> mapList;
        mapList = api.execute(cmd);
        return mapList;
    }
}
