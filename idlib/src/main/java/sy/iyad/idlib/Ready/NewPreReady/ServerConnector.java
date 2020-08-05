package sy.iyad.idlib.Ready.NewPreReady;

import java.util.concurrent.Callable;

import javax.net.SocketFactory;

import sy.iyad.idlib.Ready.Api;

public class ServerConnector implements Callable<Api> {
    private String ip,username,password;
    private static Api api;
    private int port,timeout;
  //  public static Exception externalExceptionFromConnector;
    public ServerConnector(String ip,String username,String password,int port,int timeout){
        this.ip = ip;
        this.username = username;
        this.password = password;
        this.port = port;
        this.timeout = timeout;
    }
    @Override
    public Api call() throws Exception {
        api = Api.connect(SocketFactory.getDefault(),ip,port,timeout);
        api.login(username,password);
        return api;
    }

    public static Api getCurrentConnection() {
        return api;
    }
}
