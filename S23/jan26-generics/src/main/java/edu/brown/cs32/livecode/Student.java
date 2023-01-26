package edu.brown.cs32.livecode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student<T> {
    List<T> todos;

    public Student(List<T> todos) {
        this.todos = todos;
    }

    /**
     *
     * @return
     * @throws IllegalStateException if the todo list is empty
     */
    public T mostCommonTodoItem() throws IllegalStateException {
        if(this.todos.size() < 1) {
            //throw new IllegalArgumentException("todo list was empty");
            throw new IllegalStateException("todo list was empty");
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
