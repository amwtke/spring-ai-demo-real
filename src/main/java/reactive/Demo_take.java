package reactive;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class Demo_take {
    public static void main(String[] args) throws InterruptedException {
        Flux.just(1,2,3,4,5)
                .take(2)
                .delayElements(Duration.ofMillis(1000), Schedulers.boundedElastic())
                .subscribe(System.out::println);
        Thread.currentThread().join(4000);
    }
}
