package studies.reactive.tests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/*
* Reactive streams
* 1-Asynchronous
* 2-Non-Blocking
* 3-Backpressure
* Publisher(É quem emite os eventos) -< Subscribe feita pelo Subscriber
* Subscription é criada após o subscribe
*
*
* */
@Slf4j
public class MonoTest {
    @Test
    public void monoSubscriber() {
        String nome = "Pablo Messias";
        Mono<String> mono = Mono.just(nome).log();

        mono.subscribe();
        log.info("\n -------------------------");

        StepVerifier.create(mono)
                .expectNext(nome)
                .verifyComplete();
    }

    @Test
    public void monoSubscriberConsumer() {
        String nome = "Pablo Messias";
        Mono<String> mono = Mono.just(nome).log();

        mono.subscribe(s -> log.info("Valor ->"+s));
        log.info("\n -------------------------");

        StepVerifier.create(mono)
                .expectNext(nome)
                .verifyComplete();
    }

    @Test
    public void monoSubscriberConsumerError() {
        String nome = "Pablo Messias";
        Mono<String> mono = Mono.just(nome)
                .map(s -> { throw new RuntimeException("Testando mono com erro!"); });

        mono.subscribe(s -> log.info("Nome -> "+s), e -> log.error("Algum erro ocorreu", e));
        mono.subscribe(s -> log.info("Nome -> "+s), Throwable::printStackTrace);
        log.info("\n -------------------------");

        StepVerifier.create(mono)
                .expectError(RuntimeException.class)
                .verify();
    }
}
