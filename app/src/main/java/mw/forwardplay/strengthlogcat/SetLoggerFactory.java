package mw.forwardplay.strengthlogcat;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetLoggerFactory {
    private SetLoggerFactory(){}
    private static Map<String, LogSets> exercises = new HashMap<>();

    public static void updateSetLog(String exercise, LogSets logSets)
    {
        exercises.put(exercise, logSets);
    }

    public static LogSets getSetLogInstance(String exercise)
    {
        if(exercises.containsKey(exercise))
        {
            return exercises.get(exercise);
        }else{
            exercises.put(exercise, new LogSets());
            return exercises.get(exercise);
        }
    }
}

class LogSets{
    private List<Integer> setList = new ArrayList<>();
    private int setNumber;
    private int repNumber;

    public void addRep()
    {
        repNumber +=1 ;
    }

    public void addSet()
    {
        addSetToSetList(setNumber, repNumber);
        setNumber += 1;
        repNumber = 0;
    }

    public void removeSet()
    {
        int difference = setNumber - 1;
        if(setNumber > 1)
        {
            setNumber = difference;
            repNumber = setList.get(difference -1);
        }else{
            setNumber = 1;
            repNumber = 0;
        }
    }

    public void removeRep()
    {
        int difference = repNumber - 1;
        if(repNumber > 0)
        {
            repNumber = difference;
        }else{
            repNumber = 0;
        }
    }

    private void addSetToSetList(int setNumber, int repNumber)
    {
        if(setNumber >=1)
        {
            setNumber -= 1;
        }
        setList.add(setNumber, repNumber);
    }

    public int getRepNumber() {
        return repNumber;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public String toString()
    {
        StringBuilder repsStr = new StringBuilder();
        int counter = 0;
        int totalReps = 0;
        for (int reps: setList)
        {
            totalReps += reps;
            if(counter < setList.size())
            {
                repsStr.append(reps).append(" + ");
            }else{
                repsStr.append("=").append(totalReps);
            }
            ++counter;
        }
        return repsStr.toString();
    }
}
