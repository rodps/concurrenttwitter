package concurrenttwitter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
                List<String> urls = getURLs(queue.take().getText());
                for (String url : urls) {
                    System.out.println(expandShortURL(url));
                }
//                System.out.println(urls);
            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private List<String> getURLs(String text) {
        List<String> urls = new ArrayList<>();
        
        for (String token : text.split(" ")) {

            if(token.length() > 7) {
                
                if(token.substring(0, 8).equals("https://"))
                    urls.add(token);
                if(token.substring(0, 7).equals("http://"))
                    urls.add(token);
            }
        }
        return urls;
    }
    
    private String expandShortURL(String address) throws IOException {
        URL url = new URL(address);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY); //using proxy may increase latency
        connection.setInstanceFollowRedirects(false);
        connection.connect();
        
        String expandedURL = connection.getHeaderField("Location");
        connection.getInputStream().close();
        
        return expandedURL;
    }
    
}
