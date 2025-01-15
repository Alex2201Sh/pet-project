package by.shumilov.autodealer;

import by.shumilov.autodealer.entity.Ingredient;
import by.shumilov.autodealer.entity.Taco;
import by.shumilov.autodealer.repository.TacoRepository;
import by.shumilov.autodealer.web.api.TacoController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TacoControllerTest {

    @Autowired
    private WebTestClient testClient;

    @Test
    public void shouldReturnRecentTacos() {
        Taco[] tacos = {testTaco(1L), testTaco(2L), testTaco(3L),
                testTaco(4L), testTaco(5L), testTaco(6L),
                testTaco(7L), testTaco(8L), testTaco(9L),
                testTaco(10L), testTaco(11L), testTaco(12L),
                testTaco(13L), testTaco(14L),
                testTaco(15L), testTaco(16L)};
        Flux<Taco> tacoFlux = Flux.just(tacos);

        TacoRepository tacoRepo = Mockito.mock(TacoRepository.class);
        when(tacoRepo.findAll()).thenReturn(tacoFlux);

        WebTestClient testClient = WebTestClient
                .bindToController(new TacoController(tacoRepo))
                .build();

//        testClient.get().uri("/api/tacos?recent")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$").isArray()
//                .jsonPath("$").isNotEmpty()
//                .jsonPath("$[0].id").isEqualTo(tacos[0].getId().toString())
//                .jsonPath("$[0].name").isEqualTo("Taco 1")
//                .jsonPath("$[1].id").isEqualTo(tacos[1].getId().toString())
//                .jsonPath("$[1].name").isEqualTo("Taco 2")
//                .jsonPath("$[11].id").isEqualTo(tacos[11].getId().toString())
//                .jsonPath("$[11].name").isEqualTo("Taco 12")
//                .jsonPath("$[12]").doesNotExist();

//        testClient.get().uri("/api/tacos?recent")
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(Taco.class)
//                .contains(Arrays.copyOf(tacos, 12));

        testClient.get().uri("/api/tacoss")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Taco.class)
                .contains(Arrays.copyOf(tacos, 12));
    }

    private Taco testTaco(Long number) {
        Taco taco = new Taco();
        taco.setId(number != null ? number.toString() : "TESTID");
        taco.setName("Taco " + number);
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("INGA", Ingredient.Type.WRAP));
        ingredients.add(new Ingredient("INGB", Ingredient.Type.PROTEIN));
        taco.setIngredients(ingredients);
        return taco;
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldSaveATaco() {
        TacoRepository tacoRepo = Mockito.mock(TacoRepository.class);
        WebTestClient testClient = WebTestClient.bindToController(new TacoController(tacoRepo)).build();
        Mono<Taco> unsavedTacoMono = Mono.just(testTaco(1L));
        Taco savedTaco = testTaco(1L);
        Flux<Taco> savedTacoMono = Flux.just(savedTaco);
        when(tacoRepo.saveAll(any(Mono.class))).thenReturn(savedTacoMono);
        testClient.post().uri("/api/tacos")
                .contentType(MediaType.APPLICATION_JSON)
                .body(unsavedTacoMono, Taco.class).exchange()
                .expectStatus().isCreated()
                .expectBody(Taco.class).isEqualTo(savedTaco);
    }

    @Test
    public void shouldReturnRecentTacos2() throws IOException {
        testClient.get().uri("/api/tacos?recent")
                .accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$[?(@.name == ‘Carnivore’)]").exists()
                .jsonPath("$[?(@.name == ‘Bovine Bounty’)]").exists()
                .jsonPath("$[?(@.name == ‘Veg-Out’)]").exists();
    }
}
