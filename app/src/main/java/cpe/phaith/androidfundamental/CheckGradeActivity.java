package cpe.phaith.androidfundamental;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CheckGradeActivity extends ActionBarActivity {

    private Context context;
    private TextView txtGrade;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_grade);

        context = this;
        txtGrade = (TextView)findViewById(R.id.txtGrade);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);

        int score = getIntent().getIntExtra("score", 0);
        String grade;
        boolean passExam;
        if(score >= 80){
            grade = "A";
            passExam = true;
        }else if(score >= 70){
            grade = "B";
            passExam = true;
        }else if (score >=60){
            grade = "C";
            passExam = true;
        }else if (score >= 50){
            grade = "D";
            passExam = false;
        }else{
            grade = "F";
            passExam = false;
        }

        final boolean gradeResult = passExam;
        txtGrade.setText("Your grade is " + grade);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("gradeResult", gradeResult);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check_grade, menu);
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
