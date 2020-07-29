package sy.iyad.idlib.Ready;

public class ApiException extends MikrotikApiException {

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(String msg, Throwable err) {
        super(msg, err);
    }
}
