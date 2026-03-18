package org.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Main.class)
public class TodoRepositoryTest {
  @Autowired
  private TodoRepository repository;

  @Test
  public void testRepository() {
    Todo todo = new Todo();
    todo.setTitle("Test todo");
    todo.setCompleted(false);
    repository.save(todo);
    assertEquals("Test todo", todo.getTitle());
    assertEquals(false, todo.isCompleted());

    todo.setCompleted(true);
    repository.save(todo);
    assertEquals("Test todo", todo.getTitle());
    assertEquals(true, todo.isCompleted());

    int initialSize = repository.findAll().size();
    repository.delete(todo);
    assertEquals(initialSize - 1, repository.findAll().size());
  }
}
