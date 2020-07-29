package sy.iyad.idlib.Ready;

import java.util.Map;

import sy.iyad.idlib.Ready.MikrotikApiException;

public interface ResultListener {
    /** receive data from router
     * @param result The data received */
    void receive(Map<String, String> result);

    /** called if the command associated with this listener experiences an error
     * @param ex Exception encountered */
    void error(MikrotikApiException ex);

    /** called when the command associated with this listener is done */
    void completed();
}
