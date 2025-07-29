package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Anime {
    @EqualsAndHashCode.Include
    private Long id;
    @JsonProperty(value = "name")//propiedade para usar nome diferente da variavel na requisição
    private String name;
    @Getter
    private static List<Anime> animes = new ArrayList<>();

    static {
        var jigokuraku = Anime.builder().id(1L).name("Jigokuraku").build();
        var konosuba = Anime.builder().id(2L).name("Jonosuba").build();
        var drStone = Anime.builder().id(3L).name("Dr. Stone").build();
        var jaderM = Anime.builder().id(4L).name( "JaderM").build();
        var deboraM = Anime.builder().id(5L).name("DeboraM").build();
        animes.addAll(List.of(jigokuraku, konosuba, drStone, jaderM, deboraM));
    }

}
