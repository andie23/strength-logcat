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

    public static boolean isset(String instanceName)
    {
        return  exercises.containsKey(instanceName);
    }

    public static LogSets getSetLogInstance(String instanceName)
    {
        if(isset(instanceName))
        {
            return exercises.get(instanceName);
        }else{
            exercises.put(instanceName, new LogSets());
            return exercises.get(instanceName);
        }
    }

    public static String getStrLog(){
        StringBuilder exerciseLogStr = new StringBuilder();
        int totalReps = 0;
        int totalSets = 0;

        for(Map.Entry log: exercises.entrySet()){
            LogSets set = (LogSets) log.getValue();
            totalSets += set.getSetNumber();
            totalReps += set.getTotalReps();
            exerciseLogStr.append(log.getKey())
                        .append(": ")
                        .append(set.toString())
                        .append("\n");
        }
        exerciseLogStr.append("\n")
                .append("Total Sets: ").append(totalSets)
                .append("\n")
                .append("Total Reps: ")
                .append(totalReps)
                .append("\n")
                .append("Total Exercises:")
                .append(exercises.size());
        return  exerciseLogStr.toString();
    }
}

class LogSets{
    private List<Integer> setList = new ArrayList<>();
    private int setIndex;
    private int setNumber;
    private int repNumber;

    public void addRep()
    {
        repNumber += 1;
        updateSetRepInSetList();
    }

    public void addSet()
    {
        repNumber = 0;
        addNewSetInSetList();
    }

    public void removeSet()
    {
        if(setIndex > 0)
        {
            removeSetInSetList();
        }else{
            setNumber = 1;
            repNumber = 0;
            updateSetRepInSetList();
        }
    }

    public void removeRep()
    {
        int difference = repNumber - 1;
        if(repNumber > 0)
        {
            repNumber = difference;
            updateSetRepInSetList();
        }else{
            repNumber = 0;
        }
    }

    private void removeSetInSetList()
    {
        setList.remove(setIndex);
        setSetNumber();
        setSetIndex();
    }

    private void updateSetRepInSetList(){
        setList.set(setIndex, repNumber);
    }

    private void setSetNumber()
    {
        setNumber = setList.size();
    }

    private void setSetIndex()
    {
        setIndex = setList.size() -1;
    }

    private void addNewSetInSetList()
    {
        setList.add(repNumber);
        setSetNumber();
        setSetIndex();
    }

    public int getRepNumber() {
        return repNumber;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public int getTotalReps()
    {
        int totalReps = 0;
        for(int counter=0; counter < setList.size(); ++counter)
        {
            totalReps += setList.get(counter);
        }
        return totalReps;
    }

    public String toString()
    {
        StringBuilder setListStr = new StringBuilder();
        int totalReps = 0;
        for(int counter=0; counter < setList.size(); ++counter)
        {
            totalReps += setList.get(counter);
            if (counter >= 1)
            {
                setListStr.append(" + ").append(setList.get(counter));
            }else{
                setListStr.append(setList.get(counter));
            }
        }
        setListStr.append(" = ").append(totalReps);
        return  setListStr.toString();
    }
}
