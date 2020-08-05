package sy.iyad.idlib.Ready.NewPreReady;

public interface OnHelperComplete<Iyad> {
    void onHelperSuccess(Iyad result);
    void onHelperFailed(Exception exception);
}
