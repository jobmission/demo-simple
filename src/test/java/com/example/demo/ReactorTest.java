package com.example.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class ReactorTest {
    @Disabled
    @Test
    public void testFlux() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Flux<String> flux = Flux.interval(Duration.ofMillis(250))
            .map(input -> {
                if (input < 3) return "tick " + input;
                throw new RuntimeException("boom");
            });
        flux.subscribe(System.out::println, (x) -> {
            System.out.println("onError:" + x);
            latch.countDown();
        }, () -> {
            System.out.println("onComplete");
            latch.countDown();
        });
        latch.await();
    }
}
