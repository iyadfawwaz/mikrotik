package sy.iyad.idlib.Roots;

import sy.iyad.idlib.Ready.MikrotikApiException;

public class ApiDataException extends MikrotikApiException {

    public ApiDataException(String msg) {
        super(msg);
    }

    public ApiDataException(String msg, Throwable err) {
        super(msg, err);
    }
}
