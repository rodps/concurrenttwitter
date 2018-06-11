package concurrenttwitter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;

public class Consumer implements Runnable {

    private BlockingQueue<Status> queue;
    ConcurrentHashMap<String, String> cache;

    public Consumer(BlockingQueue<Status> queue, ConcurrentHashMap<String, String> cache) {
        this.queue = queue;
        this.cache = cache;
    }

    @Override
    public void run() {
        while (true) {

            try {
                Status tweet = queue.take();
                List<String> urls = getURLs(tweet.getText());
                
                System.out.println("ID: " + tweet.getId());
                System.out.println("User: " + tweet.getUser().getName());
                System.out.println("Date: " + tweet.getCreatedAt());
                System.out.println("Text: " + tweet.getText());
                System.out.println("URLs: ");
                for (String url : urls) {
                    String expandedUrl = cache.get(url);
                    if (expandedUrl == null) {
                        expandedUrl = expandShortURL(url);
                        cache.put(url, expandedUrl);
                    }
                    System.out.println(expandShortURL(url));
                }
                System.out.println("");
            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private List<String> getURLs(String text) {
        List<String> urls = new ArrayList<>();

        for (String token : text.split(" ")) {

            if (token.length() > 7) {

                if (token.substring(0, 8).equals("https://")) {
                    urls.add(token);
                }
                if (token.substring(0, 7).equals("http://")) {
                    urls.add(token);
                }
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
