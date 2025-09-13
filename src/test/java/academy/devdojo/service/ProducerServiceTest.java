package academy.devdojo.service;

import academy.devdojo.domain.Producer;
import academy.devdojo.respository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {

    @InjectMocks
    private ProducerService service;

    @Mock
    private ProducerHardCodedRepository repository;

    private List<Producer> producers = new ArrayList<>();

    @BeforeEach
    void init() {
        var ufotable = Producer.builder().id(1L).name("Ufotable").createAt(LocalDateTime.now()).build();
        var witStudio = Producer.builder().id(2L).name("Wit Studio").createAt(LocalDateTime.now()).build();
        var studioGibli = Producer.builder().id(3L).name("Studio Gibli").createAt(LocalDateTime.now()).build();
        producers = new ArrayList<>(List.of(ufotable, witStudio, studioGibli));
    }

    @Test
    @DisplayName("findAll() returns a list with all producers")
    @Order(1)
    void findAll_Returns_Producers_WhenSuccessfull() {
        BDDMockito.when(repository.findAll()).thenReturn(this.producers);
        var producers = service.findAll();
        Assertions.assertThat(producers).hasSameElementsAs(this.producers);
    }

    @Test
    @DisplayName("findAll() returns a list with all producers")
    @Order(2)
    void findAll_Returns_Producers_ProcerNull_WhenSuccessfull() {
        BDDMockito.when(repository.findByName(null)).thenReturn(this.producers);
        var producers = service.findAll(null);
        Assertions.assertThat(producers).hasSameElementsAs(this.producers);
    }

    @Test
    @DisplayName("findAll() returns a list found producers when name is not null")
    @Order(3)
    void findAll_Returns_FoundProducers_WhenIsPassedAnFound() {
        var name = "Ufotable";
        var producerFound = this.producers.stream().filter(producer -> producer.getName().equals(name
        )).toList();
        BDDMockito.when(repository.findByName(name)).thenReturn(producerFound);
        var producers = service.findAll(name);
        Assertions.assertThat(producers).hasSize(1).contains(producerFound.get(0));
    }

    @Test
    @DisplayName("findAll() returns an empty list when no prodeucer is found by name")
    @Order(4)
    void findAll_ReturnsEmptyList_WhenNameIsFound() {
        var name = "x";
        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());
        var producers = service.findAll(name);
        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById() returns a optional producer when id exists")
    @Order(5)
    void findById_RetunsOptionalProducer_WhenExists() {
        var id = 1L;
        var producerExpected = this.producers.get(0);
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(producerExpected));
        var producerOptional = service.findById(id);
        Assertions.assertThat(producerOptional).isPresent().contains(producerExpected);
    }

    @Test
    @DisplayName("findById() returns a optional when id does not exists")
    @Order(6)
    void findById_RetunsEmptyOptionalProducer_WhenDoesNotExists() {
        var id = 1L;
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());
        var producerOptional = service.findById(id);
        Assertions.assertThat(producerOptional).isEmpty();
    }

    @Test
    @DisplayName("save() creates a producer")
    @Order(7)
    void save_CreatesProducer_WhenSuccessfull() {
        var producerToSave = Producer.builder()
                .id(99L)
                .name("MAPPA")
                .createAt(LocalDateTime.now())
                .build();
        BDDMockito.when(repository.save(producerToSave)).thenReturn(producerToSave);
        var producer = service.save(producerToSave);
        Assertions.assertThat(producer)
                .isEqualTo(producerToSave)
                .hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete() removes a producer")
    @Order(8)
    void delete_RemovesProducer_WhenSuccessfull() {
        var id = 1L;
        var producerToDelete = this.producers.get(0);
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(producerToDelete));
        BDDMockito.doNothing().when(repository).delete(producerToDelete);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(id));
    }

    @Test
    @DisplayName("delete() removes throw ResponseStatusException when no producer is found")
    @Order(9)
    void delete_ThrowsResponseStatusException_WhenNoProducerIsFound() {
        var id = 1L;
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(()-> service.delete(id))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(10)
    void update_UpdatesProducer_WhenSuccessfull() {
        var id = 1L;
        var producerToUpdate = this.producers.get(0);
        producerToUpdate.setName("Aniplex");
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(producerToUpdate));
        BDDMockito.doNothing().when(repository).update(producerToUpdate);
        Assertions.assertThatNoException().isThrownBy(()-> service.update(producerToUpdate));

    }

    @Test
    @DisplayName("update() removes throw ResponseStatusException when no producer is found")
    @Order(11)
    void update_ThrowsResponseStatusException_WhenNoProducerIsFound() {
        var id = 1L;
        var producerToUpdate = this.producers.get(0);
        producerToUpdate.setName("Aniplex");
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThatException()
                .isThrownBy(()-> service.delete(id))
                .isInstanceOf(ResponseStatusException.class);

    }



}