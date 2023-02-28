package ru.hh.school.todoapp.controller;

import ru.hh.school.todoapp.entity.Todo;
import ru.hh.school.todoapp.entity.User;
import ru.hh.school.todoapp.repository.TodoRepository;
import ru.hh.school.todoapp.repository.UserRepository;
import ru.hh.school.todoapp.request.AddTodoRequest;
import ru.hh.school.todoapp.request.AddUserRequest;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

  private UserRepository userRepository;
  private TodoRepository todoRepository;

  public UserController(UserRepository userRepository, TodoRepository todoRepository) {
    this.userRepository = userRepository;
    this.todoRepository = todoRepository;
  }

  @GetMapping("/{userId}")
  public User getUserById(@PathVariable Long userId) {
    return userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
  }

  @PostMapping
  public User addUser(@RequestBody AddUserRequest userRequest) {
    User user = new User();
    user.setUsername(userRequest.getUsername());
    user.setPassword(userRequest.getPassword());
    return userRepository.save(user);
  }

  @PostMapping("/{userId}/todos")
  public void addTodo(@PathVariable Long userId, @RequestBody AddTodoRequest todoRequest) {
    User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
    Todo todo = new Todo();
    todo.setContent(todoRequest.getContent());
    user.getTodoList().add(todo);
    userRepository.save(user);
  }

  @PostMapping("/todos/{todoId}")
  public void toggleTodoCompleted(@PathVariable Long todoId) {
    Todo todo = todoRepository.findById(todoId).orElseThrow(NoSuchElementException::new);
    todo.setCompleted(!todo.getCompleted());
    todoRepository.save(todo);
  }


  @DeleteMapping("{userId}/todos/{todoId}")
  public void deleteTodo(@PathVariable Long userId, @PathVariable Long todoId) {
    User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
    Todo todo = todoRepository.findById(todoId).orElseThrow(NoSuchElementException::new);
    user.getTodoList().remove(todo);
    todoRepository.delete(todo);
  }

  @DeleteMapping("/{userId}")
  public void deleteUser(@PathVariable Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException());
    userRepository.delete(user);
  }

}
