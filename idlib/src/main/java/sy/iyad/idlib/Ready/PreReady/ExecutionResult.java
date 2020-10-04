package sy.iyad.idlib.Ready.PreReady;
import java.util.*;

public class ExecutionResult
{
	private List<Map<String,String>> listRes;
	public ExecutionResult(){
		
	}
	public List<Map<String,String>> getList(){
		return listRes;
	}
	public void setList(List<Map<String,String>> list){
		this.listRes = list;
	}
}
