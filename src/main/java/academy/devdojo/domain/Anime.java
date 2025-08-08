package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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

}
