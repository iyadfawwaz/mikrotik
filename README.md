that is full example for using syria mikrotik lib{


    private static String ip="YOUR MIKROTIK ROUTER IP ADDRESS ";
    private static String admin="YOUR MIKROTIK ROUTER ADMIN USERNAME";
    private static String password="YOUR MIKROTIK ROUTER PASSWORD";
    private static String cmd="YOUR COMMAND etc: '/ip/address/print'";
    MikrotikServer mikrotikServer;
    TextView textView;
    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
    
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mikrotikServer = new MikrotikServer();
        mikrotikServer.connect(ip,admin,password);
        mikrotikServer.setOnConnectListener(new OnConnectListener() {
        
            @Override
            public void onConnectionSuccess(Api api) {
            
                mikrotikServer.execute(cmd);
                mikrotikServer.setOnExecuteListener(new OnExecuteListener() {
                
                    @Override
                    public void onExecutionSuccess(List<Map<String, String>> mapList) {
                    
                        for (Map<String,String> map : mapList){
                        
                            System.out.println(map.get("YOUR KEY etc:'name,interface,address'"));
                        }
                    }
                    @Override
                    public void onExecutionFailed(Exception exp) {

                        textView.setText(exp.getMessage());
                    }
                });
            }
            @Override
            public void onConnectionFailed(Exception exp) {
                textView.setText(exp.getMessage());
            }
        });
    }
