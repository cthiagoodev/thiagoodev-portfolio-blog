package br.com.thiagoodev.blogapi.presentation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {
    @GetMapping("/")
    public String index() {
        return "Portfolio";
    }

    @GetMapping("/clicked")
    @ResponseBody
    public String clicked() {
        return "<button hx-get='/portfolio/clicked' hx-swap='outerHTML'>Other Click Me</button>";
    }
}
