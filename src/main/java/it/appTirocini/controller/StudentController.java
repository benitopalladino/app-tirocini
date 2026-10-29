package it.appTirocini.controller;

import it.appTirocini.dto.StudentDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import it.appTirocini.service.BusinessException;
import it.appTirocini.service.StudentService;

import java.util.UUID;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "students/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("student", new StudentDto());
        return "students/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable UUID id, Model model) {
        model.addAttribute("student", studentService.get(id));
        return "students/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("student") StudentDto dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);
            return "students/form";
        }
        try {
            studentService.save(dto);
        } catch (BusinessException ex) {
            model.addAttribute("error", ex.getMessage());
            return "students/form";
        }
        return "redirect:/students";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable UUID id, RedirectAttributes ra) {
        try {
            studentService.delete(id);
            ra.addFlashAttribute("message", "Studente eliminato");
        } catch (BusinessException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/students";
    }
}
