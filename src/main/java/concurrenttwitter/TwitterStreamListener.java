/*
 *  Learning Project.
 */
package concurrenttwitter;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

/**
 *
 * @author rodrigo
 */
public class TwitterStreamListener implements StatusListener {
    
    BlockingQueue queue;
    
    public TwitterStreamListener(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void onStatus(Status status) {
        System.out.println(status.getSource());
//        try {
//            queue.put(status);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(TwitterStreamListener.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice sdn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onTrackLimitationNotice(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onScrubGeo(long l, long l1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onStallWarning(StallWarning sw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onException(Exception excptn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
