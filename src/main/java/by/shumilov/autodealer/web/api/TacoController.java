package by.shumilov.autodealer.web.api;

import by.shumilov.autodealer.entity.Taco;
import by.shumilov.autodealer.repository.TacoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Objects;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class TacoController {
    private final TacoRepository tacoRepo;

    @Bean
    public RouterFunction<?> routerFunction() {
        return route(GET("/api/tacos").and(queryParam("recent", Objects::nonNull)), this::recents)
                .andRoute(POST("/api/tacos"), this::postTaco)
                .andRoute(GET("/api/tacoss"),this::recents);
    }

    public Mono<ServerResponse> recents(ServerRequest request) {
        return ServerResponse.ok().
                body(tacoRepo.findAll().
                        take(12), Taco.class);
    }

    public Mono<ServerResponse> postTaco(ServerRequest request) {
        return request.bodyToMono(Taco.class)
                .flatMap(tacoRepo::save)
                .flatMap(savedTaco -> ServerResponse
                        .created(URI.create("http://localhost:8080/api/tacos/" + savedTaco.getId()))
                        .body(savedTaco, Taco.class));
    }
}
