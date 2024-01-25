package edu.brown.cs32.livecode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Backup of initial state for Student class, for easy tracking.
 */
public class StudentStarter {
    List<String> todos;

    public StudentStarter(List<String> todos) {
        this.todos = todos;
    }

    public String mostCommonTodoItem() {
        Map<String, Integer> counts = new HashMap<>();
        for(String s : this.todos) {
            if(!counts.containsKey(s)) counts.put(s, 1);
            else counts.put(s, counts.get(s) + 1);
        }
        String mostCommonItem = null;
        int howCommon = 0;
        for(String s : counts.keySet()) {
            if(counts.get(s) > howCommon) {
                mostCommonItem = s;
                howCommon = counts.get(s);
            }
        }
        return mostCommonItem;
    }

}
