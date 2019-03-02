package mw.forwardplay.strengthlogcat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String exerciseName;
    private int setNumber;
    private int repNumber;
    private Button newExerciseButton;
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
        newExerciseButton = (Button) findViewById(R.id.new_exercise);

        newExerciseButton.setEnabled(false);
    }

    public void onClickNewExercise(View view)
    {
        SetLoggerFactory.updateSetLog(exerciseName, setLog);
        setNumber = 0;
        repNumber = 0;
        exerciseName = "";
        exerciseEditText.setText("");
        repCountTextView.setText(String.valueOf(repNumber));
        setCountTextView.setText(String.valueOf(setNumber));
        exerciseEditText.setEnabled(true);
    }

    public void onClickSetAdjusters(View view)
    {
        exerciseName = exerciseEditText.getText().toString();

        if (!exerciseName.isEmpty())
        {
            newExerciseButton.setEnabled(true);
            setLog = SetLoggerFactory.getSetLogInstance(exerciseName);
            switch (view.getId()){
                case R.id.add_rep:
                    if (setNumber == 0){
                        setLog.addSet();
                    }
                    setLog.addRep();
                    exerciseEditText.setEnabled(false);
                    break;
                case R.id.add_set:
                    if (repNumber <= 0){
                        Toast.makeText(getApplicationContext(), "Can't create a new set when " +
                                "the current set's reps are 0", Toast.LENGTH_LONG).show();
                        break;
                    }
                    setLog.addSet();
                    exerciseEditText.setEnabled(false);
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
