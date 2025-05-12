package cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class demo_supplyAsync {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("supply-1");
            return 1;
        }).thenApply(result -> {
                    System.out.println(Thread.currentThread().getName());
                    System.out.println("apply");
                    return result + " World";
                }
        ).thenCompose(r -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("compose: r -> " + r);
            return CompletableFuture.supplyAsync(() -> r + " - xiaojin");
        }).thenAccept(r -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("accept");
            System.out.println("accept: r -> " + r);
            System.out.println(r);
        });
//        String x = future.get();
//        System.out.println(x); // 输出：Hello World
        Thread.sleep(10000);
    }

    static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
