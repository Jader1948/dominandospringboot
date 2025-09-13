package academy.devdojo.respository;

import academy.devdojo.domain.Anime;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class AnimeData {


    @Getter
    private static List<Anime> animes = new ArrayList<>();

    static {
        var jigokuraku = Anime.builder().id(1L).name("Jigokuraku").build();
        var konosuba = Anime.builder().id(2L).name("Jonosuba").build();
        var drStone = Anime.builder().id(3L).name("Dr. Stone").build();
        animes.addAll(List.of(jigokuraku, konosuba, drStone));
    }

    public List<Anime> getAnimes() {
        return animes;
    }

}
