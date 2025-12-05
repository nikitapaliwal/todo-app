package com.todo.repository;

import com.todo.domain.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryTaskRepository implements TaskRepository {
    private final Map<Long, Task> tasks = new HashMap<Long, Task>();
    private long idCounter = 1L;

    @Override
    public Task save(Task task) {
        long id = task.getId();
        if (id == 0L) {
            long newId = idCounter++;
            Task newTask = new Task(
                    newId,
                    task.getUserId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getDueAt(),
                    task.getStartAt(),
                    task.getTags()
            );
            tasks.put(newId, newTask);
            return newTask;
        } else {
            Task copy = new Task(task);
            tasks.put(id, copy);
            return copy;
        }
    }

    @Override
    public Optional<Task> findById(long id) {
        Task t = tasks.get(id);
        if (t == null) {
            return Optional.empty();
        }
        return Optional.of(new Task(t));
    }

    @Override
    public boolean delete(long id) {
        return tasks.remove(id) != null;
    }

    @Override
    public List<Task> findAll() {
        List<Task> list = new ArrayList<Task>();
        for (Task t : tasks.values()) {
            list.add(new Task(t));
        }
        return list;
    }
}
