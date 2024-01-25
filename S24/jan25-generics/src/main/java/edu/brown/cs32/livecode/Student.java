package edu.brown.cs32.livecode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student<String> {
    List<String> todos;

    public Student(List<String> todos) {
        this.todos = todos;
    }

    /**
     * This is a Javadoc comment (notice the double asterisk on the first line). It will power mouseover information
     * and documentation, usually automatically. To do this, it has some annotations, like @return (see below).
     *
     * *ALL* public-facing methods should contain an informative Javadoc. Why? Because they're (hopefully) meant to
     * be used by other engineers, not just you! Public classes should also get Javadoc (I've omitted it for space.)
     *
     * Here's an example:
     *
     * Find and return the most common item in this Student's to-do list.
     *    EXERCISE: do you like that description?
     *
     * @return the most common to-do item
     */
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
