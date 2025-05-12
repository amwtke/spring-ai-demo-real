package reactive;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Demo_onSubscribe {
    public static void main(String[] args) throws InterruptedException {
        Flux.range(1, 10)
                .reduce((r, u) -> {
                    System.out.println("reduce->" + Thread.currentThread().getName());
                    return r + u;
                })
//                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(r -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("output is:" + r + ". At thread:" + Thread.currentThread().getName());
                });

        Thread.currentThread().join(10000);
    }
}
