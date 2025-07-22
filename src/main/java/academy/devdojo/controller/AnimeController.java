package academy.devdojo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = {"v1/animes", "v1/animes/"})
public class AnimeController {

    private List<String> ANIMES = Arrays.asList("Jader", "Debora");

    @GetMapping()
    public List<String> getAnimes() {
        return List.of("Hell's Paradise(Jigokuraku)", "Dr. Stone", "Konosuba");
    }
}
