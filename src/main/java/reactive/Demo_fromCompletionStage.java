package reactive;

import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class Demo_fromCompletionStage {
    public static void main(String[] args) throws InterruptedException {
        //MOCK send [data]
        CompletableFuture<String> completionStage = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("服务器发送了->[data]  @" + Thread.currentThread().getName());
            return "[data]";
        });

        //MOCK Switch TO Reactor
        Mono.defer(() -> {
                    System.out.println("defer @" + Thread.currentThread().getName());
                    return Mono.fromCompletionStage(completionStage).flatMap(r -> Mono.fromCallable(() -> {
                        System.out.println("map处理 @" + Thread.currentThread().getName());
                        return r + "map---处理完成!";
                    }));
                })
                .subscribe(r -> {
                    System.out.println("订阅 @" + Thread.currentThread().getName());
                    System.out.println("结果是->" + r);
                });

//        completionStage.completeAsync(() -> {
//            System.out.println(Thread.currentThread().getName());
//            return "------->[DONE]";
//        });

        Thread.currentThread().join(5000);
    }
}
