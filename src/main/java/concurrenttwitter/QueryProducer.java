package concurrenttwitter;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class QueryProducer implements Runnable {
    
    private Twitter twitter;
    private Query query;
    private BlockingQueue<Status> queue;
    private long interval;
    
    public QueryProducer(Query query, BlockingQueue<Status> queue) {
        twitter = TwitterFactory.getSingleton();
        this.query = query;
        this.queue = queue;
        interval = 5000;
    }
    
    @Override
    public void run() {
        
        while(true) {
            try {
                QueryResult result = twitter.search(query);
                for (Status tweet : result.getTweets()) { queue.put(tweet); }
                Thread.sleep(interval);
            } catch (TwitterException | InterruptedException ex) {
                Logger.getLogger(QueryProducer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setInterval(long millis) {
        interval = millis;
    }
}
