package com.github.daggerok.reactor_03;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@SpringBootTest
class Part02MonoTests {

    @Test
    void test_00() {
        Mono<String> mono = Mono.just(1)
                                .map(integer -> "foo" + integer)
                                .delayElement(Duration.ofMillis(100))
                                .log();
        StepVerifier.create(mono)
                    .expectNext("foo1")
                    .verifyComplete();
    }

    @Test
    void test_01_emptyMono() {
        StepVerifier.create(Mono.empty())
                    .expectNextCount(0)
                    .verifyComplete();
    }

    @Test
    void test_02_monoWithNoSignal() {
        StepVerifier.create(Mono.just("value"))
                    .expectNext("value")
                    .verifyComplete();
    }

    @Test
    void test_03_monoWithNoSignal() {
        StepVerifier.create(Mono.fromCallable(() -> {
                        Thread.sleep(1000);
                        return null;
                    }))
                    .expectTimeout(Duration.ofMillis(500))
                    .verify();
    }

    @Test
    void test_04_errorMono() {
        StepVerifier.create(Mono.error(new IllegalStateException()))
                    .expectError(IllegalStateException.class)
                    .verify();
    }
}
