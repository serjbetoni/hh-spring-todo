package ru.hh.school.todoapp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hh.school.todoapp.entity.Todo;
import ru.hh.school.todoapp.entity.User;
import ru.hh.school.todoapp.repository.TodoRepository;
import ru.hh.school.todoapp.repository.UserRepository;
import ru.hh.school.todoapp.request.AddTodoRequest;
import ru.hh.school.todoapp.request.AddUserRequest;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserRepository userRepository;
  private final TodoRepository todoRepository;

  private static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "User with id %s does not exist";
  private static final String TODO_NOT_FOUND_EXCEPTION_MESSAGE = "Todo with id %s does not exist";

  public UserController(UserRepository userRepository, TodoRepository todoRepository) {
    this.userRepository = userRepository;
    this.todoRepository = todoRepository;
  }

  @GetMapping("/{userId}")
  public User getUserById(@PathVariable Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException(String.format(USER_NOT_FOUND_EXCEPTION_MESSAGE, userId)));
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
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException(String.format(USER_NOT_FOUND_EXCEPTION_MESSAGE, userId)));
    Todo todo = new Todo();
    todo.setContent(todoRequest.getContent());
    user.getTodoList().add(todo);
    userRepository.save(user);
  }

  @PostMapping("/todos/{todoId}")
  public void toggleTodoCompleted(@PathVariable Long todoId) {
    Todo todo = todoRepository.findById(todoId)
        .orElseThrow(() -> new IllegalArgumentException(String.format(TODO_NOT_FOUND_EXCEPTION_MESSAGE, todoId)));
    todo.setCompleted(!todo.getCompleted());
    todoRepository.save(todo);
  }


  @DeleteMapping("{userId}/todos/{todoId}")
  public void deleteTodo(@PathVariable Long userId, @PathVariable Long todoId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException(String.format(USER_NOT_FOUND_EXCEPTION_MESSAGE, userId)));
    Todo todo = todoRepository.findById(todoId)
        .orElseThrow(() -> new IllegalArgumentException(String.format(TODO_NOT_FOUND_EXCEPTION_MESSAGE, todoId)));
    user.getTodoList().remove(todo);
    todoRepository.delete(todo);
  }

  @DeleteMapping("/{userId}")
  public void deleteUser(@PathVariable Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException(String.format(USER_NOT_FOUND_EXCEPTION_MESSAGE, userId)));
    userRepository.delete(user);
  }
}
