package ru.hogwarts.school2.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school2.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findByColorIgnoreCase(String color);

    Collection<Faculty> findByNameIgnoreCase(String name);

    Collection<Faculty> findByColorIgnoreCaseAndNameIgnoreCase(String color, String name);

    Collection<Faculty> findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(String name, String color);
}