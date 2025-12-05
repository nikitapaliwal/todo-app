package com.todo.repository;

import com.todo.domain.ActivityLogEntry;

import java.util.ArrayList;
import java.util.List;

public class InMemoryActivityLogRepository implements ActivityLogRepository {
    private final List<ActivityLogEntry> entries = new ArrayList<ActivityLogEntry>();
    private long idCounter = 1L;

    @Override
    public ActivityLogEntry save(ActivityLogEntry entry) {
        ActivityLogEntry e = new ActivityLogEntry(
                idCounter++,
                entry.getTaskId(),
                entry.getUserId(),
                entry.getType(),
                entry.getTimestamp(),
                entry.getDescription()
        );
        entries.add(e);
        return e;
    }

    @Override
    public List<ActivityLogEntry> findAll() {
        return new ArrayList<ActivityLogEntry>(entries);
    }
}
