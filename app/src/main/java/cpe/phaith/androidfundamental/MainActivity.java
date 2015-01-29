package cpe.phaith.androidfundamental;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cpe.phaith.androidfundamental.adapters.PhoneListAdapter;
import cpe.phaith.androidfundamental.items.PhoneItem;


public class MainActivity extends ActionBarActivity {

    private Context context;
    private ListView listView;
    private List<PhoneItem> phoneList;
    private PhoneListAdapter phoneListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        listView = (ListView)findViewById(R.id.listView);

        phoneList = new ArrayList<>();
        phoneListAdapter = new PhoneListAdapter(context, phoneList);
        listView.setAdapter(phoneListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhoneListAdapter adapter = (PhoneListAdapter)parent.getAdapter();
                Toast.makeText(context,adapter.getItem(position).getName()+" is selected",Toast.LENGTH_SHORT).show();
            }
        });

        phoneList.add(new PhoneItem("Galaxy Nexus", "http://images.all-free-download.com/images/graphiclarge/mobile_icon_55606.jpg"));
        phoneList.add(new PhoneItem("Nexus 4", "http://images.all-free-download.com/images/graphiclarge/mobile_icon_55606.jpg"));
        phoneList.add(new PhoneItem("Nexus 5", "http://images.all-free-download.com/images/graphiclarge/mobile_icon_55606.jpg"));
        phoneList.add(new PhoneItem("Iphone 5", "http://simpleicon.com/wp-content/uploads/iphone.png"));
        phoneList.add(new PhoneItem("Zen Phone", "http://images.all-free-download.com/images/graphiclarge/mobile_icon_55606.jpg"));
        phoneList.add(new PhoneItem("Nokia 3310", "http://images.all-free-download.com/images/graphiclarge/mobile_icon_55606.jpg"));
        phoneList.add(new PhoneItem("Iphone 4s", "http://simpleicon.com/wp-content/uploads/iphone.png"));


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
