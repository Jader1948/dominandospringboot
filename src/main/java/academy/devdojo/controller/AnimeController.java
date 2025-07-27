package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping(path = {"v1/animes", "v1/animes/"})
@Log4j2
public class AnimeController {

    @GetMapping()
    public List<Anime> getAnimes() {
        return Anime.getAnimes();
    }

    @GetMapping("findByName")
    public List<Anime> findByName(@RequestParam(required = false) String name) throws JsonProcessingException {
        log.info("Request received  to list all animes, param name'{}'", name);
        var animes = Anime.getAnimes();
        //log.info("'{}'", new ObjectMapper().writeValueAsString(animes.get(0)));
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

    // todos metodos Post nao são indepotentesdevido que alterar o status do servido,
    //indepotentes são somente os endepoint que não alteram o status do servidor.
    @PostMapping
    public Anime save(@RequestBody Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(100_000));
        Anime.getAnimes().add(anime);
        return anime;
    }
}
