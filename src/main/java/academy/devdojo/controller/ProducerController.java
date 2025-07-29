package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.request.ProducerPostRequest;
import academy.devdojo.response.ProducerGetResponse;
import academy.devdojo.response.ProducerPostResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = {"v1/producers", "v1/producers/"})
@Log4j2
public class ProducerController {

    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;

    // se quiser forçar o formato diferente da requisição voce pode usar o produces e  consumes para que formato
    // e consumir e entregar, por exemplo XML ou qualquer outro formato de arquivo.
    // header server para forçar o cliente consumindo, precisar a colocar a versao da api,  um padrao e começar com x-api-version=v1
    // que é totalmente customisavel,
    // No caso de nao querer retornar nada, no lugar de Producer irá o void e no retorno, noContent.build
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-version=v1")
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest request) {
        var mapper = ProducerMapper.INSTANCE;
        var producer = mapper.toProducer(request);
        var response = mapper.toProducerPostResponse(producer);
        Producer.getProducers().add(producer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("findByName")
    public ResponseEntity<List<ProducerGetResponse>> findByName(@RequestParam(required = false) String name) throws JsonProcessingException {
        log.info("Request received  to list all producers, param name '{}'", name);
        var producers = Producer.getProducers();
        var producerGetResponse = MAPPER.toProducerGetResponses(producers);
        if (name == null || name == "") return ResponseEntity.ok(producerGetResponse);
        producerGetResponse = producerGetResponse
                .stream()
                .filter(producer -> producer.getName().equalsIgnoreCase(name))
                .toList();
        return ResponseEntity.ok(producerGetResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Request received to delete the producer by id '{}'", id);
        var producerToBeDelete = Producer.getProducers()
                .stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found to be delete"));
        Producer.getProducers().remove(producerToBeDelete);
        return ResponseEntity.noContent().build();
    }
}
