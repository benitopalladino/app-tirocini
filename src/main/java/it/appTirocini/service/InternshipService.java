package it.appTirocini.service;

import it.appTirocini.dto.InternshipDto;
import it.appTirocini.entity.Company;
import it.appTirocini.entity.Internship;
import it.appTirocini.entity.Student;
import it.appTirocini.mapper.DtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.appTirocini.repository.CompanyRepository;
import it.appTirocini.repository.InternshipRepository;
import it.appTirocini.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class InternshipService {

    private final InternshipRepository internshipRepository;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;

    public InternshipService(InternshipRepository internshipRepository,
                             StudentRepository studentRepository,
                             CompanyRepository companyRepository) {
        this.internshipRepository = internshipRepository;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
    }

    public InternshipDto get(UUID id) {
        Internship i = internshipRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Tirocinio non trovato"));
        return DtoMapper.toDto(i);
    }

    public Internship save(InternshipDto dto) {

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new BusinessException("La data di fine deve essere >= della data di inizio");
        }

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new BusinessException("Studente non trovato"));

        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new BusinessException("Azienda non trovata"));

        Internship internship = (dto.getId() == null) ? new Internship() : internshipRepository.findById(dto.getId())
                .orElseThrow(() -> new BusinessException("Tirocinio non trovato"));

        validateStatusTransition(internship.getId() == null ? null : internship.getStatus(), dto.getStatus());

        if (dto.getStatus() == Internship.InternshipStatus.ACTIVE) {
            validateOverlaps(student.getId(), dto.getStartDate(), dto.getEndDate(), internship.getId());
        }

        internship.setStudent(student);
        internship.setCompany(company);
        internship.setStartDate(dto.getStartDate());
        internship.setEndDate(dto.getEndDate());
        internship.setStatus(dto.getStatus());
        internship.setNotes(dto.getNotes());

        return internshipRepository.save(internship);
    }

    private void validateStatusTransition(Internship.InternshipStatus oldStatus, Internship.InternshipStatus newStatus) {
        if (oldStatus == null) return;

        if (oldStatus == newStatus) return;

        if (oldStatus == Internship.InternshipStatus.PROPOSED && newStatus == Internship.InternshipStatus.ACTIVE) return;
        if (oldStatus == Internship.InternshipStatus.ACTIVE && newStatus == Internship.InternshipStatus.COMPLETED) return;

        throw new BusinessException("Transizione di stato non permessa");
    }

    private void validateOverlaps(UUID studentId, LocalDate start, LocalDate end, UUID currentId) {

        List<Internship> overlaps = internshipRepository.findActiveOverlaps(studentId,start, end);

        if (currentId != null) {
            overlaps.removeIf(i -> i.getId().equals(currentId));
        }

        if (!overlaps.isEmpty()) {
            throw new BusinessException("Lo studente ha già un tirocinio ACTIVE sovrapposto");
        }
    }

    public void delete(UUID id) {
        Internship i = internshipRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Tirocinio non trovato"));

        internshipRepository.delete(i);
    }

    public Page<Internship> search(UUID companyId, Internship.InternshipStatus status, LocalDate from, LocalDate to, Pageable pageable) {
        return internshipRepository.search(companyId, status, from, to, pageable);
    }
}
