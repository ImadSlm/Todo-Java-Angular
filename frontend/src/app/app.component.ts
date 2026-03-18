import { Component, OnInit } from '@angular/core';
import { TodoService } from './services/todo.service';
import { Todo } from './models/todo.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  title = 'Todo App';

  todos: Todo[] = [];
  newTodo: Todo = { title: '', completed: false };

  constructor(private todoService: TodoService) {}

  ngOnInit() {
    this.loadTodos();
  }

  loadTodos() {
    this.todoService.getTodos().subscribe((todos) => {
      this.todos = todos;
    });
  }

  addTodo() {
    if (!this.newTodo.title.trim()) {
      return;
    }
    this.todoService.createTodo(this.newTodo).subscribe((createdTodo) => {
      this.todos.push(createdTodo);
      this.newTodo = { title: '', completed: false };
    });
  }

  toggleTodo(todo: Todo) {
    if (!todo.id) return;
    todo.completed = !todo.completed;
    this.todoService.updateTodo(todo.id, todo).subscribe();
  }

  deleteTodo(id: number | undefined) {
    if (!id) return;
    this.todoService.deleteTodo(id).subscribe(() => {
      this.todos = this.todos.filter((t) => t.id !== id);
    });
  }
}
