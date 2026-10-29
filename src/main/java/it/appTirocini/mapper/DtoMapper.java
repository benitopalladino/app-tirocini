package it.appTirocini.mapper;

import it.appTirocini.dto.CompanyDto;
import it.appTirocini.dto.InternshipDto;
import it.appTirocini.dto.StudentDto;
import it.appTirocini.entity.Company;
import it.appTirocini.entity.Internship;
import it.appTirocini.entity.Student;

public class DtoMapper {

    public static StudentDto toDto(Student s) {
        StudentDto dto = new StudentDto();
        dto.setId(s.getId());
        dto.setMatricola(s.getMatricola());
        dto.setFirstName(s.getFirstName());
        dto.setLastName(s.getLastName());
        dto.setLastName(s.getEmail());
        return dto;
    }

    public static Student toEntity(StudentDto dto) {
        Student s = new Student();
        s.setId(dto.getId());
        s.setMatricola(dto.getMatricola());
        s.setFirstName(dto.getFirstName());
        s.setLastName(dto.getLastName());
        s.setEmail(dto.getEmail());
        return s;
    }

    public static CompanyDto toDto(Company c) {
        CompanyDto dto = new CompanyDto();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setCity(c.getCity());
        dto.setContactName(c.getContactName());
        dto.setContactEmail(c.getContactEmail());
        return dto;
    }

    public static Company toEntity(CompanyDto dto) {
        Company c = new Company();
        c.setId(dto.getId());
        c.setName(dto.getName());
        c.setCity(dto.getCity());
        c.setContactName(dto.getContactName());
        c.setContactEmail(dto.getContactEmail());
        return c;
    }

    public static InternshipDto toDto(Internship i) {
        InternshipDto dto = new InternshipDto();
        dto.setId(i.getId());
        dto.setStudentId(i.getStudent().getId());
        dto.setCompanyId(i.getCompany().getId());
        dto.setStartDate(i.getStartDate());
        dto.setEndDate(i.getEndDate());
        dto.setStatus(i.getStatus());
        dto.setNotes(i.getNotes());
        return dto;
    }

    public static Internship toEntity(InternshipDto dto, Student s, Company c) {
        Internship i = new Internship();
        i.setId(dto.getId());
        i.setStudent(s);
        i.setCompany(c);
        i.setStartDate(dto.getStartDate());
        i.setEndDate(dto.getEndDate());
        i.setStatus(dto.getStatus());
        i.setNotes(dto.getNotes());
        return i;
    }
}
