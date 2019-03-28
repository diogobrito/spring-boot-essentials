package br.com.devdojo.awesome.endpoint;

import br.com.devdojo.awesome.error.CustomErrorType;
import br.com.devdojo.awesome.error.ResourceNotFoundException;
import br.com.devdojo.awesome.model.Student;
import br.com.devdojo.awesome.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("students")
public class StudentEndpoint {


    private final StudentRepository studentDAO;
    @Autowired
    public StudentEndpoint(StudentRepository studentDAO) {
        this.studentDAO = studentDAO;
    }

    @GetMapping
    public ResponseEntity<?> listAll() {

        return new ResponseEntity<>(studentDAO.findAll(), HttpStatus.OK) ;

    }

    @RequestMapping(path = "/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") long id) {
        verifyIfStudentExists(id);

        Optional<Student> student = studentDAO.findById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findStudentByName(@PathVariable String name) {
        return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Student student) {

        return new ResponseEntity<>(studentDAO.save(student), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Student student) {
        verifyIfStudentExists(student.getId());
        studentDAO.delete(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student) {
        verifyIfStudentExists(student.getId());
        studentDAO.save(student);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void verifyIfStudentExists(Long id) {

        if (!studentDAO.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Student not found fot ID: " + id);
        }
    }

}
