package com.github.daggerok.reactor_01;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class Part01BasicTests {

    @Test
    void test_00() {
        StepVerifier.create(Flux.just(1, 2, 3))
                    .expectNextCount(3)
                    .verifyComplete();
    }

    @Test
    void test_03() {
        StepVerifier.create(Flux.range(1, 10))
                    .expectNextCount(10)
                    .verifyComplete();
    }

    @Test
    void test_04() {
        Flux<String> flux = Flux.just("A");
        flux.map(s -> "foo" + s);
        StepVerifier.create(flux)
                    .expectNext("A")
                    .verifyComplete();
    }
}
