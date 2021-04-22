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

    public static void createBST(String fileToRead, SearchArrivalTime<String, String> st) {
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

                String arrivalTime = lineData[1];

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

    public static void printSortedTripsByArrivalTime(String arrivalTime,SearchArrivalTime<String,String> c){
        ArrayList<Trip> sortedTrips = c.getSortedTripsByArrivalTime(arrivalTime);
        String results = "";

        for (int i = 0; i < sortedTrips.size(); i++){
            Trip trip = sortedTrips.get(i);


            results += sortedTrips.get(i).tripId +
                    " departure Time: " +  trip.departureTime +
                    " Stop Id: " + trip.stopId +
                    " Stop Sequence: " +  trip.stopSequence + "\n";

        }
        System.out.println(results);
        System.out.println("************\n");

    }


    public static void main(String[] args) {

        SearchArrivalTime<String,String> c = new SearchArrivalTime<String,String>();

        createBST("stop_times.txt",c);

        printSortedTripsByArrivalTime(" 5:29:24",c);
        printSortedTripsByArrivalTime(" 8:18:24",c);




    }

}