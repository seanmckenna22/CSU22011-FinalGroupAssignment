import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * This file will search through the arrival times and return those that satisfy
 * the given criteria
 *
 * This will require the bus stop times to be sorted so that they can be searched efficiently
 */


public class SearchArrivalTime <Key extends Comparable <Key>,Value> {


    public HashMap<String,ArrayList<Trip>> arrivalTimeMap = new HashMap<String,ArrayList<Trip>>();


    public  ArrayList<Trip> getSortedTripsByArrivalTime(String arrivalTime) {

        ArrayList<Trip> tripValues = this.arrivalTimeMap.get(arrivalTime);

        Collections.sort(tripValues, (trip1, trip2) -> trip1.tripId.compareTo(trip2.tripId));

        return tripValues;

    }

    /**
     * Creates a binary search tree reading in the files and then using put method to enter bst
     * @param fileToRead
     * @param st
     */

    public static void createHashMap(String fileToRead, SearchArrivalTime<String, String> st) {
        try {
            Scanner input = new Scanner(new FileInputStream(fileToRead));

            int line = 0;
            String dump;

            while (input.hasNextLine()) {
                if(line == 0){
                    dump = input.nextLine();
                    line ++;
                }

                String[] lineData = input.nextLine().trim().split(",");
                line ++;

                String arrivalTime = lineData[1].trim();

                String distanceTravelled = "";
                if(lineData.length > 8){
                    distanceTravelled = lineData[8];
                }


                Trip newTrip = new Trip(lineData[0],lineData[2],lineData[3],lineData[4],lineData[5],lineData[6],lineData[7],distanceTravelled);

                if(!st.arrivalTimeMap.containsKey(arrivalTime)){
                    st.arrivalTimeMap.put(arrivalTime,new ArrayList<>());

                }

                st.arrivalTimeMap.get(arrivalTime).add(newTrip);

            }

            input.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public static String createStringForSortedTripsByArrivalTime(String arrivalTime,SearchArrivalTime<String,String> c){

        ArrayList<Trip> sortedTrips = c.getSortedTripsByArrivalTime(arrivalTime);
        String results = "";

        for (int i = 0; i < sortedTrips.size(); i++){
            Trip trip = sortedTrips.get(i);


            results += sortedTrips.get(i).tripId +
                    " departure Time: " +  trip.departureTime +
                    " Stop Id: "  + trip.stopId +
                    " Stop Sequence: " +  trip.stopSequence +
                    " Stop Headsign: "  + trip.stopHeadSign +
                    " Pick Up type: "  + trip.pickUpTypes +
                    " Drop Off Type: "  + trip.dropOffType  +
                    " Distance Travelled: "  +  trip.distanceTravelled + "\n";

        }
        return results;

    }

    /**
     * This method manages the systems request to find the right key pair value
     * @param key
     */

    public static void manageRequest(String key){

        String keyWithoutColon = key.replaceAll(":","");

        try{
            Integer.parseInt(keyWithoutColon);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Invalid input, you cannot enter text, it must be a valid time. Thank You");
            return;
        }

        int keyAsInt = Integer.parseInt(keyWithoutColon);

        if(key == null || keyAsInt > 235959 || keyAsInt < 0){
            JOptionPane.showMessageDialog(null,"The time entered is invalid, it must fall " +
                    "between 0:00:00 and 23:59:59, thank you.");
            return;
        }
        SearchArrivalTime<String,String> c = new SearchArrivalTime<String,String>();
        createHashMap("stop_times.txt",c);
    try {
            String result = createStringForSortedTripsByArrivalTime(key, c);

            if (result == null) {
                JOptionPane.showMessageDialog(null, "There is no time that matches your criteria \n" +
                        "Please try again");
            } else {
                JOptionPane.showMessageDialog(null, "Arrival Time " + key + "\n" + result);
            }
        }
        catch(Exception e){
        JOptionPane.showMessageDialog(null,"No valid input found, Please use the format H:MM:SS, thank you");
        return;

        }
    }



}