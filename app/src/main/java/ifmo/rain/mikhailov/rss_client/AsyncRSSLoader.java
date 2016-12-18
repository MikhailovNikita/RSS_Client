package ifmo.rain.mikhailov.rss_client;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.zip.DataFormatException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static ifmo.rain.mikhailov.rss_client.Constants.DEBUG_TAG_XML;

/**
 * Created by Михайлов Никита on 18.12.2016.
 * RSS_Client
 */

//TODO: rename class
public class AsyncRSSLoader extends AsyncTask<String, ArrayList<RSSItem>, ArrayList<RSSItem>> {

    public interface AsyncResponse {
        void processFinish(ArrayList<RSSItem> list);
    }


    public AsyncResponse delegate = null;

    @Override
    protected ArrayList<RSSItem> doInBackground(String... strings) {
        ArrayList<RSSItem> rssItems = new ArrayList<>();

        RSSItem templateRSSItem = new RSSItem("TITLE", "DESCRIPTION",
                new Date(), "LINK");

        rssItems.add(templateRSSItem);

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                //Let's try parse this XML
                InputStream is = connection.getInputStream();
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = factory.newDocumentBuilder();

                Document doc = docBuilder.parse(is);
                doc.getDocumentElement().normalize();
                Log.d(DEBUG_TAG_XML, "Root element: " + doc.getDocumentElement().getNodeName());
                NodeList nodeList = doc.getElementsByTagName("item");
                Log.d(DEBUG_TAG_XML, "LET'S ****ING PARSE THAT");
                Log.d(DEBUG_TAG_XML, "God save us");
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);


                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);

                    Element nodeElement = (Element) node;


                    String title = nodeElement.getElementsByTagName("title").item(0).getTextContent();
                    String link = nodeElement.getElementsByTagName("link").item(0).getTextContent();
                    String description = nodeElement.getElementsByTagName("description").item(0).getTextContent();
                    String pubDate = nodeElement.getElementsByTagName("pubDate").item(0).getTextContent();
                    Date publishDate;

                    try {
                        publishDate = dateFormat1.parse(pubDate);
                    } catch (ParseException e) {
                        try {
                            publishDate = dateFormat2.parse(pubDate);
                        } catch (ParseException ex) {
                            //TODO: add notification that data is not correct
                            publishDate = dateFormat2.parse("01 Jan 0000 00:00:00 +0000");
                        }
                    }


                    Log.d(DEBUG_TAG_XML, title);
                    Log.d(DEBUG_TAG_XML, pubDate);


                    RSSItem rssItem = new RSSItem(title, description, publishDate, link);


                    rssItems.add(rssItem);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rssItems;
    }

    public AsyncRSSLoader(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPostExecute(ArrayList<RSSItem> arrayList) {

        delegate.processFinish(arrayList);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> origin/leshabranch
