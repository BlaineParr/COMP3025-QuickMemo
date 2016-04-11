/*
 * Quick Memo
 * By: Blaine Parr, Richard Estrada and Cody Hutchinson
 * Date Last Edited: April 11, 2016
 * Last Edited By: Blaine Parr
 * Description: This application displays tasks/memos entered by the user. The user can view tasks
 * for today, completed tasks and all tasks.
 */
package ca.georgiancollege.quickmemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //instance variables
    private Button todayButton;
    private Button newButton;
    private LinearLayout mainLayout;

    private ArrayList<Memo> memos;

    /*
     * This method runs when the app is started. It sets up the app and all of its components.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //initialize the arrayList and the buttons
        this.memos = new ArrayList<Memo>(0);

        this.todayButton = (Button) findViewById(R.id.todayButton);
        this.newButton = (Button) findViewById(R.id.newButton);
        this.mainLayout = (LinearLayout) findViewById(R.id.MainLayout);

        /*
         * When the todayButton is clicked...
         */
        this.todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("QuickMemo", memos.get(0).getDescription());
            } //method onClick ends
        });

        /*
         * When the new button is clicked, launch the create activity, and prepare to receive
         * memo data as a result.
         */
        this.newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a new CreateActivity
                Intent i = new Intent(getApplicationContext(), CreateActivity.class);

                //start the activity, and prepare for result code 100
                startActivityForResult(i, 100);
            } //method onClick ends
        });
    } //method onCreate ends

    /*
     * This method handles the returned information from the CreateActivity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //prepare to receive a requestCode, resultCode and data.
        super.onActivityResult(requestCode, resultCode, data);

        //if the result code is 100, we were successfully returned data
        //get the memo information from data and add it as a new Memo
        if(resultCode == 100) {
            this.memos.add(new Memo(data.getExtras().get("title").toString(), data.getExtras().get("category").toString(), data.getExtras().get("date").toString(), data.getExtras().get("description").toString()));
        } //if ends

        this.displayMemos();
    } //method onActivityResult ends

    private void displayMemos() {
        for(int i = 0; i < this.memos.size(); i++) {
            //make a new linear layout
            LinearLayout l = new LinearLayout(this);

            //orientation is horizontal
            l.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            TextView textView = new TextView(this);

            textView.setText(this.memos.get(i).getTitle());

            l.addView(textView);

            this.mainLayout.addView(l);
        } //for ends
    } //method displayMemos ends
} //class MainActivity ends
