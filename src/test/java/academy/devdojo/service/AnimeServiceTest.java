package academy.devdojo.service;

import academy.devdojo.domain.Anime;
import academy.devdojo.domain.Producer;
import academy.devdojo.respository.AnimeHardCodedRepository;
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
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeServiceTest {


    @InjectMocks
    private AnimeService service;

    @Mock
    private AnimeHardCodedRepository repository;

    private List<Anime> animes = new ArrayList<>();

    @BeforeEach
    void init() {
        var jigokuraku = Anime.builder().id(1L).name("Jigokuraku").build();
        var konosuba = Anime.builder().id(2L).name("Jonosuba").build();
        var drStone = Anime.builder().id(3L).name("Dr. Stone").build();
        animes = new ArrayList<>(List.of(jigokuraku, konosuba, drStone));
    }


    @Test
    @DisplayName("findAll() returns a list with all animes")
    @Order(1)
    void findAll_Returns_AllAnimes_WhenSuccessfull() {
        BDDMockito.when(repository.findAll()).thenReturn(this.animes);
        var producers = service.listAll();
        Assertions.assertThat(producers).hasSameElementsAs(this.animes);
    }

    @Test
    @DisplayName("findAll() returns a list with all animes when name is null")
    @Order(2)
    void findAll_Returns_Animes_WhenNameIsNull() {
        BDDMockito.when(repository.findByName(null)).thenReturn(this.animes);
        var producers = service.findByName(null);
        Assertions.assertThat(producers).hasSameElementsAs(this.animes);
    }

    @Test
    @DisplayName("findAll() returns a list found animes when name is not null")
    @Order(3)
    void findAll_Returns_FoundAnimes_WhenIsPassedAnFound() {
        var name = "Jonosuba";
        var animeFound = this.animes.stream().filter(anime -> anime.getName().equals(name)).toList();
        BDDMockito.when(repository.findByName(name)).thenReturn(animeFound);
        var animes = service.findByName(name);
        Assertions.assertThat(animes).hasSize(1).contains(animeFound.get(0));
    }

    @Test
    @DisplayName("findById() returns an  animes when id exists")
    @Order(4)
    void findById_RetunsAnime_WhenIdExists() {
        var id = 1L;
        var animeExpected = this.animes.get(0);
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(animeExpected));
        var producerOptional = service.findById(id);
        Assertions.assertThat(producerOptional).isEqualTo(animeExpected);
    }

    @Test
    @DisplayName("findById()  throw ResponseStatusException when no anime is found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenNoAnimeIsFound() {
        var id = 1L;
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThatException().isThrownBy(() -> service.findById(id))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save() creates a anime")
    @Order(6)
    void save_CreateAnime_WhenSuccessfull() {
        var animeToSave = Anime.builder()
                .id(99L)
                .name("Jader")
                .build();
        BDDMockito.when(repository.save(animeToSave)).thenReturn(animeToSave);
        var anime = service.save(animeToSave);
        Assertions.assertThat(anime)
                .isEqualTo(animeToSave)
                .hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete() removes a anime")
    @Order(7)
    void delete_RemoveAnime_WhenSuccessfull() {
        var id = 1L;
        var animeToDelete = this.animes.get(0);
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(animeToDelete));
        BDDMockito.doNothing().when(repository).delete(animeToDelete);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(id));
    }

    @Test
    @DisplayName("delete() removes throw ResponseStatusException when no anime is found")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenNoAnimeIsFound() {
        var id = 1L;
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(()-> service.delete(id))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(9)
    void update_UpdatesAnime_WhenSuccessfull() {
        var id = 1L;
        var animeToBeUpdate = this.animes.get(0);
        animeToBeUpdate.setName("Morpheu");
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(animeToBeUpdate));
        BDDMockito.doNothing().when(repository).update(animeToBeUpdate);
        Assertions.assertThatNoException().isThrownBy(()-> service.update(animeToBeUpdate));
    }

    @Test
    @DisplayName("update() throw ResponseStatusException when no anime is found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenNoAnimeIsFound() {
        var id = 1L;
        var animeToBeUpdate = this.animes.get(0);
        animeToBeUpdate.setName("Morpheu");
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThatException()
                .isThrownBy(()-> service.update(animeToBeUpdate))
                .isInstanceOf(ResponseStatusException.class);

    }

}