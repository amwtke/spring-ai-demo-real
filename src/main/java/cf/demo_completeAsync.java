package cf;

import java.util.concurrent.CompletableFuture;

public class demo_completeAsync {
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> future = new CompletableFuture<>();

        // 异步地完成这个 future
        future.completeAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            System.out.println("结果来自 completeAsync");
            return "结果来自 completeAsync";
        });

        // 等待结果 不用get也能执行
        Thread.currentThread().join(10000);
    }
}
