package com.github.daggerok.reactor_06;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class Part05MergeTests {

    @Test
    void test_01_merge_with_interleave_unordered() {
        Flux<String> flux1 = Flux.just("ololo", "trololo");
        Flux<String> flux2 = Flux.just("1", "2", "3");
        Flux<String> flux3 = Flux.just(".", "..", "...");
        Flux<String> mergedFlux = Flux.merge(flux1, flux2, flux3)
                                      .log();

        StepVerifier.create(mergedFlux)
                    // .expectSubscription()
                    .expectNext("ololo", "trololo",
                                "1", "2", "3", ".", "..", "...")
                    .verifyComplete();
    }

    @Test
    void test_02_concat_with_no_interleave_ordered() {
        Flux<String> flux1 = Flux.just("ololo", "trololo");
        Flux<String> flux2 = Flux.just("1", "2", "3");
        Flux<String> flux3 = Flux.just(".", "..", "...");
        Flux<String> concatFlux = Flux.concat(flux1, flux2, flux3)
                                      .log();

        StepVerifier.create(concatFlux)
                    .expectNext("ololo", "trololo",
                                "1", "2", "3", ".", "..", "...")
                    .verifyComplete();
    }

    @Test
    void test_03_concat_mono_flux() {
        Mono<String> mono1 = Mono.just("ololo");
        Mono<String> mono2 = Mono.empty();
        Flux<String> flux = Flux.just("trololo", "...");
        Flux<String> concatMonoFlux = Flux.concat(mono1, mono2, flux)
                                          .log();

        StepVerifier.create(concatMonoFlux)
                    .expectNext("ololo", "trololo", "...")
                    .verifyComplete();
    }
}
