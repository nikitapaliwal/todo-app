package com.todo.service;

import com.todo.domain.ActivityLogEntry;
import com.todo.domain.TimePeriod;
import com.todo.domain.enums.ActivityType;
import com.todo.repository.ActivityLogRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ActivityLogService {
    private final ActivityLogRepository activityLogRepository;

    public ActivityLogService(ActivityLogRepository repo) {
        this.activityLogRepository = repo;
    }

    public void log(long taskId, String userId, ActivityType type, String description) {
        ActivityLogEntry entry = new ActivityLogEntry(
                0L,
                taskId,
                userId,
                type,
                LocalDateTime.now(),
                description
        );
        activityLogRepository.save(entry);
    }

    public List<ActivityLogEntry> getActivityLog(TimePeriod period) {
        List<ActivityLogEntry> all = activityLogRepository.findAll();
        List<ActivityLogEntry> result = new ArrayList<ActivityLogEntry>();
        for (ActivityLogEntry e : all) {
            if (period.contains(e.getTimestamp())) {
                result.add(e);
            }
        }
        Collections.sort(result, new Comparator<ActivityLogEntry>() {
            @Override
            public int compare(ActivityLogEntry o1, ActivityLogEntry o2) {
                return o1.getTimestamp().compareTo(o2.getTimestamp());
            }
        });
        return result;
    }
}
