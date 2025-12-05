package com.todo.domain.sort;

import com.todo.domain.Task;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CreationTimeSortStrategy implements TaskSortStrategy {
    @Override
    public void sort(List<Task> tasks) {
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return t1.getCreatedAt().compareTo(t2.getCreatedAt());
            }
        });
    }
}
