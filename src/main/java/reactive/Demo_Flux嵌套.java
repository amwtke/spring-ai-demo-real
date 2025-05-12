package reactive;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Demo_Flux嵌套 {
    public static void main(String[] args) throws InterruptedException {
        Flux.defer(() -> {
                    System.out.println(Thread.currentThread().getName());
                    return Flux.range(1, 3)
                            .map(r -> {
                                        System.out.println(Thread.currentThread().getName());
                                        return r.toString();
                                    }
                            );
                })
                .publishOn(Schedulers.boundedElastic())
                .map(r -> {
                    System.out.println(Thread.currentThread().getName());
                    return r + " -->";
                }).subscribe(r -> {
                    System.out.println(r);
                });

        Thread.currentThread().join(2000);
    }
}
