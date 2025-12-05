package com.todo.service;

import com.todo.domain.ActivityLogEntry;
import com.todo.domain.Task;
import com.todo.domain.TaskStatistics;
import com.todo.domain.TimePeriod;
import com.todo.domain.enums.ActivityType;
import com.todo.repository.ActivityLogRepository;
import com.todo.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

public class StatisticsService {
    private final TaskRepository taskRepository;
    private final ActivityLogRepository activityLogRepository;

    public StatisticsService(TaskRepository taskRepository,
                             ActivityLogRepository activityLogRepository) {
        this.taskRepository = taskRepository;
        this.activityLogRepository = activityLogRepository;
    }

    public TaskStatistics getStatistics(TimePeriod period) {
        List<ActivityLogEntry> activities = activityLogRepository.findAll();

        int added = 0;
        int completed = 0;
        for (ActivityLogEntry e : activities) {
            if (!period.contains(e.getTimestamp())) {
                continue;
            }
            if (e.getType() == ActivityType.ADD) {
                added++;
            } else if (e.getType() == ActivityType.COMPLETE) {
                completed++;
            }
        }

        int spilled = calculateSpilledOver(period);

        return new TaskStatistics(added, completed, spilled);
    }

    private int calculateSpilledOver(TimePeriod period) {
        List<Task> tasks = taskRepository.findAll();
        LocalDateTime periodEnd = period.getTo();
        int count = 0;
        for (Task t : tasks) {
            LocalDateTime deadline = t.getDueAt();
            if (deadline == null) {
                continue;
            }
            if (!period.contains(deadline)) {
                continue;
            }

            boolean spilled = false;
            if (t.isCompleted()) {
                LocalDateTime completedAt = t.getCompletedAt();
                if (completedAt != null && completedAt.isAfter(deadline)) {
                    spilled = true;
                }
            } else {
                if (periodEnd.isAfter(deadline)) {
                    spilled = true;
                }
            }

            if (spilled) {
                count++;
            }
        }
        return count;
    }
}
