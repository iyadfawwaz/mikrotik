package sy.e.serverconn;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.List;
import java.util.Map;
import sy.iyad.idlib.MikrotikServer;
import sy.iyad.idlib.Ready.Api;
import sy.iyad.idlib.Ready.PreReady.ConnectEventListener;
import sy.iyad.idlib.Ready.PreReady.ExecuteEventListener;
import sy.iyad.idlib.Ready.PreReady.*;
/*@author iyad fawwaz
*/

public class MainActivity extends AppCompatActivity {
    
    Button connectbtn,sendbtn;
    TextView warn;
    EditText ipE,usernameE,passwordE,cmdE;
    String ip,username,password,cmd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectbtn = findViewById(R.id.connect);
        sendbtn = findViewById(R.id.send);
        ipE = findViewById(R.id.ip);
        cmdE = findViewById(R.id.command);
        usernameE = findViewById(R.id.username);
        passwordE = findViewById(R.id.password);
        warn = findViewById(R.id.warning);
        connectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip = ipE.getText().toString();
                username = usernameE.getText().toString();
                password = passwordE.getText().toString();
                connectToServer(ip,username,password);
            }
        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cmd = cmdE.getText().toString();
                executeSomethingCommand(cmd);
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void connectToServer(String ip, String username, String password) {
		
		MikrotikServer.connect().addConnectEventListener(new ConnectEventListener<ConnectionResult>(){

				@Override
				public void onCompleted(LatestResult<ConnectionResult> task)
				{
					if(task.isSuccessful()){
						
						warn.setText("connected");
						ipE.setVisibility(View.GONE);
						usernameE.setVisibility(View.GONE);
						passwordE.setVisibility(View.GONE);
						connectbtn.setVisibility(View.GONE);
						cmdE.setVisibility(View.VISIBLE);
						sendbtn.setVisibility(View.VISIBLE);
					}
				}
			});
    }
	
    private void executeSomethingCommand(String cmd){
        
        MikrotikServer.execute(cmd).addExecuteEventListener(
			new ExecuteEventListener<ExecutionResult>(){

				@Override
				public void onCompleted(LatestResult<ExecutionResult> listRes)
				{
					if(listRes.isSuccessful()){
						warn.setText(listRes.getResult().getList().toString());
					}else{
						warn.setText(listRes.getException().getMessage());
					}
				}
			});        
    }
}
