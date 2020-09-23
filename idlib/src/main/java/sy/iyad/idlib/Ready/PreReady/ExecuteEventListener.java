package sy.iyad.idlib.Ready.PreReady;

import java.util.List;
import java.util.Map;

public interface ExecuteEventListener {
    void onExecutionSuccess(List<Map<String, String>> mapList);
    void onExecutionFailed(Exception exp);
}
