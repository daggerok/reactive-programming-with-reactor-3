package com.github.daggerok.reactor_04;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Collections;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Part03StepVerifierTests {

    @Test
    void test_05() {
        StepVerifier.create(Mono.just(Collections.singletonMap("ololo", "trololo"))
                                .log())
                    .assertNext(stringStringMap -> assertThat(stringStringMap.get("ololo")).isEqualTo("trololo"))
                    .verifyComplete();
    }

    @Test
    void test_06_time_manipulation() {
        Supplier<Flux<Integer>> supplier = () -> Flux.range(0, 3600)
                                                     .delayElements(Duration.ofSeconds(1))/*
                                                     .log()*/;
        StepVerifier.withVirtualTime(supplier)
                    .expectSubscription()
                    .thenAwait(Duration.ofSeconds(3600))
                    .expectNextCount(3600)
                    .verifyComplete();
    }
}
