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
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static ifmo.rain.mikhailov.rss_client.Constants.DEBUG_TAG_XML;

/**
 * Created by Михайлов Никита on 18.12.2016.
 * RSS_Client
 */

//TODO: rename class
public class MyAsyncTask extends AsyncTask<String, ArrayList<RSSItem>, ArrayList<RSSItem>> {

    public interface AsyncResponse{
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

                for(int i = 0; i < nodeList.getLength(); i++){
                    Node node = nodeList.item(i);

                    Element nodeElement = (Element) node;



                    String title = nodeElement.getElementsByTagName("title").item(0).getTextContent();
                    String link = nodeElement.getElementsByTagName("link").item(0).getTextContent();
                    String description = nodeElement.getElementsByTagName("description").item(0).getTextContent();
                    String pubDate = nodeElement.getElementsByTagName("pubDate").item(0).getTextContent();

                    Log.d(DEBUG_TAG_XML, title);
                    Log.d(DEBUG_TAG_XML, pubDate);

                    //TODO: fix date issue
                    RSSItem rssItem = new RSSItem(title,description,new Date(), link);


                    rssItems.add(rssItem);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        return rssItems;
    }

    public MyAsyncTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPostExecute(ArrayList<RSSItem> arrayList) {

        delegate.processFinish(arrayList);
    }
}
