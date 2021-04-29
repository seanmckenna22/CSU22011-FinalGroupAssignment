import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.io.*;

public class Graph {

    public int numberOfStops;
    public double weight;
    public static ArrayList<BusStop> busStops;
    public static ArrayList<ArrayList<Edge>> adjacent;


    public Graph(String stopTimes, String transfers, String stops) {

        busStops = new ArrayList<>();
        addStops(stops);
        adjacent = new ArrayList<>(busStops.size());
        numberOfStops = busStops.size();

        for (int i = 0; i < busStops.size(); i++) {
            adjacent.add(new ArrayList<>());
        }

        addStopEdges(stopTimes);
        addTransferEdges(transfers);
    }

    private static void addStops(String filename) {

        try {
            File inputFile = new File(filename);
            Scanner scan = new Scanner(inputFile);

            scan.useDelimiter(",");
            scan.nextLine();

            while (scan.hasNext()) {

                if (scan.hasNextInt()) {
                    busStops.add(new BusStop(scan.nextInt()));
                    scan.nextLine();
                }
            }
            scan.close();

        } catch (IOException e) {
            busStops = null;
        }
    }

    private static void addTransferEdges(String filename) {
        try {

            int destinationStop;
            int fromStop;
            double weight;

            File inputFile = new File(filename);
            Scanner scan = new Scanner(inputFile);
            scan.useDelimiter(",|\\n");
            scan.nextLine();

            while (scan.hasNext()) {
                fromStop = scan.nextInt();
                destinationStop = scan.nextInt();

                if (scan.nextInt() == 2){
                    weight = (scan.nextInt() / 100);
                    scan.nextLine();
                }

                else {
                    weight = 2;
                    scan.nextLine();
                }

                if (adjacent.get(findStop(fromStop)).isEmpty()){

                    ArrayList<Edge> e = new ArrayList<Edge>();
                    e.add(new Edge(fromStop, destinationStop, weight));
                    adjacent.add(e);
                }

                else adjacent.get(findStop(fromStop)).add(new Edge(fromStop, destinationStop, weight));
            }
            scan.close();
        }

        catch (IOException e) {
            adjacent = null;
        }
    }

    private static void addStopEdges(String filename) {

        try {

            int currentStop;
            int currentRoute;
            int lastStop;
            int lastRoute;

            File inputFile = new File(filename);
            Scanner scan = new Scanner(inputFile);
            scan.useDelimiter(",");
            scan.nextLine();

            lastRoute = scan.nextInt();
            scan.next();
            scan.next();
            lastStop = scan.nextInt();
            scan.nextLine();

            while (scan.hasNext()) {
                currentRoute = scan.nextInt();
                scan.next();
                scan.next();

                if (lastRoute == currentRoute) {
                    currentStop = scan.nextInt();

                    adjacent.get(findStop(lastStop)).add(new Edge(lastStop, currentStop, 1));
                    scan.nextLine();
                }

                else {
                    lastRoute = scan.nextInt();
                    scan.next();
                    scan.next();
                    lastStop = scan.nextInt();
                    scan.nextLine();
                }
            }
            scan.close();
        }

        catch (IOException e) {
            adjacent = null;
        }
    }

    public static int findStop(int busStopID) {

        for (int i = 0; i < busStops.size(); i++) {

            if (busStops.get(i).busStopID == busStopID) {
                return i;
            }
        }
        return -1;
    }

}
