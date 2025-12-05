package com.todo.domain;

public class TaskStatistics {
    private final int tasksAdded;
    private final int tasksCompleted;
    private final int tasksSpilledOverDeadline;

    public TaskStatistics(int tasksAdded, int tasksCompleted, int tasksSpilledOverDeadline) {
        this.tasksAdded = tasksAdded;
        this.tasksCompleted = tasksCompleted;
        this.tasksSpilledOverDeadline = tasksSpilledOverDeadline;
    }

    public int getTasksAdded() {
        return tasksAdded;
    }

    public int getTasksCompleted() {
        return tasksCompleted;
    }

    public int getTasksSpilledOverDeadline() {
        return tasksSpilledOverDeadline;
    }

    @Override
    public String toString() {
        return "TaskStatistics{" +
                "tasksAdded=" + tasksAdded +
                ", tasksCompleted=" + tasksCompleted +
                ", tasksSpilledOverDeadline=" + tasksSpilledOverDeadline +
                '}';
    }
}
