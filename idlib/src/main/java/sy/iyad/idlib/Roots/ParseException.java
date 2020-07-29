package sy.iyad.idlib.Roots;

import sy.iyad.idlib.Ready.MikrotikApiException;

public class ParseException extends MikrotikApiException {
    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(String msg, Throwable err) {
        super(msg, err);
    }
}
