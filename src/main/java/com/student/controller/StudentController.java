package com.student.controller;

import com.student.model.Student;
import com.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Add a new student
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }
 // Update student
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Student student = studentRepository.findById(id).orElseThrow();
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setCourse(studentDetails.getCourse());
        return studentRepository.save(student);
    }

 // Soft Delete Method
    @DeleteMapping("/{id}")
    public ResponseEntity<?> softDeleteStudent(@PathVariable Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            student.setDeleted(true);
            studentRepository.save(student);
            return ResponseEntity.ok().body("Moved to trash successfully");
        }
        return ResponseEntity.notFound().build();
    }

    // Restore Method
    @PutMapping("/restore/{id}")
    public ResponseEntity<?> restoreStudent(@PathVariable Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            student.setDeleted(false);
            studentRepository.save(student);
            return ResponseEntity.ok().body("Restored successfully");
        }
        return ResponseEntity.notFound().build();
    }
}