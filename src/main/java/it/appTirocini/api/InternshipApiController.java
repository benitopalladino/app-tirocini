package it.appTirocini.api;

import it.appTirocini.dto.InternshipDto;
import it.appTirocini.entity.Internship;
import it.appTirocini.mapper.DtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import it.appTirocini.service.InternshipService;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/internships")
public class InternshipApiController {

    private final InternshipService service;

    public InternshipApiController(InternshipService service) {
        this.service = service;
    }

    @GetMapping
    public Page<InternshipDto> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Internship.InternshipStatus status,
            @RequestParam(required = false) UUID companyId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false)
            @DateTimeFormat(iso =DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Internship> result = service.search(companyId, status, from, to, pageable);

        return result.map(DtoMapper::toDto);
    }
}
