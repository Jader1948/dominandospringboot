package academy.devdojo.controller;

import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.request.AnimePostRequest;
import academy.devdojo.request.AnimePutRequest;
import academy.devdojo.response.AnimeGetResponse;
import academy.devdojo.response.AnimePostResponse;
import academy.devdojo.service.AnimeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"v1/animes", "v1/animes/"})
@Log4j2
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeMapper MAPPER;
    private final AnimeService service;


    @GetMapping()
    public ResponseEntity<List<AnimeGetResponse>> getAnimes() {
        var animes = service.listAll();
        var animeResponses = MAPPER.toAnimeGetResposes(animes);
        return ResponseEntity.ok(animeResponses);
    }

    @GetMapping("findByName")
    public ResponseEntity<List<AnimeGetResponse>> findByName(@RequestParam(required = false) String name) throws JsonProcessingException {
        log.info("Request received  to list all animes, param name '{}'", name);
        var animes = service.findByName(name);
        var animesGetResponses = MAPPER.toAnimeGetResposes(animes);
        return ResponseEntity.ok(animesGetResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        log.info("Request received find anime by id '{}'", id);
        var animeFound = service.findById(id);
        var response = MAPPER.toAnimeGetResponse(animeFound);
        return ResponseEntity.ok(response);
    }

    // todos metodos Post nao são indepotentesdevido que alterar o status do servido,
    //indepotentes são somente os endepoint que não alteram o status do servidor.
    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest request) {
        log.info("Request received  to save anime, param name'{}'", request);
        var anime = MAPPER.toAnime(request);
        service.save(anime);
        var response = MAPPER.toAnimePostResponse(anime);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Request received to delete the anime by id '{}'", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request) {
        log.info("Request received to updated the producer '{}'", request);
        var animeToUpdate = MAPPER.toAnimePutRequest(request);
        service.update(animeToUpdate);
        return ResponseEntity.noContent().build();
    }
}
