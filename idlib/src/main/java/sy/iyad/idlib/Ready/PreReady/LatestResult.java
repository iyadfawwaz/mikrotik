package sy.iyad.idlib.Ready.PreReady;

public class LatestResult<Tx>
{
	
		private Tx res;
		private Exception exp;
		private boolean boolRes,compRes;
		public LatestResult(){

		}
		public Tx getResult(){
			return res;
		}
		public Exception getException(){
			return exp;
		}
		public boolean isSuccessful(){
			return boolRes;
		}
		public boolean isCompleted(){
			return compRes;
		}
		public void setResult(Tx res){
			this.res=res;
		}
		public void setException(Exception exp){
			this.exp = exp;
		}
		public void setOperationBool(boolean isSuccessful){
			this.boolRes = isSuccessful;
		}
}
