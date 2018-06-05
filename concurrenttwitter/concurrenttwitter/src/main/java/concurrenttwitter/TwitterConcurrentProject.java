package concurrenttwitter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import twitter4j.*;

public class TwitterConcurrentProject {
    
    public static void main(String[] args) throws TwitterException, InterruptedException, ExecutionException {
        BlockingQueue queue = new LinkedBlockingQueue();
        QueryProducer queryproducer = new QueryProducer(new Query("security malware"), queue);
        queryproducer.setInterval(5000);
        Consumer consumer = new Consumer(queue);
//        ExecutorService executorService = Executors.newFixedThreadPool(15);;
        
        new Thread(queryproducer).start();
        for (int i = 0; i < 15; i++) {
            new Thread(consumer).start();
        }       
    }
}
