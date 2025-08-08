package academy.devdojo.respository;

import academy.devdojo.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
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
        producers = List.of(ufotable, witStudio, studioGibli);
        BDDMockito.when(producerData.getProducers()).thenReturn(producers);
    }

    @Test
    @DisplayName("findAll returns a list with all producers")
    void findAll_Retyurns_Producers_WhenSuccessfull() {
        var producers = repository.findAll();
        Assertions.assertThat(producers).hasSize(3);
    }

}