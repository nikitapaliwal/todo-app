package com.todo.repository;

import com.todo.domain.ActivityLogEntry;

import java.util.List;

public interface ActivityLogRepository {
    ActivityLogEntry save(ActivityLogEntry entry);
    List<ActivityLogEntry> findAll();
}
