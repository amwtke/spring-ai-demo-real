package reactive;

import lombok.val;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.locks.ReentrantLock;

public class Dome_Publishon {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();

        Flux.just("1", "2", "3")
                .map(Integer::valueOf)
                .doOnEach(r -> System.out.println(r.get()))
                .flatMap(i -> Mono.fromCallable(() -> {
                                    lock.lock();
                                    val i1 = i * 2;
                                    //模拟一个耗时的过程
                                    Thread.sleep(100);
                                    System.out.println("-------b--------");
                                    System.out.println(Thread.currentThread().getName());
                                    System.out.println(i1);
                                    System.out.println("---------------");
                                    lock.unlock();
                                    return i1;
                                })
                                .publishOn(Schedulers.boundedElastic())
                )
                .flatMap(r -> Mono.fromCallable(() -> {
                            lock.lock();
                            val i1 = r * 10;
                            //模拟一个耗时的过程
                            Thread.sleep(100);
                            System.out.println("-------p--------");
                            System.out.println(Thread.currentThread().getName());
                            System.out.println(i1);
                            System.out.println("----------------");
                            lock.unlock();
                            return i1;
                        })
                        .publishOn(Schedulers.parallel()))
                .doOnSubscribe(r -> System.out.println("on Sub"))
                .doOnComplete(() -> System.out.println("on complete"))
                .doOnEach(r -> System.out.println("on each r-> " +r.get()))
                .reduce(Integer::sum)
                .subscribe(r -> {
                    System.out.println("subscribe is:" + r + ". At thread:" + Thread.currentThread().getName());
                    System.out.println();
                });


        Thread.currentThread().join(20000);
    }
}
