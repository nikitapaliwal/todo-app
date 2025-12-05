package com.todo.domain.sort;

import com.todo.domain.Task;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DeadlineSortStrategy implements TaskSortStrategy {
    @Override
    public void sort(List<Task> tasks) {
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                LocalDateTime d1 = t1.getDueAt();
                LocalDateTime d2 = t2.getDueAt();
                if (d1 == null && d2 == null) {
                    return t1.getCreatedAt().compareTo(t2.getCreatedAt());
                }
                if (d1 == null) return 1;
                if (d2 == null) return -1;
                int cmp = d1.compareTo(d2);
                if (cmp != 0) return cmp;
                return t1.getCreatedAt().compareTo(t2.getCreatedAt());
            }
        });
    }
}
