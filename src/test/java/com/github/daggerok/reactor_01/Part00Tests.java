package com.github.daggerok.reactor_01;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Part00Tests {

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

    @Test
    void test_05() {
        StepVerifier.create(Mono.just(Collections.singletonMap("ololo", "trololo")))
                    .assertNext(stringStringMap -> assertThat(stringStringMap.get("ololo")).isEqualTo("trololo"))
                    .verifyComplete();
    }
}
