package by.shumilov.autodealer.repository;

import by.shumilov.autodealer.entity.Taco;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TacoRepository
//        extends ReactiveCrudRepository<Taco, Long>
{
    Flux<Taco> findAll();

    Mono<Taco> save(Taco taco);

    Flux<Taco> saveAll(Mono<Taco> taco);
}
