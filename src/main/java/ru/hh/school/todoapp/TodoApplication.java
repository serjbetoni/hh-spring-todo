package ru.hh.school.todoapp;

import ru.hh.school.todoapp.entity.Todo;
import ru.hh.school.todoapp.entity.User;
import ru.hh.school.todoapp.repository.TodoRepository;
import ru.hh.school.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoApplication implements CommandLineRunner {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TodoRepository todoRepository;

  public static void main(String[] args) {
    SpringApplication.run(TodoApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    User user = new User();
    //pass hashing in DB
    user.setPassword("pass");
    user.setUsername("hh_user");

    Todo todo = new Todo();
    todo.setContent("resolve homework");

    user.getTodoList().add(todo);

    userRepository.save(user);
  }
}
