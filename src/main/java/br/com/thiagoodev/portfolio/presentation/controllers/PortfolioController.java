package br.com.thiagoodev.portfolio.presentation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {
    @GetMapping("/techs")
    public String techs(Model model) {
        List<Map<String, String>> techs = List.of(
                Map.of("name", "Codemagic", "icon", "/images/codemagic.svg", "url", "https://codemagic.io", "color", "#2396F3"),
                Map.of("name", "Dart", "icon", "/images/dart.svg", "url", "https://dart.dev", "color", "#0175C2"),
                Map.of("name", "Docker", "icon", "/images/docker.svg", "url", "https://www.docker.com", "color", "#0db7ed"),
                Map.of("name", "Firebase", "icon", "/images/firebase.svg", "url", "https://firebase.google.com", "color", "#FFCA28"),
                Map.of("name", "Flutter", "icon", "/images/flutter.svg", "url", "https://flutter.dev", "color", "#02569B"),
                Map.of("name", "Jetpack Compose", "icon", "/images/jetpackcompose.svg", "url", "https://developer.android.com/jetpack/compose", "color", "#4285F4"),
                Map.of("name", "Kotlin", "icon", "/images/kotlin.svg", "url", "https://kotlinlang.org", "color", "#7F52FF"),
                Map.of("name", "Linux", "icon", "/images/linux.svg", "url", "https://www.linux.org", "color", "#FCC624"),
                Map.of("name", "macOS", "icon", "/images/macos.svg", "url", "https://www.apple.com/macos", "color", "#000000"),
                Map.of("name", "Java", "icon", "/images/openjdk.svg", "url", "https://openjdk.org", "color", "#5382A1"),
                Map.of("name", "Python", "icon", "/images/python.svg", "url", "https://www.python.org", "color", "#3776AB"),
                Map.of("name", "Spring", "icon", "/images/spring.svg", "url", "https://spring.io", "color", "#6DB33F")
        );
        model.addAttribute("techs", techs);

        return "fragments/tech-carousel :: tech-carousel";
    }

    @GetMapping("/projects")
    public String index() {
        return "Portfolio";
    }
}
