/*
 *  Learning Project.
 */
package concurrenttwitter;

import twitter4j.*;

/**
 * Get access twitter data
 * @author rodrigo
 */
public class TwitterConcurrentProject_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws TwitterException {
        
        /* API Searching */
        Twitter twitter = TwitterFactory.getSingleton();
        QueryResult result = twitter.search(new Query("security malware"));
        
        
        
//        result.getTweets().forEach((status) -> {
//            System.out.println("@" + status.getUser().getScreenName() + 
//                    ":" + status.getText());
//        });
        
        
        /* API Streaming */
        TwitterStreamListener listener = new TwitterStreamListener();
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(listener);
        
        String[] keywords = {"infosec","malware"};
        FilterQuery filterQuery = new FilterQuery(keywords);
        twitterStream.filter(filterQuery);
    }

}
