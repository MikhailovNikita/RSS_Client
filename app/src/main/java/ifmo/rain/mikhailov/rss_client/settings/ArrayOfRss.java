package ifmo.rain.mikhailov.rss_client.settings;

import java.util.List;

/**
 * Created by user on 19.12.2016.
 */

public class ArrayOfRss {
    List<ChannelRSS> MainRss;
    List<ChannelRSS> PoliticRss;
    List<ChannelRSS> BusinessRss;
    List<ChannelRSS> SocietyRss;
    List<ChannelRSS> WorldRss;
    List<ChannelRSS> SportRss;
    List<ChannelRSS> IncidentRss;
    List<ChannelRSS> CultureRss;
    List<ChannelRSS> ScienceRss;
    List<ChannelRSS> ComputersRss;
    List<ChannelRSS> AutoRss;

    public void add(String name, ChannelRSS rss){
        switch (name){
            case ("MainRss"):{
                MainRss.add(rss);
                break;
            }
            case ("PoliticRss"):{
                PoliticRss.add(rss);
                break;
            }
            case ("BusinessRss"):{
                BusinessRss.add(rss);
                break;

            }
            case ("SocietyRss"):{
                SocietyRss.add(rss);
                break;
            }
            case ("WorldRss"):{
                WorldRss.add(rss);
                break;
            }
            case ("SportRss"):{
                SportRss.add(rss);
                break;
            }
            case ("IncidentRss"):{
                IncidentRss.add(rss);
                break;
            }
            case ("CultureRss"):{
                CultureRss.add(rss);
                break;
            }
            case ("ScienceRss"):{
                ScienceRss.add(rss);
                break;

            }
            case ("ComputersRss"):{
                ComputersRss.add(rss);
                break;
            }
            case ("AutoRss"):{
                AutoRss.add(rss);
                break;
            }
        }
    }

    public void addDefoult(){


    }



}
