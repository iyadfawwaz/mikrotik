package sy.iyad.idlib.Roots;

public class ScanException extends ParseException {
    public ScanException(String msg) {
        super(msg);
    }

    public ScanException(String msg, Throwable err) {
        super(msg, err);
    }
}
