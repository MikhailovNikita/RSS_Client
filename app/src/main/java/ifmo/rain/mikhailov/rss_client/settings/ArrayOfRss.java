package ifmo.rain.mikhailov.rss_client.settings;

import java.util.List;

/**
 * Created by user on 19.12.2016.
 */

public class ArrayOfRss {
    List<ChanelRss> MainRss;
    List<ChanelRss> PoliticRss;
    List<ChanelRss> BusinessRss;
    List<ChanelRss> SocietyRss;
    List<ChanelRss> WorldRss;
    List<ChanelRss> SportRss;
    List<ChanelRss> IncidentRss;
    List<ChanelRss> CultureRss;
    List<ChanelRss> ScienceRss;
    List<ChanelRss> ComputersRss;
    List<ChanelRss> AutoRss;

    public void add(String name, ChanelRss rss){
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
