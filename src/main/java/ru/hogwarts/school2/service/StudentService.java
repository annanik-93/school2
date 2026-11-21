package ru.hogwarts.school2.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school2.exception.StudentNotFoundException;
import ru.hogwarts.school2.model.Student;
import ru.hogwarts.school2.repository.StudentRepository;

@Service
public class StudentService {

    private static final String STUDENT_NOT_FOUND_MESSAGE = "Студент с таким идентификатором не найден";

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Long addStudent(Student student) {
        student.setId(0);
        return studentRepository.save(student).getId();
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(STUDENT_NOT_FOUND_MESSAGE));
    }

    public Student editStudent(Student student) {
        if (!studentRepository.existsById(student.getId())) {
            throw new StudentNotFoundException(STUDENT_NOT_FOUND_MESSAGE);
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(STUDENT_NOT_FOUND_MESSAGE);
        }
        studentRepository.deleteById(id);
    }

    public Collection<Student> findStudents(Integer age, String name) {
        boolean hasAge = age != null;
        boolean hasName = name != null && !name.isBlank();

        if (hasAge && hasName) {
            return studentRepository.findByAgeAndNameIgnoreCase(age, name);
        }
        if (hasAge) {
            return studentRepository.findByAge(age);
        }
        if (hasName) {
            return studentRepository.findByNameIgnoreCase(name);
        }
        return studentRepository.findAll();
    }
}