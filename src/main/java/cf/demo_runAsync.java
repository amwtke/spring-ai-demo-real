package cf;

import java.util.concurrent.CompletableFuture;

public class demo_runAsync {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("执行任务");
        });

        Thread.currentThread().join(1000);
    }
}
