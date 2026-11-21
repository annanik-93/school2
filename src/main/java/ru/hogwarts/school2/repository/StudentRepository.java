package ru.hogwarts.school2.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school2.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAge(int age);

    Collection<Student> findByNameIgnoreCase(String name);

    Collection<Student> findByAgeAndNameIgnoreCase(int age, String name);
}