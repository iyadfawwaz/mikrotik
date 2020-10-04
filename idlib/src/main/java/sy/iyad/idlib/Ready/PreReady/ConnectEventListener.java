package sy.iyad.idlib.Ready.PreReady;

import sy.iyad.idlib.Ready.Api;
import android.annotation.*;

public interface ConnectEventListener<ConnectionResult> {
	
    void onCompleted(@NonNull LatestResult<ConnectionResult> task);
}
