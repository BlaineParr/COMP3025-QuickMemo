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
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //constants
    final int ALL = 0;
    final int TODAY = 1;

    //instance variables
    private Button todayButton;
    private Button allButton;
    private Button newButton;
    private LinearLayout contentLayout;

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
        this.allButton = (Button) findViewById(R.id.allButton);
        this.newButton = (Button) findViewById(R.id.newButton);
        this.contentLayout = (LinearLayout) findViewById(R.id.contentLayout);

        /*
         * When the todayButton is clicked...
         */
        this.todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayMemos(TODAY);
            } //method onClick ends
        });

        /*
         * When the allButton is clicked...
         */
        this.allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayMemos(ALL);
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

        this.displayMemos(ALL);
    } //method onActivityResult ends

    /*
     * This method displays the users' entered memos
     */
    private void displayMemos(int sort) {
        //clear contentLayout
        this.contentLayout.removeAllViews();

        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        ArrayList<Memo> displayMemos = new ArrayList<Memo>(0);

        switch(sort) {
            case ALL: displayMemos = this.memos;
                break;
            case TODAY:
                for(int i = 0; i < this.memos.size(); i++) {
                    if(this.memos.get(i).getDate().equals(today)) {
                        displayMemos.add(this.memos.get(i));
                    } //if ends
                } //for ends
        } //switch ends

        for(int i = 0; i < displayMemos.size(); i++) {
            //make a new linear layout
            LinearLayout l = new LinearLayout(this);

            //set up linear layout
            l.setOrientation(LinearLayout.HORIZONTAL);
            l.setBackgroundColor(Color.DKGRAY);
            LayoutParams lParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            l.setLayoutParams(lParams);

            //set up the title text view
            TextView titleTextView = new TextView(this);
            titleTextView.setTextColor(Color.WHITE);
            titleTextView.setText(displayMemos.get(i).getTitle());
            titleTextView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));

            //set up the date textView
            TextView dateTextView = new TextView(this);
            dateTextView.setTextColor(Color.WHITE);
            dateTextView.setText(displayMemos.get(i).getDate());
            dateTextView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));

            //add the textViews
            l.addView(titleTextView);
            l.addView(dateTextView);

            //create another LinearLayout
            LinearLayout l2 = new LinearLayout(this);

            //set up the LinearLayout
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setBackgroundColor(Color.WHITE);
            l2.setLayoutParams(lParams);

            //set up the categoryTextView
            TextView categoryTextView = new TextView(this);
            categoryTextView.setTextColor(Color.BLACK);
            categoryTextView.setText(displayMemos.get(i).getCategory());
            categoryTextView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));

            //set up the descriptionTextView
            TextView descriptionTextView = new TextView(this);
            descriptionTextView.setTextColor(Color.BLACK);
            descriptionTextView.setText(displayMemos.get(i).getDescription());
            descriptionTextView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));

            //add the textViews
            l2.addView(categoryTextView);
            l2.addView(descriptionTextView);

            //add the LinearLayouts to contentLayout
            this.contentLayout.addView(l);
            this.contentLayout.addView(l2);
        } //for ends
    } //method displayMemos ends
} //class MainActivity ends
