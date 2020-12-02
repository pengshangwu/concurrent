package com.hdsx.juc;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.*;

public class CompletableFutureTest {

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2, 10, 60, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(10), new ThreadPoolExecutor.AbortPolicy()
    );

    /**
     * Async结尾的为异步执行
     * 无返回结果
     *          runAsync(Runnable runnable)
     *          runAsync(Runnable runnable, Executor executor)
     * 有返回结果
     *          supplyAsync(Supplier<U> supplier)
     *          supplyAsync(Supplier<U> supplier, Executor executor)
     */
    @Test
    public void runAsync_supplyAsync() {
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            System.out.println("我是一个没有返回结果的方法");
        }, executor);

        CompletableFuture<Integer[]> supplyAsync = CompletableFuture.supplyAsync(this::generateDataForTime, executor);
        try {
            // 最多等待5秒返回结果，否则就抛异常
            Integer[] integers = supplyAsync.get(5, TimeUnit.SECONDS);
            System.out.println(Arrays.toString(integers));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 两个CompletionStage，执行快的结果将作为action的入参
     * acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action)
     * acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action)
     * acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action, Executor executor)
     */
    @Test
    public void acceptEitherAsync() throws Exception {

        CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "c1跑得快";
        }, executor);

        CompletableFuture<String> c2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "c2跑得快";
        }, executor);

        c1.acceptEitherAsync(c2, new Consumer<String>() {
            @Override
            public void accept(String result) {
                System.out.println("最终结果为: " + result);
            }
        }, executor);

        System.in.read();
    }

    /**
     * 和acceptEitherAsync一样的用法，只不过会对最快的CompletionStage的结果进行加工并返回给下一个阶段使用
     * applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn)
     * applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn)
     * applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn, Executor executor)
     */
    @Test
    public void applyToEitherAsync() throws Exception {
        CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "c1";
        }, executor);

        CompletableFuture<String> c2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "c2";
        }, executor);

        c1.applyToEitherAsync(c2, new Function<String, String>() {
            @Override
            public String apply(String result) {
                return "结果为: " + result;
            }
        }, executor).whenCompleteAsync(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String result, Throwable throwable) {
                System.out.println("最终" + result);
            }
        });

        System.in.read();
    }

    /**
     * 可以捕获任意阶段的处理结果及异常
     * whenComplete(BiConsumer<? super T, ? super Throwable> action)
     * whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action)
     * whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action, Executor executor)
     */
    @Test
    public void whenCompleteAsync() throws Exception {
        CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> {
            int i = new Random().nextInt(10);
            if(i <= 5) {
                return "c1";
            }else {
                throw new RuntimeException("c1出异常了啊");
            }
        }, executor);
        c1.whenCompleteAsync((result, throwable) -> {
            if(throwable != null) {
                System.out.println(throwable.getMessage());
            }else {
                System.out.println("我的结果为：" + result);
            }
        }, executor);

        CompletableFuture<Void> c2 = CompletableFuture.runAsync(() -> {
            int i = new Random().nextInt(10);
            if(i <= 5) {
                System.out.println("我的结果是：c2");
            }else {
                throw new RuntimeException("c2出异常了啊");
            }
        }, executor);
        c2.whenCompleteAsync((result, throwable) -> {
            if(throwable != null) {
                System.out.println(throwable.getMessage());
            }
        }, executor);
        System.in.read();
    }

    /**
     * 和whenCompleteAsync差不多，只不过handle有返回值给下一个阶段继续使用
     * handle(BiFunction<? super T, Throwable, ? extends U> fn)
     * handleAsync(BiFunction<? super T, Throwable, ? extends U> fn)
     * handleAsync(BiFunction<? super T, Throwable, ? extends U> fn, Executor executor)
     */
    @Test
    public void handleAsync() throws Exception {
        CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> {
            int i = new Random().nextInt(10);
            if (i <= 5) {
                return "c1";
            } else {
                throw new RuntimeException("c1出异常了啊");
            }
        }, executor);
        String message = c1.handleAsync(new BiFunction<String, Throwable, String>() {
            @Override
            public String apply(String result, Throwable throwable) {
                if (throwable != null) {
                    return "c1出异常了啊";
                } else {
                    return result;
                }
            }
        }, executor).get();
        System.out.println(message);

        System.in.read();
    }

    /**
     * 当get()等待结果的时候，可以使用complete来快速返回结果
     * complete(T value)
     * completeExceptionally(Throwable ex) 直接返回一个异常
     */
    @Test
    public void complate() throws Exception {
        CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "c1";
        }, executor);

        Thread.sleep(3000);

//        c1.complete("还是没有等到c1");
        c1.completeExceptionally(new RuntimeException("还是没有等到c1"));

        System.out.println(c1.get());

        System.in.read();
    }

    /**
     * 对阶段的结果进行消费
     * thenAccept(Consumer<? super T> action)
     * thenAcceptAsync(Consumer<? super T> action)
     * thenAcceptAsync(Consumer<? super T> action, Executor executor)
     */
    @Test
    public void thenAcceptAsync() throws Exception {
        CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> "c" + new Random().nextInt(10), executor);

        c1.thenAcceptAsync(System.out::println, executor);

        System.in.read();
    }

    /**
     * 接受阶段返回结果并加工返回下一阶段使用
     * thenApply(Function<? super T,? extends U> fn)
     * thenApplyAsync(Function<? super T,? extends U> fn)
     * thenApplyAsync(Function<? super T,? extends U> fn, Executor executor)
     */
    @Test
    public void thenApplyAsync() throws Exception {

        CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> "c" + new Random().nextInt(10), executor);

        c1.thenApplyAsync(new Function<String, String>() {
            @Override
            public String apply(String result) {
                return result;
            }
        }, executor).thenAccept(System.out::println);

        System.in.read();
    }

    /**
     * 对阶段返回的结果再次封装为阶段并返回
     * thenCompose(Function<? super T, ? extends CompletionStage<U>> fn)
     * thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn)
     * thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn, Executor executor)
     */
    @Test
    public void thenComposeAsync() throws Exception {
        CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> "c1", executor);

        c1.thenComposeAsync(new Function<String, CompletionStage<String>>() {
            @Override
            public CompletionStage<String> apply(String result) {

                return CompletableFuture.supplyAsync(new Supplier<String>() {
                    @Override
                    public String get() {
                        return "我是加工过的结果：" + result;
                    }
                });
            }
        }, executor).thenAcceptAsync(System.out::println);

        System.in.read();
    }

    /**
     * 将两个阶段的返回值合并（两个阶段的值都必须要正常返回，否则一直等下去）后，进行加工再次返回
     * thenCombine(CompletionStage<? extends U> other, BiFunction<? super T,? super U,? extends V> fn)
     * thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T,? super U,? extends V> fn)
     * thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T,? super U,? extends V> fn, Executor executor)
     */
    @Test
    public void thenCombineAsync() throws Exception {
        CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            int i = 1 / 0;
            return "c1";
        }, executor);
        CompletableFuture<String> c2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "c2";
        }, executor);

        c1.thenCombineAsync(c2, new BiFunction<String, String, String>() {
            @Override
            public String apply(String r1, String r2) {
                return "最终的结果为：" + r1 + "和" + r2;
            }
        }, executor).thenAccept(System.out::println);

        System.in.read();
    }

    /**
     * 当执行的阶段出现异常的时候，可捕获异常，并将另一种阶段形式返回
     * exceptionally(Function<Throwable, ? extends T> fn)
     */
    @Test
    public void exceptionally() throws Exception {
        CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            return "c1";
        }, executor).exceptionally(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable throwable) {
                return "出现了异常";
            }
        }).thenAccept(System.out::println);

        System.in.read();
    }

    /**
     * allOf: 等待所有的阶段完成才算完成
     * anyOf: 只有一个完成才算完成
     */
    @Test
    public void allof_anyof() throws Exception {
        CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "c1";
        }, executor);
        CompletableFuture<String> c2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "c2";
        }, executor);

        CompletableFuture<Object> future = CompletableFuture.anyOf(c1, c2);
        future.whenCompleteAsync(new BiConsumer<Object, Throwable>() {
            @Override
            public void accept(Object o, Throwable throwable) {
                System.out.println(o);
            }
        });

//        CompletableFuture<Void> future = CompletableFuture.allOf(c1, c2);
//        future.whenCompleteAsync(new BiConsumer<Void, Throwable>() {
//            @Override
//            public void accept(Void aVoid, Throwable throwable) {
//                System.out.println("所有成功返回");
//            }
//        });

        System.in.read();
    }

    /**
     * 两个阶段都正常执行完才能执行下一步
     * runAfterBoth(CompletionStage<?> other, Runnable action)
     * runAfterBothAsync(CompletionStage<?> other, Runnable action)
     * runAfterBothAsync(CompletionStage<?> other, Runnable action, Executor executor)
     */
    @Test
    public void runAfterBothAsync() throws Exception {
        CompletableFuture<Void> c1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i = 1 / 0;
        }, executor);
        CompletableFuture<Void> c2 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, executor);

        c1.runAfterBothAsync(c2, () -> System.out.println("都执行完了"));

        System.in.read();
    }

    /**
     * 要么其中一个阶段正常（如果能确定各个阶段的执行时间，那么时间少的如果执行出错，
     * 会一直不执行后续操作，无论另一个是否是正常执行，如果是时间最多的执行会出错，时间少的会正常执行完，
     * 那么action会正常的执行）执行完才会执行下一个操作
     * runAfterEither(CompletionStage<?> other, Runnable action)
     * runAfterEitherAsync(CompletionStage<?> other, Runnable action)
     * runAfterEitherAsync(CompletionStage<?> other, Runnable action, Executor executor)
     */
    @Test
    public void runAfterEitherAsync() throws Exception {
        CompletableFuture<Void> c1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            int i = 1 / 0;
        }, executor);
        CompletableFuture<Void> c2 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, executor);

        c1.runAfterEitherAsync(c2, () -> System.out.println("不晓得谁先执行完"));

        System.in.read();
    }

    /**
     * 某个阶段正常执行完后的执行操作
     * thenRunAsync(Runnable action)
     * thenRunAsync(Runnable action, Executor executor)
     */
    @Test
    public void thenRunAsync() throws Exception {
        CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> "c1", executor);

        c1.thenRunAsync(() -> System.out.println("执行完"));

        System.in.read();
    }

    /**
     * 接受两阶段的结果，并进行消费，无返回值
     * thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action)
     * thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action)
     * thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action, Executor executor)
     */
    @Test
    public void thenAcceptBothAsync() throws Exception {
        CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> "c1", executor);
        CompletableFuture<String> c2 = CompletableFuture.supplyAsync(() -> "c2", executor);

        c1.thenAcceptBothAsync(c2, new BiConsumer<String, String>() {
            @Override
            public void accept(String s, String s2) {
                System.out.println("最终的结果为：" + s + "和" + s2);
            }
        });

        System.in.read();
    }

    private Integer[] generateData() {
        Integer[] array = new Integer[5];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        return array;
    }

    private Integer[] generateDataForTime() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Integer[] array = new Integer[5];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        return array;
    }

}
