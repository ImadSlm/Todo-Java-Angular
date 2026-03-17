package org.todo;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
public class TodoController {
  @org.springframework.beans.factory.annotation.Autowired
  private TodoService todoService;

  @GetMapping("/todos/{id}")
  public Todo getById(@PathVariable Long id) {
    return todoService.getById(id);
    }
    
  @GetMapping("/todos")
  public List<Todo> getAll() {
    return todoService.getAll();
  }
  
  @PostMapping("/todos")
  @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
  public Todo create(@RequestBody Todo todo) {
    return todoService.create(todo);
  }
  
  @PutMapping("/todos/{id}")
  public Todo update(@PathVariable Long id, @RequestBody Todo todo) {
    return todoService.update(id, todo);
  }
  
  @DeleteMapping("/todos/{id}")
  @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    todoService.delete(id);
  }
}
