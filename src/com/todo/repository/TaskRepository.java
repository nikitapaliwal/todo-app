package com.todo.repository;

import com.todo.domain.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById(long id);
    boolean delete(long id);
    List<Task> findAll();
}
