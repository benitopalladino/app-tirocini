package it.appTirocini.controller;

import it.appTirocini.dto.InternshipDto;
import it.appTirocini.entity.Internship;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import it.appTirocini.repository.CompanyRepository;
import it.appTirocini.repository.StudentRepository;
import it.appTirocini.service.BusinessException;
import it.appTirocini.service.InternshipService;

import java.time.LocalDate;
import java.util.UUID;

@Controller
@RequestMapping("/internships")
public class InternshipController {

    private final InternshipService internshipService;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;

    public InternshipController(InternshipService internshipService,
                                StudentRepository studentRepository,
                                CompanyRepository companyRepository) {
        this.internshipService = internshipService;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public String list(
            @RequestParam(required = false)UUID companyId,
            @RequestParam(required = false)Internship.InternshipStatus status,
            @RequestParam(required = false)LocalDate from,
            @RequestParam(required = false)LocalDate to,
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            Model model
            ) {
        Page<?> result = internshipService.search(companyId, status, from, to, PageRequest.of(page, size));

        model.addAttribute("internships", result);
        model.addAttribute("companies", companyRepository.findAll());
        model.addAttribute("statuses", Internship.InternshipStatus.values());
        model.addAttribute("companyId", companyId);
        model.addAttribute("status", status);
        model.addAttribute("from", from);
        model.addAttribute("to", to);

        return "internships/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("internship", new InternshipDto());
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("companies", companyRepository.findAll());
        model.addAttribute("statuses", Internship.InternshipStatus.values());
        return "internships/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable UUID id, Model model) {
        model.addAttribute("internship", internshipService.get(id));
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("companies", companyRepository.findAll());
        model.addAttribute("statuses", Internship.InternshipStatus.values());
        return "internships/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("internship") InternshipDto dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);
            model.addAttribute("students", studentRepository.findAll());
            model.addAttribute("companies", companyRepository.findAll());
            model.addAttribute("statuses", Internship.InternshipStatus.values());
            return "internships/form";
        }
        try {
            internshipService.save(dto);
        } catch (BusinessException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("students", studentRepository.findAll());
            model.addAttribute("companies", companyRepository.findAll());
            model.addAttribute("statuses", Internship.InternshipStatus.values());
            return "internships/form";
        }
        return "redirect:/internships";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable UUID id, RedirectAttributes ra) {
        try {
            internshipService.delete(id);
            ra.addFlashAttribute("message", "Tirocinio eliminato");
        } catch (BusinessException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/internships";
    }
}
