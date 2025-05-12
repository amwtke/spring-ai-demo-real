package reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Flux<String> flux = Flux.just("x1", "x2", "x3", "x4")
                .publishOn(Schedulers.boundedElastic(), 16)
//                .delayElements(Duration.ofMillis(1000))
                .map(r -> r + "y")
                .doOnSubscribe(r -> {
                    System.out.println("doOnSubscribe");
                    System.out.println(Thread.currentThread().getName());
                })
                .doOnComplete(() -> {
                    System.out.println("doOnComplete");
                    System.out.println(Thread.currentThread().getName());
                });
        flux
                .subscribeOn(Schedulers.boundedElastic(), true).subscribe(r -> {
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(r);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
        Thread.currentThread().join(10000);

        Flux.just(1, 2, 3, 4, 5)
                .map(r -> r * 10)
                .flatMap(r -> Flux.just(r * 2, r * 3, r * 4))
                .doOnSubscribe(r -> r.request(1))
                .subscribe(r -> System.out.println(r.intValue()));

        Flux.range(1, 10)
                .flatMap(i ->
                                Mono.fromCallable(() -> {
                                    Thread.sleep(500);
                                    return i * 2;
                                }).publishOn(Schedulers.boundedElastic()),
                        3 // 最多并发 3 个任务
                )
                .subscribe(System.out::println);
    }
}
