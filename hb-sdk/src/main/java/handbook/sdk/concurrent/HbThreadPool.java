package handbook.sdk.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class HbThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("order-list-syncer-pool-%d").build();

        ExecutorService executorService = new ThreadPoolExecutor(50, 100,
                30000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024),
                namedThreadFactory);

        /*
        submit过程
          1. 将runnable包装成RunnableFuture
          2. 如果线程数小于corePoolSize，那么新增一个Worker。Worker包装了RunnableFuture、Thread; 如果线程数达到了corePoolSize，
             那么直接放入队列，并且检查worker数(如果没了需要新增)；如果达到了corePoolSize、队列已满、未达到maximumPoolSize，那么会
             启动新的worker。
          3. worker执行run方法，实际执行的是ExecutorService.runWorker

        runWorker过程
          1. 不断地从queue里面读取task
          2. 必要的话，调用interrupt方法
          3. 执行一个task
          4. 如果读不到task(例如主线程调用shutdown，进而调用了interrupt)，就进行收尾工作
          5. 看看所有work是不是都没了，如果是的话，pool就停止了
         */
        // 方式1
        executorService.submit(new TestRunnable());

        // 方式2
        Future<Integer> future1 = executorService.submit(() -> runnable(1));
        System.out.println(future1.get());

        // 方式3
        Future<Integer> future2 = executorService.submit(new TestRunnable(), 2);
        System.out.println(future2.get());

        // 执行中的worker不会被interrupt，因为有锁的保护
        executorService.submit(new TestRunnable());

        //调用所有worker的interrupt方法，等待worker结束
        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.SECONDS);

    }

    private static class TestRunnable implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // showdown不会触发到这里
                System.out.println("interrupt");
                return;
            }
            System.out.println("stop");
        }
    }

    static private Integer runnable(Integer data) {
        return data;
    }

}