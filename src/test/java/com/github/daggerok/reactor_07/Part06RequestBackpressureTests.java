package com.github.daggerok.reactor_07;

import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

@Value
class User {
    public static final User MAX = new User("max");
    public static final User BOB = new User("bob");
    private final String name;
}

@Log4j2
@SpringBootTest
class Part06RequestBackpressureTests {

    @Test
    void test_01() {
        Flux<Long> flux = Flux.interval(Duration.ofNanos(123))
                              .log();

        StepVerifier stepVerifier = StepVerifier.create(flux)
                                                .expectNextCount(4)
                                                .expectComplete();
        // stepVerifier.verify(); // will fail...
    }

    @Test
    void test_02() {
        Flux<Long> flux = Flux.interval(Duration.ofNanos(123))
                              .log();

        StepVerifier.create(flux)
                    .expectNextCount(25)
                    .thenCancel()
                    .verify();
    }

    @Test
    void test_03() {
        Flux<User> flux = Flux.just(User.MAX, User.BOB);

        StepVerifier.create(flux)
                    .expectNext(User.MAX)
                    .thenCancel()
                    .verify();
    }

    @Test
    void test_04() {
        Flux<String> flux = Flux.just("ololo", "trololo")
                                .doOnSubscribe(subscription -> log.info("Starring:"))
                                .doOnNext(s -> log.info("... {}", s))
                                .doOnComplete(() -> log.info("The End!"));

        StepVerifier.create(flux)
                    .expectNextCount(2)
                    .verifyComplete();
    }
}
