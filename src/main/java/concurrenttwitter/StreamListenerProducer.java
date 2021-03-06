package concurrenttwitter;

import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class StreamListenerProducer implements Runnable{
    TwitterStream twitterStream;
    private String[] keywords;
    private long interval;
    
    public StreamListenerProducer(String[] keywords) {
        twitterStream = new TwitterStreamFactory().getInstance();
        this.keywords = keywords;
    }
    
    @Override
    public void run() {
        
        FilterQuery filterQuery = new FilterQuery(keywords);
        twitterStream.filter(filterQuery);
    }
    
    public void addListener(StatusListener listener) {
        twitterStream.addListener(listener);
    }
    
}
