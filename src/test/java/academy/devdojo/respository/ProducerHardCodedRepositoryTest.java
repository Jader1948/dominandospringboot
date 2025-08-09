package academy.devdojo.respository;

import academy.devdojo.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerHardCodedRepositoryTest {

    @InjectMocks
    private ProducerHardCodedRepository repository;

    @Mock
    private ProducerData producerData;

    private List<Producer> producers =  new ArrayList<>();

    @BeforeEach
    void init(){
        var ufotable = Producer.builder().id(1L).name("Ufotable").createAt(LocalDateTime.now()).build();
        var witStudio = Producer.builder().id(2L).name("Wit Studio").createAt(LocalDateTime.now()).build();
        var studioGibli = Producer.builder().id(3L).name("Studio Gibli").createAt(LocalDateTime.now()).build();
        producers = new ArrayList<>(List.of(ufotable, witStudio, studioGibli));
        BDDMockito.when(producerData.getProducers()).thenReturn(producers);
    }

    @Test
    @DisplayName("findAll() returns a list with all producers")
    @Order(1)
    void findAll_Retyurns_Producers_WhenSuccessfull() {
        var producers = repository.findAll();
        Assertions.assertThat(producers).hasSameElementsAs(this.producers);
    }

    @Test
    @DisplayName("findById() returns an  object with given id")
    @Order(2)
    void findById_Retyurns_Producers_WhenSuccessfull() {
        var producerOptional = repository.findById(3L);
        Assertions.assertThat(producerOptional).isPresent().contains(producers.get(2));
    }

    @Test
    @DisplayName("findByName() returns all producers when name is null")
    @Order(3)
    void findByName_ReturnsAllProducers_Producers_WhenNameisNull() {
        var producers = repository.findByName(null);
        Assertions.assertThat(producers).hasSameElementsAs(this.producers);
    }

    @Test
    @DisplayName("findByName() returns list with filtered producers name is not null")
    @Order(4)
    void findByName_ReturnsFilteredProducers_WhenNameisNotNull() {
        var producers = repository.findByName("Ufotable");
        Assertions.assertThat(producers).hasSize(1).contains(this.producers.get(0));
    }

    @Test
    @DisplayName("findByName() returns empty list when no producers is found")
    @Order(5)
    void findByName_ReturnsEmptyListOfProducers_WhenNameisNotNull() {
        var producers = repository.findByName("xxxx");
        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save() creates a producer")
    @Order(6)
    void save_CreatesProducer_WhenSuccessfull() {
        var producerToSave = Producer.builder()
                .id(99L)
                .name("MAPPA")
                .createAt(LocalDateTime.now())
                .build();
        var producer = repository.save(producerToSave);
        Assertions.assertThat(producer)
                .isEqualTo(producerToSave)
                .hasNoNullFieldsOrProperties();
        var producers = repository.findAll();
        Assertions.assertThat(producers).contains(producerToSave);
    }

    @Test
    @DisplayName("delete() removes a producer")
    @Order(7)
    void delete_RemovesProducer_WhenSuccessfull() {
        var producerToDelete = this.producers.get(0);
        repository.delete(producerToDelete);
        Assertions.assertThat(this.producers).doesNotContain(producerToDelete);
    }
    @Test
    @DisplayName("update() updates a producer")
    @Order(8)
    void update_UpdatesProducer_WhenSuccessfull() {
        var producerToUpdate = this.producers.get(0);
        producerToUpdate.setName("Aniplex");
        repository.update(producerToUpdate);
        Assertions.assertThat(this.producers).contains(producerToUpdate);
        this.producers
                .stream()
                .filter(producer -> producer.getId().equals(producerToUpdate.getId()))
                .findFirst()
                .ifPresent(producer -> Assertions.assertThat(producer.getName()).isEqualTo(producerToUpdate.getName()));
    }

}