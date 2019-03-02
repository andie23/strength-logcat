package mw.forwardplay.strengthlogcat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String exerciseName;
    private int setNumber;
    private int repNumber;
    private EditText exerciseEditText;
    private TextView repCountTextView;
    private TextView setCountTextView;
    private LogSets setLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exerciseEditText = (EditText) findViewById(R.id.exercise_name);
        repCountTextView = (TextView) findViewById(R.id.rep_count);
        setCountTextView = (TextView) findViewById(R.id.set_count);
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
                    break;
                case R.id.add_set:
                    if (repNumber <= 0){
                        Toast.makeText(getApplicationContext(), "Can't create a new set when " +
                                "the current set's reps are 0", Toast.LENGTH_LONG).show();
                        break;
                    }
                    setLog.addSet();
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
            repCountTextView.setText(String.valueOf(repNumber));
            setCountTextView.setText(String.valueOf(setNumber));
        }else{
            Toast.makeText(getApplicationContext(), "Exercise name is required!",
                    Toast.LENGTH_LONG).show();
        }
    }

}
