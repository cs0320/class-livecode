package edu.brown.cs32.livecode.prep;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * ***PREP***
 * This class is meant to be livecoded in lecture, replacing the original Student class.
 * If you are reading this in advance of the lecture, don't spoil the story in class.
 */

/**
 * A /generic/ student can hold any kind of item in their to-do list.
 * Individual generic students will still have *ONE* kind of item in their lists,
 *   but we aren't enforcing strings on every single student anymore.
 * @param <T> the type of entries in this student's list
 */
public class PrepGenericStudent<T> {
    /** This student's to-do list */
    List<T> todos;
    public PrepGenericStudent(List<T> todos) {
        this.todos = todos;
    }

    /** To-do lists might have the same thing listed multiple times. (Tim's certainly does.)
     *  What's the most needed thing---the most common element on the list?
     */
    public T mostCommonTodoItem() {
        // I don't like this returning-null business.
        // Better to give something more meaningful.
        if(this.todos.isEmpty()) {
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
