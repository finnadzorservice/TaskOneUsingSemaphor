import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

public class MainUsingSemaphor {
    Semaphore semaphoreFirst;
    Semaphore semaphoreSecond;
    Semaphore semaphoreThird;

    public MainUsingSemaphor() {
        semaphoreFirst = new Semaphore(1);
        semaphoreSecond = new Semaphore(0);
        semaphoreThird = new Semaphore(0);
    }

    public void first() {
        try {
            semaphoreFirst.acquire();
            System.out.print("first");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        semaphoreSecond.release();
    }

    public void second() {
        try {
            semaphoreSecond.acquire();
            System.out.print("second");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        semaphoreThird.release();
    }

    public void third() {
        try {
            semaphoreThird.acquire();
            System.out.print("third");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        semaphoreThird.release();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MainUsingSemaphor mainUsingSemaphor = new MainUsingSemaphor();

        CompletableFuture<Void> doSecond = CompletableFuture.runAsync(mainUsingSemaphor::second);
        CompletableFuture<Void> doThird = CompletableFuture.runAsync(mainUsingSemaphor::third);
        CompletableFuture<Void> doFirst = CompletableFuture.runAsync(mainUsingSemaphor::first);
        doThird.get();
        doFirst.get();
        doSecond.get();
    }
}

