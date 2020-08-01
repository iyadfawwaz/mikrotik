package sy.iyad.idlib.Ready.PreReady;

import android.os.AsyncTask;
import javax.net.SocketFactory;
import sy.iyad.idlib.Ready.Api;
import sy.iyad.idlib.Ready.MikrotikApiException;

public class Connection extends AsyncTask<String,Integer, Api> {
  public static int PORTcUstom=8728;
  public static int TIMEOUTcUstom=1000;
    @Override
    protected Api doInBackground(String... strings) {
        Api api;
        try {
            api = Api.connect(SocketFactory.getDefault(),strings[0],PORTcUstom,TIMEOUTcUstom);
            api.login(strings[1],strings[2]);
            return api;
        } catch (MikrotikApiException ignored) {

        }
        return null;
    }
}
