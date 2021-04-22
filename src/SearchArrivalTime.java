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
    private Node root;
    public Value[] values;
    public  ArrayList<Value> valueArray = new ArrayList<>();
    public int iterator = 0;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int size;
        private boolean alreadyVisited;


        public Node(Key key, Value val, int size, boolean alreadyVisited) {
            this.key = key;
            this.val = val;
            this.size = size;
            this.alreadyVisited = alreadyVisited;
        }
    }

    public HashMap<String,ArrayList<Trip>> arrivalTimeMap = new HashMap<String,ArrayList<Trip>>();


    /**
     * Initializes an empty symbol table
     */

    public SearchArrivalTime() {

    }

    /**
     * Returns true if this symbol table is empty
     *
     * @return {@code true} if this symbol table is empty;{@code false} otherwise
     */

    public boolean isEmpty() {
        return size() == 0;

    }

    /**
     * return number of key-pair values
     */

    public int size() {
        return size(root);
    }

    public int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    /**
     * Checks to see if symbol table contains key that is given
     *
     * @param key the Key
     * @return {@code true} if ST contains {@code Key} and {@code false} otherwise
     */

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /**
     * return value associated with the given key
     *
     * @param key the Key
     * @return the value associated with the given key, and {@code null} if the key is not in ST
     * @throws IllegalArgumentException if {@code Key} is {@code null}
     */

    public ArrayList<Value> get(Key key) {
        return get(root, key);
    }

    private ArrayList<Value> get(Node x, Key key) {

        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null;

        int cmp = key.compareTo(x.key);

        if (cmp < 0) return get(x.left, key);

        else if (cmp > 0) return get(x.right, key);

        else if (x.alreadyVisited) {

            return get(x.left, key);
        } else {

            x.alreadyVisited = true;
            valueArray.add(x.val);
            iterator++;

            if (x.left != null) {
                return get(x.left, key);
            }

        }
        return valueArray;
    }


    /**
     * Deletes Key
     *
     * @param key
     */

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else if (cmp == 0) x.left = delete(x.left, key);

        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) - 1;
        return x;
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty ST");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);

    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Inserts speciifed key-value pair into the symbol table, overwriting old val
     * with new value if st contains speicified key
     * deletes speicifed key (and associated value) from ST
     * if specified value is {@code null}
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */

    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);

    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1, false);

        int cmp = key.compareTo(x.key);

        if (cmp < 0) x.left = put(x.left, key, val);

        else if (cmp > 0) x.right = put(x.right, key, val);

        else x.left = put(x.left, key, val);

        x.size = 1 + size(x.left) + size(x.right);

        return x;
    }

    /**
     * For debugging purposes - returns the height of BSt
     *
     * @return height of a bst (1 node tree has height 0)
     */

    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }



    /**
     * Sorts trip id's into the right order so it can be shown in the right order to the user
     * @param tripInformation
     * @return output
     */

    public  String sortByTripID(Key x) {

        ArrayList<Value> tripValues = get(x);

        String [] tripInformation = tripValues.toArray(new String[tripValues.size()]);

        String output = "";

        int[] toBeSorted = new int[tripInformation.length];

        for (int i = 0; i < tripInformation.length; i++) {
            String currentString = tripInformation[i];
            int result = Integer.parseInt(currentString.substring(9, 16));
            toBeSorted[i] = result;
        }

        int[] sortedArray = Arrays.copyOf(toBeSorted, toBeSorted.length);

        Arrays.sort(sortedArray);

        for (int i = 0; i < sortedArray.length; i++) {
            for (int j = 0; j < sortedArray.length; j++) {
                if (toBeSorted[i] == sortedArray[j]) {
                    output += "\n" + tripInformation[j];
                }

            }
        }
        return output;
    }

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

                String tripInformation = "Trip Id: " + lineData[0] + "// Departure Time: " + lineData[2] +
                        "// Stop Id : " + lineData[3] + "// Stop Sequence: " + lineData[4] + "// Stop headsign: " +
                        lineData[5] + "// pickup Type: " + lineData[6] + "// dropoff Type: " + lineData[7] ; //"// shape distance travelled: " + lineData[8]

               // st.put(arrivalTime, tripInformation);

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


    public static void main(String[] args) {

        SearchArrivalTime<String,String> c = new SearchArrivalTime<String,String>();

        createBST("stop_times.txt",c);
        //System.out.println(c.sortByTripID(" 5:29:24"));

        ArrayList<Trip> sortedTrips = c.getSortedTripsByArrivalTime(" 5:29:24");
        String results = "";

        for (int i = 0; i < sortedTrips.size(); i++){
            Trip trip = sortedTrips.get(i);


            results += sortedTrips.get(i).tripId +
                    " departure Time: " +  trip.departureTime +
                    " Stop Id: " + trip.stopId +
                    " Stop Sequence: " +  trip.stopSequence + "\n";

        }
        System.out.println(results);

    }

}