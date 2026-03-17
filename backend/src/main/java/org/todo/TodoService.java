package org.todo;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TodoService {
  @org.springframework.beans.factory.annotation.Autowired
  private TodoRepository todoRepository;

  public List<Todo> getAll() {
    return todoRepository.findAll();
  }

  public Todo getById(Long id) {
    return todoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Todo : " + id + " not found"));
  }

  public Todo create(Todo todo) {
    return todoRepository.save(todo);
  }

  public Todo update(Long id, Todo todo) {
    Todo existingTodo = getById(id);
    existingTodo.setTitle(todo.getTitle());
    existingTodo.setCompleted(todo.isCompleted());
    return todoRepository.save(existingTodo);
  }

  public void delete(Long id) {
    todoRepository.deleteById(id);
  }
}
