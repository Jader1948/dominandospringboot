package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

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


}
