package cs160.caloriesconverter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<CharSequence> adapter;
    private static final HashSet<String> REPS = new HashSet<String>(Arrays.asList("Pushup", "Situp", "Squats", "Pullup"));
    private static final HashSet<String> MINS = new HashSet<String>(Arrays.asList("Jumping Jacks", "Jogging",
            "Leg-lift", "Plank", "Cycling", "Walking", "Swimming", "Stair-Climbing"));
    private static final HashMap<String, Integer> TABLE;
    static {
        TABLE = new HashMap<String, Integer>();
        TABLE.put("Pushup", 350);
        TABLE.put("Situp", 200);
        TABLE.put("Squats", 225);
        TABLE.put("Leg-lift", 25);
        TABLE.put("Plank", 25);
        TABLE.put("Jumping Jacks", 10);
        TABLE.put("Pullup", 100);
        TABLE.put("Cycling", 12);
        TABLE.put("Walking", 20);
        TABLE.put("Jogging", 12);
        TABLE.put("Swimming", 13);
        TABLE.put("Stair-Climbing", 15);
        TABLE.put("Calories", 100);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((TextView)findViewById(R.id.toolbar_title)).setText(Html.fromHtml("CAL<sup><small>2</small></sup>"));

        Spinner selectLeft = (Spinner) findViewById(R.id.selectLeft);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.exercises, R.layout.spinner_item);
        selectLeft.setAdapter(adapter);
        selectLeft.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                 TextView unit = (TextView) findViewById(R.id.unitLeft);
                 String exercise = (String) parent.getItemAtPosition(pos);
                 if (REPS.contains(exercise)) {
                     unit.setText(R.string.reps);
                 } else if (MINS.contains(exercise)) {
                     unit.setText(R.string.mins);
                 } else {
                     unit.setText("");
                 }

                 convert();

             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {
                 // Another interface callback
             }
         }
        );

        Spinner selectRight = (Spinner) findViewById(R.id.selectRight);
        selectRight.setAdapter(adapter);
        selectRight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                TextView unit = (TextView) findViewById(R.id.unitRight);
                String exercise = (String) parent.getItemAtPosition(pos);
                if (REPS.contains(exercise)) {
                    unit.setText(R.string.reps);
                } else if (MINS.contains(exercise)) {
                    unit.setText(R.string.mins);
                } else {
                    unit.setText("");
                }

                convert();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }

        });

        EditText input = (EditText) findViewById(R.id.input);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                convert();
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

    public void convert() {
        Spinner selectLeft = (Spinner) findViewById(R.id.selectLeft);
        String itemLeft = selectLeft.getSelectedItem().toString();
        Spinner selectRight = (Spinner) findViewById(R.id.selectRight);
        String itemRight = selectRight.getSelectedItem().toString();
        EditText input = (EditText) findViewById(R.id.input);
        String inputString = input.getText().toString();
        Integer inputNum = 0;
        if (!inputString.equals("")) {
            inputNum = Integer.parseInt(inputString);
        }

        TextView result = (TextView) findViewById(R.id.result);
        Integer resultNum = (int) Math.ceil((float) inputNum* (float) TABLE.get(itemRight)
                / TABLE.get(itemLeft));
        result.setText(resultNum.toString());
    }


}
