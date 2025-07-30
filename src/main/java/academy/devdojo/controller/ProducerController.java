package academy.devdojo.controller;

import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.request.ProducerPostRequest;
import academy.devdojo.request.ProducerPutRequest;
import academy.devdojo.response.ProducerGetResponse;
import academy.devdojo.response.ProducerPostResponse;
import academy.devdojo.service.ProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"v1/producers", "v1/producers/"})
@Log4j2
public class ProducerController {

    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;
    private ProducerService service;

    public ProducerController() {
        this.service = new ProducerService();
    }

    // se quiser forçar o formato diferente da requisição voce pode usar o produces e  consumes para que formato
    // e consumir e entregar, por exemplo XML ou qualquer outro formato de arquivo.
    // header server para forçar o cliente consumindo, precisar a colocar a versao da api,  um padrao e começar com x-api-version=v1
    // que é totalmente customisavel,
    // No caso de nao querer retornar nada, no lugar de Producer irá o void e no retorno, noContent.build
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-version=v1")
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest request) {
        var producer = MAPPER.toProducer(request);
        service.save(producer);
        var response = MAPPER.toProducerPostResponse(producer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("findByName")
    public ResponseEntity<List<ProducerGetResponse>> findByName(@RequestParam(required = false) String name) throws JsonProcessingException {
        log.info("Request received  to list all producers, param name '{}'", name);
        var producers = service.findAll(name);
        var producerGetResponse = MAPPER.toProducerGetResponses(producers);
        return ResponseEntity.ok(producerGetResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Request received to delete the producer by id '{}'", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {
        log.info("Request received to updated the producer '{}'", request);
        var producerUpdated = MAPPER.toProducerPutRequest(request);
        service.update(producerUpdated);
        return ResponseEntity.noContent().build();
    }
}
