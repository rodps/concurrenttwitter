package concurrenttwitter;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;

public class Consumer implements Runnable {
    
    private BlockingQueue<Status> queue;
    
    public Consumer(BlockingQueue<Status> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true) {
            try {
                String[] tokens = getTokens(queue.take().getText());
                System.out.println(Arrays.toString(tokens));
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private String[] getTokens(String text) {
        return text.split(" ");
    }
    
}
