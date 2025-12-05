package com.todo.driver;

import com.todo.domain.ActivityLogEntry;
import com.todo.domain.Task;
import com.todo.domain.TaskFilter;
import com.todo.domain.TaskStatistics;
import com.todo.domain.TimePeriod;
import com.todo.domain.enums.TaskSortOption;
import com.todo.repository.ActivityLogRepository;
import com.todo.repository.InMemoryActivityLogRepository;
import com.todo.repository.InMemoryTaskRepository;
import com.todo.repository.TaskRepository;
import com.todo.service.TodoService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class TodoAppDriver {
    public static void main(String[] args) {
        TaskRepository taskRepo = new InMemoryTaskRepository();
        ActivityLogRepository activityRepo = new InMemoryActivityLogRepository();
        TodoService todoService = new TodoService(taskRepo, activityRepo);

        String userA = "userA";
        String userB = "userB";

        Task task1 = new Task(
                0L,
                userA,
                "Finish machine coding round",
                "Implement TODO app in Java",
                LocalDateTime.now().plusHours(4),
                null,
                new HashSet<String>(Arrays.asList("work", "coding"))
        );
        Task saved1 = todoService.addTask(task1);

        Task task2 = new Task(
                0L,
                userA,
                "Buy groceries",
                "Milk, eggs, bread",
                LocalDateTime.now().plusDays(1),
                null,
                new HashSet<String>(Arrays.asList("personal", "home"))
        );
        Task saved2 = todoService.addTask(task2);

        Task task3 = new Task(
                0L,
                userB,
                "Go to gym",
                "Leg day",
                null,
                LocalDateTime.now().plusDays(2),
                new HashSet<String>(Arrays.asList("health"))
        );
        Task saved3 = todoService.addTask(task3);

        Task modified2 = new Task(
                saved2.getId(),
                saved2.getUserId(),
                saved2.getTitle(),
                "Milk, eggs, bread, fruits",
                saved2.getDueAt(),
                saved2.getStartAt(),
                saved2.getTags()
        );
        todoService.modifyTask(modified2);

        Task complete1 = new Task(
                saved1.getId(),
                saved1.getUserId(),
                saved1.getTitle(),
                saved1.getDescription(),
                saved1.getDueAt(),
                saved1.getStartAt(),
                saved1.getTags()
        );
        complete1.markCompleted();
        todoService.modifyTask(complete1);

        todoService.removeTask(saved3.getId());

        TaskFilter filterUserA = new TaskFilter(userA);
        List<Task> activeTasksUserA = todoService.listTasks(filterUserA, TaskSortOption.BY_DEADLINE);
        System.out.println("Active tasks for userA:");
        for (Task t : activeTasksUserA) {
            System.out.println(t);
        }

        TaskFilter filterUserAAll = new TaskFilter(userA);
        filterUserAAll.includeCompletedTasks = true;
        List<Task> allTasksUserA = todoService.listTasks(filterUserAAll, TaskSortOption.BY_CREATED_AT);
        System.out.println("\nAll tasks for userA (including completed):");
        for (Task t : allTasksUserA) {
            System.out.println(t);
        }

        System.out.println("\nActivity log (all time):");
        List<ActivityLogEntry> logAll = todoService.getActivityLog(null);
        for (ActivityLogEntry e : logAll) {
            System.out.println(e);
        }

        TaskStatistics statsAll = todoService.getStatistics(null);
        System.out.println("\nStatistics (all time):");
        System.out.println(statsAll);
    }
}
