package mw.forwardplay.strengthlogcat;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private String exerciseName;
    private int setNumber;
    private int repNumber;
    private EditText exerciseEditText;
    private TextView repCountTextView;
    private TextView setCountTextView;
    private TextView exerciseLogTextView;
    private LogSets setLog;
    private String exerciseLog;
    private ShareActionProvider shareContent;
    private boolean isExerciseNameEditable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        exerciseEditText = (EditText) findViewById(R.id.exercise_name);
        repCountTextView = (TextView) findViewById(R.id.rep_count);
        setCountTextView = (TextView) findViewById(R.id.set_count);
        exerciseLogTextView = (TextView) findViewById(R.id.exercise_log);

        if(savedInstanceState != null)
        {
            setNumber = (int) savedInstanceState.get("setNumber");
            repNumber = (int) savedInstanceState.get("repNumber");
            exerciseName = (String) savedInstanceState.get("exerciseName");
            exerciseLog = (String) savedInstanceState.get("exerciseLog");
            isExerciseNameEditable = (Boolean) savedInstanceState.get("isExerciseNameEditable");
            updateUIText();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putInt("setNumber", setNumber);
        savedInstanceState.putInt("repNumber", repNumber);
        savedInstanceState.putString("exerciseName", exerciseName);
        savedInstanceState.putString("exerciseLog", exerciseLog);
        savedInstanceState.putBoolean("isExerciseNameEditable", isExerciseNameEditable);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onClickNewExercise()
    {
        SetLoggerFactory.updateSetLog(exerciseName, setLog);
        setNumber = 0;
        repNumber = 0;
        exerciseName = "";
        isExerciseNameEditable = true;
        updateUIText();
    }

    public void onClickSetAdjusters(View view)
    {
        exerciseName = exerciseEditText.getText().toString();

        if (!exerciseName.isEmpty())
        {
            setLog = SetLoggerFactory.getSetLogInstance(exerciseName);
            switch (view.getId()){
                case R.id.add_rep:
                    if (setNumber == 0){
                        setLog.addSet();
                    }
                    setLog.addRep();
                    isExerciseNameEditable = false;
                    break;
                case R.id.add_set:
                    if (repNumber <= 0){
                        Toast.makeText(getApplicationContext(), "Can't create a new set when " +
                                "the current set has no reps", Toast.LENGTH_LONG).show();
                        break;
                    }
                    setLog.addSet();
                    isExerciseNameEditable = false;
                    break;
                case R.id.remove_rep:
                    setLog.removeRep();
                    break;
                case R.id.remove_set:
                    setLog.removeSet();
                    Toast.makeText(getApplicationContext(), "Riverted to previous Set",
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            setNumber = setLog.getSetNumber();
            repNumber = setLog.getRepNumber();
            SetLoggerFactory.updateSetLog(exerciseName, setLog);
            exerciseLog = SetLoggerFactory.getStrLog();
            setShareContentActionProvider();
            updateUIText();
        }else{
            Toast.makeText(getApplicationContext(), "Exercise name is required!",
                    Toast.LENGTH_LONG).show();
        }
    }

    void updateUIText()
    {
        exerciseEditText.setText(exerciseName);
        repCountTextView.setText(String.valueOf(repNumber));
        setCountTextView.setText(String.valueOf(setNumber));
        exerciseLogTextView.setText(exerciseLog);
        exerciseEditText.setEnabled(isExerciseNameEditable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.ic_launcher);
        getMenuInflater().inflate(R.menu.main_toolbar_items, menu);
        MenuItem shareItem = menu.findItem(R.id.share_info);
        shareContent = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        setShareContentActionProvider();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()){
            case R.id.next_exercise:
                onClickNewExercise();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    void setShareContentActionProvider()
    {
        StringBuilder subject = new StringBuilder();
        Date currentDate = new Date();
        subject.append("Strength Training: ").append(currentDate);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject.toString());
        shareIntent.putExtra(Intent.EXTRA_TEXT, exerciseLog);
        shareContent.setShareIntent(shareIntent);
    }
}
