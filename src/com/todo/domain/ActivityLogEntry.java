package com.todo.domain;

import com.todo.domain.enums.ActivityType;

import java.time.LocalDateTime;

public class ActivityLogEntry {
    private final long id;
    private final long taskId;
    private final String userId;
    private final ActivityType type;
    private final LocalDateTime timestamp;
    private final String description;

    public ActivityLogEntry(long id,
                            long taskId,
                            String userId,
                            ActivityType type,
                            LocalDateTime timestamp,
                            String description) {
        this.id = id;
        this.taskId = taskId;
        this.userId = userId;
        this.type = type;
        this.timestamp = timestamp;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public long getTaskId() {
        return taskId;
    }

    public String getUserId() {
        return userId;
    }

    public ActivityType getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ActivityLogEntry{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", userId='" + userId + '\'' +
                ", type=" + type +
                ", timestamp=" + timestamp +
                ", description='" + description + '\'' +
                '}';
    }
}
