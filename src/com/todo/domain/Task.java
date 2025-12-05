package com.todo.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Task {
    private final long id;
    private final String userId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime dueAt;
    private LocalDateTime startAt;
    private boolean completed;
    private LocalDateTime completedAt;
    private Set<String> tags;

    // For new tasks: pass id = 0
    public Task(long id,
                String userId,
                String title,
                String description,
                LocalDateTime dueAt,
                LocalDateTime startAt,
                Set<String> tags) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.dueAt = dueAt;
        this.startAt = startAt;
        this.tags = (tags == null) ? new HashSet<String>() : new HashSet<String>(tags);
        this.createdAt = LocalDateTime.now();
        this.completed = false;
        this.completedAt = null;
    }

    public Task(Task other) {
        this.id = other.id;
        this.userId = other.userId;
        this.title = other.title;
        this.description = other.description;
        this.createdAt = other.createdAt;
        this.dueAt = other.dueAt;
        this.startAt = other.startAt;
        this.completed = other.completed;
        this.completedAt = other.completedAt;
        this.tags = new HashSet<String>(other.tags);
    }

    public long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDueAt() {
        return dueAt;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public Set<String> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueAt(LocalDateTime dueAt) {
        this.dueAt = dueAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public void setTags(Set<String> tags) {
        this.tags = new HashSet<String>(tags);
    }

    public void markCompleted() {
        if (!this.completed) {
            this.completed = true;
            this.completedAt = LocalDateTime.now();
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", dueAt=" + dueAt +
                ", startAt=" + startAt +
                ", completed=" + completed +
                ", tags=" + tags +
                '}';
    }
}
