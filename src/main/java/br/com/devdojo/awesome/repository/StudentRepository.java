package br.com.devdojo.awesome.repository;

import br.com.devdojo.awesome.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {

    List<Student> findByNameIgnoreCaseContaining(String name);

    Optional<Student> findById(Long id);

}
