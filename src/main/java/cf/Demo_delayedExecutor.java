package cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo_delayedExecutor {
    volatile static AtomicInteger i = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture
                .runAsync(() -> {
                    i.getAndAdd(1);
                }, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS)).thenRun(() -> i.getAndAdd(1));

        while (true) {
            Thread.sleep(500);
            System.out.println(i.get());
        }
    }
}
