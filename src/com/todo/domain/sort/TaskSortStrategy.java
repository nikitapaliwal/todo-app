package com.todo.domain.sort;

import com.todo.domain.Task;

import java.util.List;

public interface TaskSortStrategy {
    void sort(List<Task> tasks);
}
