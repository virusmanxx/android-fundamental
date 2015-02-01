package cpe.phaith.androidfundamental;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private static final int CHECK_GRADE_REQUEST = 1;

    private Context context;
    private Button btnCheck;
    private EditText score;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        btnCheck = (Button) findViewById(R.id.btnCheck);
        score = (EditText) findViewById(R.id.score);
        txtResult = (TextView) findViewById(R.id.txtResult);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (score.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please enter your score.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, CheckGradeActivity.class);
                    intent.putExtra("score", Integer.parseInt(score.getText().toString()));
                    startActivityForResult(intent, CHECK_GRADE_REQUEST);


                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHECK_GRADE_REQUEST && resultCode == RESULT_OK) {
            boolean passExam = data.getBooleanExtra("gradeResult", false);
            if (passExam) {
                txtResult.setTextColor(Color.GREEN);
                txtResult.setText("You've passed the test.");
            }else{
                txtResult.setTextColor(Color.RED);
                txtResult.setText("You've failed the test.");
            }
        }else{
            txtResult.setText("");
        }
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
