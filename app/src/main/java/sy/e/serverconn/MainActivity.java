package sy.e.serverconn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import sy.iyad.idlib.Mikrotik;
import sy.iyad.idlib.Ready.Api;
import sy.iyad.idlib.Ready.NewPreReady.ServerExecutor;

public class MainActivity extends AppCompatActivity {

    public Mikrotik mikrotik;
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
        mikrotik = new Mikrotik();
        // second : you will do connect to server with your informations about your RouterOs
        if (mikrotik.connect(ip, username, password) != null) {
            warn.setText("connected");
            ipE.setVisibility(View.GONE);
            usernameE.setVisibility(View.GONE);
            passwordE.setVisibility(View.GONE);
            connectbtn.setVisibility(View.GONE);
            cmdE.setVisibility(View.VISIBLE);
            sendbtn.setVisibility(View.VISIBLE);
        } else if (Mikrotik.getInternalException() != null){
            warn.setText(Mikrotik.getInternalException().getMessage());
            // now you are connected and your connection was saved to ServerConector's Api
        }
    }
    private void executeSomethingCommand(String cmd){
        // execute your command ...
       if (mikrotik.execute(cmd) != null)
           warn.setText(Mikrotik.mapList.toString());
       else if (Mikrotik.getInternalException() != null)
           warn.setText(Mikrotik.getInternalException().getMessage());
    }
}
