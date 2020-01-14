package com.github.daggerok.reactor_02;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;

@Log4j2
@SpringBootTest
class Part01FluxTests {

    @Test
    void test_00() {
        List<Integer> someList = Arrays.asList(1, 2, 3, 4, 5);
        Consumer<? super Integer> someObserver = i -> log.info("i: {}", i);
        Function<? super Throwable, ? extends Publisher<? extends Integer>> fallback =
                e -> Mono.just(ThreadLocalRandom.current().nextInt());
        Runnable incrementTerminate = () -> log.info("I'm done!");
        Flux<Integer> flux = Flux.fromIterable(someList)
                                 .delayElements(Duration.ofMillis(100))
                                 .doOnNext(someObserver)
                                 .map(d -> d * 2)
                                 .take(3)
                                 .onErrorResume(fallback)
                                 .doAfterTerminate(incrementTerminate);

        StepVerifier.create(flux)
                    .expectNext(2, 4, 6)
                    .verifyComplete();
    }

    @Test
    void test_01_emptyFlux() {
        StepVerifier.create(Flux.empty())
                    .expectNextCount(0)
                    .verifyComplete();
    }

    @Test
    void test_02_fooBarFluxFromValues() {
        Flux<String> fooBarFluxFromValues = Flux.just("foo", "bar");

        StepVerifier.create(fooBarFluxFromValues)
                    .expectNext("foo", "bar")
                    .verifyComplete();
    }

    @Test
    void test_03_fooBarFluxFromList() {
        List<String> list = Arrays.asList("foo", "bar");
        Flux<String> fooBarFluxFromList = Flux.fromIterable(list);

        StepVerifier.create(fooBarFluxFromList)
                    .expectNext("foo", "bar")
                    .verifyComplete();
    }

    @Test
    void test_04_errorFlux() {
        Flux<String> errorFlux = Flux.error(new IllegalStateException());

        StepVerifier.create(errorFlux)
                    .expectError(IllegalStateException.class)
                    .verify();
    }

    @Test
    void test_05_counter() {
        Flux<Long> counter = Flux.interval(Duration.ofMillis(100))
                                 .take(10);

        StepVerifier.create(counter)
                    .expectNext(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L)
                    .verifyComplete();
    }
}
