package by.shumilov.autodealer;

import by.shumilov.autodealer.config.HelloWorldRouterFunctionConfig;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

class HelloWorldRouterFunctionConfigTest {

    @Test
    void testHello(){
        WebTestClient testClient = WebTestClient
                .bindToController(new HelloWorldRouterFunctionConfig())
                .build();

        testClient.get().uri("/hello")
//                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
//                .expectBodyList(Taco.class)
//                .contains(Arrays.copyOf(tacos, 12));
        ;
    }

}