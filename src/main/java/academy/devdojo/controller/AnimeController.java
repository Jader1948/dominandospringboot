package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = {"v1/animes", "v1/animes/"})
public class AnimeController {

    @GetMapping()
    public List<Anime> getAnimes() {
        return Anime.getAnimes();
    }

    @GetMapping("findByName")
    public List<Anime> findByName(@RequestParam(required = false) String name) {
        var animes = Anime.getAnimes();
        if (name == null || name == "") return animes;
        return Anime.getAnimes().stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
    }

    @GetMapping("{id}")
    public Anime findById(@PathVariable Long id) {
        return Anime.getAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
