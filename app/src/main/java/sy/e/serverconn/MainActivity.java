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

public class MainActivity extends AppCompatActivity {
    private MikrotikServer mikrotikServer;
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
        // first :  you will implement new Mikrotik class
        mikrotikServer = MikrotikServer.connect(ip,username,password);
        // second : you will do connect to server with your informations about your RouterOs
        if (mikrotikServer != null) {
            warn.setText("connected");
            ipE.setVisibility(View.GONE);
            usernameE.setVisibility(View.GONE);
            passwordE.setVisibility(View.GONE);
            connectbtn.setVisibility(View.GONE);
            cmdE.setVisibility(View.VISIBLE);
            sendbtn.setVisibility(View.VISIBLE);
            mikrotikServer.addConnectEventListener(new ConnectEventListener() {
                @Override
                public void onConnectionSuccess(Api api) {
                    System.out.println(api.toString());
                }

                @Override
                public void onConnectionFailed(Exception exp) {
                    System.out.println(exp.getMessage());
                }
            });
        }
    }
    private void executeSomethingCommand(String cmd){
        // execute your command ...
        MikrotikServer mikrotikServer = MikrotikServer.execute(cmd);
       if (mikrotikServer != null)
           mikrotikServer.addExecuteEventListener(new ExecuteEventListener() {
               @Override
               public void onExecutionSuccess(List<Map<String, String>> mapList) {
                   warn.setText(mapList.toString());
               }

               @Override
               public void onExecutionFailed(Exception exp) {
                   warn.setText(exp.getMessage());
               }
           });
    }
}
