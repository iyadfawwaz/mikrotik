package sy.iyad.idlib.Ready.PreReady;

import android.os.AsyncTask;

import java.util.List;
import java.util.Map;

import sy.iyad.idlib.Ready.Api;
import sy.iyad.idlib.Ready.MikrotikApiException;

public class Commander extends AsyncTask<Api,Integer, List<Map<String,String>>> {
    public String cmd;
    public Commander(String cmd){
        this.cmd = cmd;
    }
    @Override
    protected List<Map<String, String>> doInBackground(Api... apis) {
        List<Map<String, String>> mapList;
        try {
            mapList = apis[0].execute(cmd);
            return mapList;
        } catch (MikrotikApiException e) {

        }
        return null;
    }
}
