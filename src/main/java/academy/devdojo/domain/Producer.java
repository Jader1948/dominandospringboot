package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Producer {

    @EqualsAndHashCode.Include
    private Long id;
    @JsonProperty(value = "name")//propiedade para usar nome diferente da variavel na requisição
    private String name;
    private LocalDateTime createAt;
    @Getter
    private static List<Producer> producers = new ArrayList<>();

    static {
        var mappa = Producer.builder().id(1L).name("Mappa").createAt(LocalDateTime.now()).build();
        var kyotoAnimation = Producer.builder().id(2L).name("Kyoto Animation").createAt(LocalDateTime.now()).build();
        var madhouse = Producer.builder().id(1L).name("Madhouse").createAt(LocalDateTime.now()).build();
        producers.addAll(List.of(mappa, kyotoAnimation, madhouse));
    }

}
