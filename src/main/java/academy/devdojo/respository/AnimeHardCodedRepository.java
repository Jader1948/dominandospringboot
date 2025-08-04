package academy.devdojo.respository;

import academy.devdojo.domain.Anime;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AnimeHardCodedRepository {


    @Getter
    private static List<Anime> ANIMES = new ArrayList<>();

    static {
        var jigokuraku = Anime.builder().id(1L).name("Jigokuraku").build();
        var konosuba = Anime.builder().id(2L).name("Jonosuba").build();
        var drStone = Anime.builder().id(3L).name("Dr. Stone").build();
        var jaderM = Anime.builder().id(4L).name( "JaderM").build();
        var deboraM = Anime.builder().id(5L).name("DeboraM").build();
        ANIMES.addAll(List.of(jigokuraku, konosuba, drStone, jaderM, deboraM));
    }

    public List<Anime> listAll(){
        return ANIMES;
    }

    public List<Anime> findByName(String name) {
        return ANIMES.stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name))
                .toList();
    }

    public Optional<Anime> findById(Long id) {
        return ANIMES.stream().filter(anime -> anime.getId().equals(id)).findFirst();
    }

    public Anime save(Anime anime) {
        ANIMES.add(anime);
        return anime;
    }

    public void delete(Anime anime) {
        ANIMES.remove(anime);
    }

    public void update(Anime anime) {
        delete(anime);
        save(anime);
    }

}
