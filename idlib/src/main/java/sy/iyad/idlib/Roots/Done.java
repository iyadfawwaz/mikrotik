package sy.iyad.idlib.Roots;

public class Done extends Response {
     Done(String tag) {
        super(tag);
    }
    void setHash(String hash) {
        this.hash = hash;
    }

    String getHash() {
        return hash;
    }

    private String hash;
}
