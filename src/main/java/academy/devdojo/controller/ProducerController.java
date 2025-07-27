package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping(path = {"v1/producers","v1/producers/"})
public class ProducerController {

    // se quiser forçar o formato diferente da requisição voce pode usar o produces e  consumes para que formato
    // e consumir e entregar, por exemplo XML ou qualquer outro formato de arquivo.
    // header server para forçar o cliente consumindo, precisar a colocar a versao da api,  um padrao e começar com x-api-version=v1
    // que é totalmente customisavel,
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-version=v1")
    public Producer save(@RequestBody Producer producer) {
        producer.setId(ThreadLocalRandom.current().nextLong(100_000));
        Producer.getProducers().add(producer);
        return producer;
    }
}
