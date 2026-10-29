package it.appTirocini.service;

import it.appTirocini.dto.CompanyDto;
import it.appTirocini.entity.Company;
import it.appTirocini.mapper.DtoMapper;
import org.springframework.stereotype.Service;
import it.appTirocini.repository.CompanyRepository;
import it.appTirocini.repository.InternshipRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final InternshipRepository internshipRepository;

    public CompanyService(CompanyRepository companyRepository,
                          InternshipRepository internshipRepository) {
        this.companyRepository = companyRepository;
        this.internshipRepository = internshipRepository;
    }

    public CompanyDto get(UUID id) {
        Company c = companyRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Azienda non trovata"));
        return DtoMapper.toDto(c);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company save(CompanyDto dto) {

        if (dto.getId() == null && companyRepository.existsByName(dto.getName())) {
            throw new BusinessException("Nome azienda già esistente");
        }

        Company c = DtoMapper.toEntity(dto);
        return companyRepository.save(c);
    }

    public void delete(UUID id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Azienda non trovata"));

        boolean hasInternships = internshipRepository.existsByCompanyId(id);

        if (hasInternships) {
            throw new BusinessException("Non puoi eliminare l'azienda: ha tirocini associati.");
        }

        companyRepository.delete(company);
    }
}
