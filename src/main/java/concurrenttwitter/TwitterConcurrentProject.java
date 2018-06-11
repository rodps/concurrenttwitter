package concurrenttwitter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import twitter4j.*;

public class TwitterConcurrentProject {
    
    public static void main(String[] args) throws TwitterException, InterruptedException, ExecutionException {

        BlockingQueue queue = new LinkedBlockingQueue();
        ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
        
        String[] keywords = {"infosec","malware"};
        StreamListenerProducer streamlistener = new StreamListenerProducer(keywords);
        streamlistener.addListener(new TwitterStreamListener(queue));
        new Thread(streamlistener).start();
        
        Consumer consumer = new Consumer(queue, cache);
        for (int i = 0; i < 15; i++) {
            new Thread(consumer).start();
        }
    }
}
