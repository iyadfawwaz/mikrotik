package sy.iyad.idlib.Ready.PreReady;

import android.os.AsyncTask;

import sy.iyad.idlib.Ready.Api;
import sy.iyad.idlib.Ready.MikrotikApiException;

public class Connection extends AsyncTask<String,Integer, Api> {
    @Override
    protected Api doInBackground(String... strings) {
        Api api;
        try {
            api = Api.connect(strings[0]);
            api.login(strings[1],strings[2]);
            return api;
        } catch (MikrotikApiException ignored) {

        }
        return null;
    }
}
