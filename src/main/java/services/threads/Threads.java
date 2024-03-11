package services.threads;

public class Threads {
    public void sleepThread(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void waiting(){
        System.out.println("Waiting...");
        sleepThread(1000);
        System.out.println("Ready!!!!");
    }
}
