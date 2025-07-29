package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.request.AnimePostRequest;
import academy.devdojo.response.AnimeGetResponse;
import academy.devdojo.response.AnimePostResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = {"v1/animes", "v1/animes/"})
@Log4j2
public class AnimeController {

    private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE;

    @GetMapping()
    public ResponseEntity<List<AnimeGetResponse>> getAnimes() {
        var animes = Anime.getAnimes();
        var animeResponses = MAPPER.toAnimeGetResposes(animes);
        return ResponseEntity.ok(animeResponses);
    }

    @GetMapping("findByName")
    public ResponseEntity<List<AnimeGetResponse>> findByName(@RequestParam(required = false) String name) throws JsonProcessingException {
        log.info("Request received  to list all animes, param name '{}'", name);
        var animes = Anime.getAnimes();
        var animesGetResponses = MAPPER.toAnimeGetResposes(animes);
        if (name == null || name == "") return ResponseEntity.ok(animesGetResponses);
        animesGetResponses = animesGetResponses.stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
        return ResponseEntity.ok(animesGetResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        log.info("Request received find anime by id '{}'", id);
        var animeFound = Anime.getAnimes().stream().filter(anime -> anime.getId().equals(id)).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
        var response = MAPPER.toAnimeGetResponse(animeFound);
        return ResponseEntity.ok(response);
    }

    // todos metodos Post nao são indepotentesdevido que alterar o status do servido,
    //indepotentes são somente os endepoint que não alteram o status do servidor.
    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest request) {
        log.info("Request received  to save anime, param name'{}'", request);
        var anime = MAPPER.toAnime(request);
        var response = MAPPER.toAnimePostResponse(anime);
        Anime.getAnimes().add(anime);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Request received to delete the anime by id '{}'", id);
        var animeToBeDelete = Anime.getAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found to be delete"));
        Anime.getAnimes().remove(animeToBeDelete);
        return ResponseEntity.noContent().build();
    }
}
