package ru.hogwarts.school2.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school2.exception.FacultyNotFoundException;
import ru.hogwarts.school2.model.Faculty;
import ru.hogwarts.school2.model.Student;
import ru.hogwarts.school2.repository.FacultyRepository;

@Service
public class FacultyService {

    private static final String FACULTY_NOT_FOUND_MESSAGE = "Факультет с таким идентификатором не найден";

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Long addFaculty(Faculty faculty) {
        faculty.setId(0);
        return facultyRepository.save(faculty).getId();
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(FACULTY_NOT_FOUND_MESSAGE));
    }

    public Faculty editFaculty(Faculty faculty) {
        if (!facultyRepository.existsById(faculty.getId())) {
            throw new FacultyNotFoundException(FACULTY_NOT_FOUND_MESSAGE);
        }
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        if (!facultyRepository.existsById(id)) {
            throw new FacultyNotFoundException(FACULTY_NOT_FOUND_MESSAGE);
        }
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findFaculties(String color, String name) {
        boolean hasColor = color != null && !color.isBlank();
        boolean hasName = name != null && !name.isBlank();

        if (hasColor && hasName) {
            return facultyRepository.findByColorIgnoreCaseAndNameIgnoreCase(color, name);
        }
        if (hasColor) {
            return facultyRepository.findByColorIgnoreCase(color);
        }
        if (hasName) {
            return facultyRepository.findByNameIgnoreCase(name);
        }
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByNameOrColor(String search) {
        return facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(search, search);
    }

    public Collection<Student> getFacultyStudents(long id) {
        return findFaculty(id).getStudents();
    }
}