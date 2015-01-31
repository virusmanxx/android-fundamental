package cpe.phaith.androidfundamental;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final String NEW_LINE = System.getProperty("line.separator");
    private TextView txtChat;
    private Button btnSend;
    private EditText chatInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtChat = (TextView) findViewById(R.id.txtChat);
        btnSend = (Button) findViewById(R.id.btnSend);
        chatInput = (EditText) findViewById(R.id.chatInput);

        final Pubnub pubnub = new Pubnub("demo", "demo");
        try {
            pubnub.subscribe(new String[]{"hello_channel"}, new Callback() {
                @Override
                public void successCallback(String channel, final Object message) {
                    super.successCallback(channel, message);
                    Log.i(TAG, "pubnub success: " + message.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtChat.append(message.toString() + NEW_LINE);
                        }
                    });
                }

                @Override
                public void errorCallback(String channel, Object message) {
                    super.errorCallback(channel, message);
                    Log.e(TAG, "pubnub error: " + message.toString());
                }

                @Override
                public void connectCallback(String channel, Object message) {
                    super.connectCallback(channel, message);
                    Log.i(TAG, "pubnub connect: " + message.toString());
                }

                @Override
                public void reconnectCallback(String channel, Object message) {
                    super.reconnectCallback(channel, message);
                    Log.i(TAG, "pubnub reconnect: " + message.toString());
                }

                @Override
                public void disconnectCallback(String channel, Object message) {
                    super.disconnectCallback(channel, message);
                    Log.i(TAG, "pubnub disconnect: " + message.toString());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "pubnub exception", e);
        }

        final Callback callback = new Callback() {
            @Override
            public void successCallback(String channel, Object message) {
                super.successCallback(channel, message);
                Log.i(TAG, "publish success: " + message.toString());
            }

            @Override
            public void errorCallback(String channel, Object message) {
                super.errorCallback(channel, message);
                Log.e(TAG, "publish error: " + message.toString());
            }
        };
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pubnub.publish("hello_channel", chatInput.getText().toString(), callback);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
