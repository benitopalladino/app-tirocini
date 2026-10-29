package it.appTirocini.api;

import it.appTirocini.dto.StudentDto;
import it.appTirocini.entity.Student;
import it.appTirocini.mapper.DtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import it.appTirocini.repository.StudentRepository;

@RestController
@RequestMapping("/api/students")
public class StudentApiController {

    private final StudentRepository repo;

    public StudentApiController(StudentRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Page<StudentDto> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String q
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Student> result;

        if (q != null && !q.isBlank()) {
            result = repo.findByLastNameContainingIgnoreCase(q, pageable);
        } else {
            result = repo.findAll(pageable);
        }
        return result.map(DtoMapper::toDto);
    }
}
