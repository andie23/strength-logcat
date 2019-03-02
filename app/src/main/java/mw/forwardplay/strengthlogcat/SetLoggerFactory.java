package mw.forwardplay.strengthlogcat;

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

    public static String getStrLog(){
        StringBuilder exerciseLogStr = new StringBuilder();

        for(Map.Entry log: exercises.entrySet()){
            exerciseLogStr.append(log.getKey())
                        .append(":")
                        .append(log.getValue().toString())
                        .append("\n");
        }
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

    public String toString()
    {
        StringBuilder setListStr = new StringBuilder();
        for(int counter=0; counter < setList.size(); ++counter)
        {
            if (counter >= 1)
            {
                setListStr.append("+").append(setList.get(counter));
            }else{
                setListStr.append(setList.get(counter));
            }
        }

        return  setListStr.toString();
    }
}
