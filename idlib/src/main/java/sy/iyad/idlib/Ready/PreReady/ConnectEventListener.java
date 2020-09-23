package sy.iyad.idlib.Ready.PreReady;

import sy.iyad.idlib.Ready.Api;

public interface ConnectEventListener {
    void onConnectionSuccess(Api api);
    void onConnectionFailed(Exception exp);
}
