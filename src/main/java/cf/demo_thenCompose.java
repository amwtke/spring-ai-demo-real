package cf;

import java.util.concurrent.CompletableFuture;

public class demo_thenCompose {
    public static void main(String[] args) throws InterruptedException {
        fetchUserId()
                .thenCompose(demo_thenCompose::fetchUserProfile)
                .thenAccept(System.out::println);

        Thread.currentThread().join(4000);
    }
    public static CompletableFuture<String> fetchUserId() {
        return CompletableFuture.supplyAsync(() -> "user-123");
    }

    public static CompletableFuture<String> fetchUserProfile(String userId) {
        return CompletableFuture.supplyAsync(() -> "Profile of " + userId);
    }
}
