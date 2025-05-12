package cf;

import java.util.concurrent.CompletableFuture;

public class demo_whenComplete {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("执行成功，结果: " + result);
                    }
                });

//        future.join(); // 等待完成
        Thread.currentThread().join(5000);
    }
}
