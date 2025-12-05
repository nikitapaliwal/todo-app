package com.todo.service;

import com.todo.domain.ActivityLogEntry;
import com.todo.domain.Task;
import com.todo.domain.TaskFilter;
import com.todo.domain.TaskStatistics;
import com.todo.domain.TimePeriod;
import com.todo.domain.enums.ActivityType;
import com.todo.domain.enums.TaskSortOption;
import com.todo.domain.sort.TaskSortStrategy;
import com.todo.domain.sort.TaskSortStrategyFactory;
import com.todo.repository.ActivityLogRepository;
import com.todo.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TodoService {
    private final TaskRepository taskRepository;
    private final ActivityLogService activityLogService;
    private final StatisticsService statisticsService;

    public TodoService(TaskRepository taskRepository,
                       ActivityLogRepository activityLogRepository) {
        this.taskRepository = taskRepository;
        this.activityLogService = new ActivityLogService(activityLogRepository);
        this.statisticsService = new StatisticsService(taskRepository, activityLogRepository);
    }

    public Task addTask(Task task) {
        Task saved = taskRepository.save(task);
        activityLogService.log(saved.getId(), saved.getUserId(),
                ActivityType.ADD, "Task added: " + saved.getTitle());
        return saved;
    }

    public Optional<Task> getTask(long taskId) {
        return taskRepository.findById(taskId);
    }

    public Task modifyTask(Task updatedTask) {
        Optional<Task> existingOpt = taskRepository.findById(updatedTask.getId());
        if (!existingOpt.isPresent()) {
            throw new IllegalArgumentException("Task not found: " + updatedTask.getId());
        }
        Task existing = existingOpt.get();

        existing.setTitle(updatedTask.getTitle());
        existing.setDescription(updatedTask.getDescription());
        existing.setDueAt(updatedTask.getDueAt());
        existing.setStartAt(updatedTask.getStartAt());
        existing.setTags(updatedTask.getTags());

        boolean wasCompleted = existing.isCompleted();
        boolean shouldBeCompleted = updatedTask.isCompleted();

        if (!wasCompleted && shouldBeCompleted) {
            existing.markCompleted();
            activityLogService.log(existing.getId(), existing.getUserId(),
                    ActivityType.COMPLETE, "Task completed: " + existing.getTitle());
        } else {
            activityLogService.log(existing.getId(), existing.getUserId(),
                    ActivityType.MODIFY, "Task modified: " + existing.getTitle());
        }

        Task saved = taskRepository.save(existing);
        return saved;
    }

    public boolean removeTask(long taskId) {
        Optional<Task> existingOpt = taskRepository.findById(taskId);
        if (!existingOpt.isPresent()) {
            return false;
        }
        Task t = existingOpt.get();
        boolean deleted = taskRepository.delete(taskId);
        if (deleted) {
            activityLogService.log(taskId, t.getUserId(),
                    ActivityType.REMOVE, "Task removed: " + t.getTitle());
        }
        return deleted;
    }

    public List<Task> listTasks(TaskFilter filter, TaskSortOption sortOption) {
        List<Task> all = taskRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime visibleTime = (filter.visibleOn != null) ? filter.visibleOn : now;

        List<Task> result = new ArrayList<Task>();
        for (Task t : all) {
            if (filter.userId != null && !filter.userId.equals(t.getUserId())) {
                continue;
            }

            if (!filter.includeCompletedTasks && t.isCompleted()) {
                continue;
            }
            if (filter.completed != null && t.isCompleted() != filter.completed.booleanValue()) {
                continue;
            }

            LocalDateTime startAt = t.getStartAt();
            if (!filter.includeFutureTasks) {
                if (startAt != null && startAt.isAfter(visibleTime)) {
                    continue;
                }
            }

            if (filter.tags != null && !filter.tags.isEmpty()) {
                if (!t.getTags().containsAll(filter.tags)) {
                    continue;
                }
            }

            result.add(t);
        }

        TaskSortStrategy strategy = TaskSortStrategyFactory.getStrategy(sortOption);
        strategy.sort(result);
        return result;
    }

    public TaskStatistics getStatistics(TimePeriod period) {
        if (period == null) {
            period = TimePeriod.allTime();
        }
        return statisticsService.getStatistics(period);
    }

    public List<ActivityLogEntry> getActivityLog(TimePeriod period) {
        if (period == null) {
            period = TimePeriod.allTime();
        }
        return activityLogService.getActivityLog(period);
    }
}
