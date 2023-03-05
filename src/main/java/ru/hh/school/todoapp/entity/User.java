package ru.hh.school.todoapp.entity;


import ru.hh.school.todoapp.utils.PassHash;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String username;
  private String password;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Todo> todoList = new ArrayList<>();

  public User() {
  }

  public User(String username, String password) {
    this.username = username;
    this.password = PassHash.getHash(password);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = PassHash.getHash(password);
  }

  public List<Todo> getTodoList() {
    return todoList;
  }

  public void setTodoList(List<Todo> todoList) {
    this.todoList = todoList;
  }
}
