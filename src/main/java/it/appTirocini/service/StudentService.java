package it.appTirocini.service;

import it.appTirocini.dto.StudentDto;
import it.appTirocini.entity.Internship;
import it.appTirocini.entity.Student;
import it.appTirocini.mapper.DtoMapper;
import org.springframework.stereotype.Service;
import it.appTirocini.repository.InternshipRepository;
import it.appTirocini.repository.StudentRepository;

import java.util.List;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final InternshipRepository internshipRepository;

    public StudentService(StudentRepository studentRepository,
                          InternshipRepository internshipRepository) {
        this.studentRepository = studentRepository;
        this.internshipRepository = internshipRepository;
    }

    public StudentDto get(UUID id) {
        Student s = studentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Studente non trovato"));
        return DtoMapper.toDto(s);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student save(StudentDto dto) {

        if (dto.getId() == null && studentRepository.existsByMatricola(dto.getMatricola())) {
            throw new BusinessException("Matricola già esistente");
        }

        Student s = DtoMapper.toEntity(dto);
        return studentRepository.save(s);
    }

    public void delete(UUID id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Studente non trovato"));

        List<Internship> internships = internshipRepository.findByStudentId(id);

        boolean hasActiveOrCompleted = internships.stream()
                .anyMatch(i -> i.getStatus() == Internship.InternshipStatus.ACTIVE ||
                                         i.getStatus() == Internship.InternshipStatus.COMPLETED);

        if (hasActiveOrCompleted) {
            throw new BusinessException("Non puoi eliminare lo studente: tirocini ACTIVE/COMPLETED presenti.");
        }

        internships.forEach(internshipRepository::delete);

        studentRepository.delete(student);
    }
}
