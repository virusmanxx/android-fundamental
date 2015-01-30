package cpe.phaith.androidfundamental;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private Context context;
    private static final String TAG = MainActivity.class.getName();
    private Button loginButton;
    private Button btnPost;
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    private static final String PUBLISH_PERMISSION = "publish_actions";
    private static final List<String> PERMISSIONS = Arrays.asList(PUBLISH_PERMISSION);
    private PendingAction pendingAction;

    enum PendingAction {
        NONE, LOGGED_IN, LOGGED_OUT, PUBLISH
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        pendingAction = PendingAction.NONE;

        loginButton = (Button) findViewById(R.id.login_button);
        btnPost = (Button) findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Session session = Session.getActiveSession();
                if(session != null && session.isOpened()){
                    if(!session.isPermissionGranted(PUBLISH_PERMISSION)) {
                        pendingAction = PendingAction.PUBLISH;
                        session.requestNewPublishPermissions(new Session.NewPermissionsRequest(MainActivity.this, PERMISSIONS));
                    }else{
                        postToWall(session);
                    }

                }
            }
        });
        updateLogInUI();


    }

    private void postToWall(Session session){
        Request.newStatusUpdateRequest(session, "Hello World from tutorial app.", new Request.Callback() {
            @Override
            public void onCompleted(Response response) {
                Log.d(TAG, response.toString());
                Toast.makeText(context, "Message is published to wall", Toast.LENGTH_SHORT).show();
            }
        }).executeAsync();
    }

    private void updateLogOutUI() {
        btnPost.setEnabled(true);
        loginButton.setText("Logout");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pendingAction = PendingAction.LOGGED_OUT;
                callFacebookLogout();
            }
        });
    }

    private void updateLogInUI() {
        btnPost.setEnabled(false);
        loginButton.setText("Facebook Login");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pendingAction = PendingAction.LOGGED_IN;
                Session session = Session.getActiveSession();
                if (session != null && !session.isOpened() && !session.isClosed()) {
                    session.openForRead(new Session.OpenRequest(MainActivity.this)
                            .setCallback(callback));
                } else {
                    Session.openActiveSession(MainActivity.this, true,  callback);
                }
            }
        });
    }

    public static void callFacebookLogout() {
        if (Session.getActiveSession() != null) {
            Session.getActiveSession().closeAndClearTokenInformation();
        }

        Session.setActiveSession(null);
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

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            if (pendingAction == PendingAction.LOGGED_IN) {
                pendingAction = PendingAction.NONE;
                Log.i(TAG, "Logged in...");
                updateLogOutUI();
                Request.newMeRequest(session, new Request.GraphUserCallback() {

                    // callback after Graph API response with user object
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        Toast.makeText(context, user.getName() + " is Logged in", Toast.LENGTH_SHORT).show();
                    }
                }).executeAsync();
            }else if (pendingAction == PendingAction.PUBLISH){
                pendingAction = PendingAction.NONE;
                postToWall(session);
            }
        } else if (state.isClosed()) {
            if (pendingAction == PendingAction.LOGGED_OUT) {
                pendingAction = PendingAction.NONE;
                updateLogInUI();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Session.getActiveSession() != null)
            Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }
}
