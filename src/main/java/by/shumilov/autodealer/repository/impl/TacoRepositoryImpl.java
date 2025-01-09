package by.shumilov.autodealer.repository.impl;

import by.shumilov.autodealer.entity.Ingredient;
import by.shumilov.autodealer.entity.Taco;
import by.shumilov.autodealer.repository.TacoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TacoRepositoryImpl implements TacoRepository {

    private List<Taco> tacoList = List.of(testTaco(1L), testTaco(2L), testTaco(3L),
            testTaco(4L), testTaco(5L), testTaco(6L),
            testTaco(7L), testTaco(8L), testTaco(9L),
            testTaco(10L), testTaco(11L), testTaco(12L),
            testTaco(13L), testTaco(14L),
            testTaco(15L), testTaco(16L));

    @Override
    public Flux<Taco> findAll() {
        return Flux.fromIterable(tacoList);
    }

    @Override
    public Mono<Taco> save(Taco taco) {
        tacoList.add(taco);
        return Mono.just(taco);
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
}
