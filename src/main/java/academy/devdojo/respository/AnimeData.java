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
        var jaderM = Anime.builder().id(4L).name("JaderM").build();
        var deboraM = Anime.builder().id(5L).name("DeboraM").build();
        animes.addAll(List.of(jigokuraku, konosuba, drStone, jaderM, deboraM));
    }

    public List<Anime> getAnimes() {
        return animes;
    }

}
