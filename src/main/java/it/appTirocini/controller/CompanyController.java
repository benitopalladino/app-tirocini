package it.appTirocini.controller;

import it.appTirocini.dto.CompanyDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import it.appTirocini.service.BusinessException;
import it.appTirocini.service.CompanyService;

import java.util.UUID;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("companies", companyService.findAll());
        return "companies/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("company", new CompanyDto());
        return "companies/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable UUID id, Model model) {
        model.addAttribute("company", companyService.get(id));
        return "companies/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("company") CompanyDto dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);
            return "companies/form";
        }
        try {
            companyService.save(dto);
        } catch (BusinessException ex) {
            model.addAttribute("error", ex.getMessage());
            return "companies/form";
        }
        return "redirect:/companies";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable UUID id, RedirectAttributes ra) {
        try {
            companyService.delete(id);
            ra.addFlashAttribute("message", "Azienda eliminata");
        } catch (BusinessException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/companies";
    }
}
