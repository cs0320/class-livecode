package edu.brown.cs32.livecode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student<T> {
    /** This student's to-do list */
    List<T> todos;
    public Student(List<T> todos) {
        this.todos = todos;
    }

    /** To-do lists might have the same thing listed multiple times. (Tim's certainly does.)
     *  What's the most needed thing---the most common element on the list?
     */
    public T mostCommonTodoItem() {
        if(todos.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Map<T, Integer> counts = new HashMap<>();
        for(T s : this.todos) {
            if(!counts.containsKey(s)) counts.put(s, 1);
            else counts.put(s, counts.get(s) + 1);
        }
        T mostCommonItem = null;
        int howCommon = 0;
        for(T s : counts.keySet()) {
            if(counts.get(s) > howCommon) {
                mostCommonItem = s;
                howCommon = counts.get(s);
            }
        }
        return mostCommonItem;
    }
}
