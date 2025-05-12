package reactive;

import reactor.core.publisher.Flux;

public class Demo_simple {
    public static void main(String[] args) {
        Flux.range(1, 10)
                .map(i -> {

                    System.out.println(Thread.currentThread().getName());
                    return i * 2;
                }).reduce((r, j) -> {
                    System.out.println(Thread.currentThread().getName());
                    return Integer.sum(r, j);
                })
                .subscribe();

    }
}
