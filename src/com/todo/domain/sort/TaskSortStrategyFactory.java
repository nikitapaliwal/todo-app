package com.todo.domain.sort;

import com.todo.domain.enums.TaskSortOption;

public class TaskSortStrategyFactory {
    public static TaskSortStrategy getStrategy(TaskSortOption option) {
        if (option == TaskSortOption.BY_DEADLINE) {
            return new DeadlineSortStrategy();
        } else {
            return new CreationTimeSortStrategy();
        }
    }
}
