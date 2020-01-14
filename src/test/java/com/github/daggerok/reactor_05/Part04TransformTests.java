package com.github.daggerok.reactor_05;

import lombok.Value;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@Value
class User {
    private final String username, firstName, lastName;
}

@SpringBootTest
class Part04TransformTests {

    @Test
    void test_01_capitalizeOne() {
        Function<String, String> capitalize = s -> {
            String notNullable = Objects.requireNonNull(s);
            return notNullable.length() < 2 ? notNullable.toUpperCase() :
                    notNullable.toUpperCase().charAt(0) + notNullable.substring(1);
        };
        Function<User, User> transformer = user -> {
            String username = capitalize.apply(user.getUsername());
            String firstName = capitalize.apply(user.getFirstName());
            String lastName = capitalize.apply(user.getLastName());
            return new User(username, firstName, lastName);
        };
        Mono<User> mono = Mono.just(new User("lola", "ololo", "trololo"))
                              .map(transformer)
                              .log();

        StepVerifier.create(mono)
                    .assertNext(user -> {
                        assertThat(user.getUsername()).isEqualTo("Lola");
                        assertThat(user.getFirstName()).isEqualTo("Ololo");
                        assertThat(user.getLastName()).isEqualTo("Trololo");
                    })
                    .verifyComplete();
    }
}
