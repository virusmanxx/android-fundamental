package cpe.phaith.androidfundamental;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class PlusActivity extends ActionBarActivity {

    private Context context;
    private Button btnPlus;
    private EditText input1;
    private EditText input2;
    private TextView txtOutput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plus);

        context = this;
        btnPlus = (Button)findViewById(R.id.btnPlus);
        input1 = (EditText)findViewById(R.id.input1);
        input2 = (EditText)findViewById(R.id.input2);
        txtOutput = (TextView)findViewById(R.id.txtOutput);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inputVal1 = parseInt(input1.getText().toString());
                int inputVal2 = parseInt(input2.getText().toString());
                txtOutput.setText(inputVal1 + inputVal2 + "");
            }
        });

    }

    private int parseInt(String stringNumber){
        if(stringNumber.isEmpty()){
            return 0;
        }else{
            return Integer.parseInt(stringNumber);
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
