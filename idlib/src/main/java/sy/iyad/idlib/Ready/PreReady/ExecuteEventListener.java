package sy.iyad.idlib.Ready.PreReady;

import java.util.List;
import java.util.Map;
import android.annotation.*;

public interface ExecuteEventListener<ExecutionResult> {
    
	void onCompleted(@NonNull LatestResult<ExecutionResult> listRes);
  
}
