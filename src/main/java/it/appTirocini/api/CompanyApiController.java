package it.appTirocini.api;

import it.appTirocini.dto.CompanyDto;
import it.appTirocini.entity.Company;
import it.appTirocini.mapper.DtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import it.appTirocini.repository.CompanyRepository;

@RestController
@RequestMapping("/api/companies")
public class CompanyApiController {

    private final CompanyRepository repo;

    public CompanyApiController(CompanyRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Page<CompanyDto> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String q
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Company> result;

        if (q != null && !q.isBlank()) {
            result = repo.findByNameContainingIgnoreCase(q, pageable);
        } else {
            result = repo.findAll(pageable);
        }
        return result.map(DtoMapper::toDto);
    }
}
