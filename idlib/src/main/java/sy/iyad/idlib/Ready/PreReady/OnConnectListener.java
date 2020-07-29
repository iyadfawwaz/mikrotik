package sy.iyad.idlib.Ready.PreReady;

import sy.iyad.idlib.Ready.Api;

public interface OnConnectListener {
    void onConnectionSuccess(Api api);
    void onConnectionFailed(Exception exp);
}
