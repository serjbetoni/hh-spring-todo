package ru.hh.school.todoapp.repository;

import ru.hh.school.todoapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
