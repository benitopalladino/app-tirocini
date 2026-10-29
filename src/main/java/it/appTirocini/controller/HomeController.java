package it.appTirocini.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import it.appTirocini.repository.CompanyRepository;
import it.appTirocini.repository.InternshipRepository;
import it.appTirocini.repository.StudentRepository;

@Controller
public class HomeController {

    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    private final InternshipRepository internshipRepository;

    public HomeController(StudentRepository studentRepository,
                          CompanyRepository companyRepository,
                          InternshipRepository internshipRepository) {
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
        this.internshipRepository = internshipRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("studentCount", studentRepository.count());
        model.addAttribute("companyCount", companyRepository.count());
        model.addAttribute("internshipCount", internshipRepository.count());
        return "home";
    }
}
