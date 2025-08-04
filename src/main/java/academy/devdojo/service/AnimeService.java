package academy.devdojo.service;

import academy.devdojo.domain.Anime;
import academy.devdojo.respository.AnimeHardCodedRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public class AnimeService {

    AnimeHardCodedRepository repository;

    public AnimeService( ) {
        this.repository = new AnimeHardCodedRepository();
    }

    public List<Anime> listAll() {
        return repository.listAll();
    }

    public List<Anime> findByName(String name) {
        return repository.findByName(name);
    }

    public Anime findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
    }

    public Anime save(Anime anime) {
        return repository.save(anime);
    }

    public void delete(Long id) {
        var anime = findById(id);
        repository.delete(anime);
    }

    public void update(Anime animeToUpdate) {
        assertAnimeExists(animeToUpdate);
        repository.update(animeToUpdate);
    }

    private void assertAnimeExists(Anime animeToUpdated) {
        findById(animeToUpdated.getId());
    }
}   
