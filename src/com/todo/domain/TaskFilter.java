package com.todo.domain;

import java.time.LocalDateTime;
import java.util.Set;

public class TaskFilter {
    public String userId;
    public Set<String> tags;
    public Boolean completed;
    public boolean includeFutureTasks;
    public boolean includeCompletedTasks;
    public LocalDateTime visibleOn;

    public TaskFilter(String userId) {
        this.userId = userId;
        this.tags = null;
        this.completed = null;
        this.includeFutureTasks = false;
        this.includeCompletedTasks = false;
        this.visibleOn = null;
    }
}
