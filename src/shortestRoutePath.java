import javax.swing.*;
import java.io.*;
import java.io.IOException;

import javax.swing.JOptionPane;
import java.util.Scanner;

public class shortestRoutePath {

    /**
     * This finds the shortest route path. It displays the path taken and
     * the associated costs of the route.
     * This uses Dijkstra Implementation.
     */
    public final int STOPS = 12479;
    public double adjacencyList[][] = new double[STOPS][STOPS];

    double infinity = Double.POSITIVE_INFINITY;
    int maximum = Integer.MAX_VALUE;
    public final double HUNDRED = 100;

    shortestRoutePath() {

        try {
            adjacencyList();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * adjacencyList() creates an adjacency list that is used to hold the routes and
     * cost/distance associated with each route.
     */
    public void adjacencyList() throws FileNotFoundException {

        File stopTimes = new File("stop_times.txt");
        File transfers = new File("transfers.txt");

        Scanner scanner1 = new Scanner(stopTimes);
        Scanner scanner2 = null;

        double cost = 1;
        String current;
        int currentRoute = 0;
        int lastRoute;
        int source;
        int destination = 0;
        int transferType;
        double minimumTime;

        for (int u = 0; u < adjacencyList.length; u++) {
            for (int v = 0; v < adjacencyList.length; v++) {
                if (u != v) {
                    adjacencyList[u][v] = maximum;
                } else {
                    adjacencyList[u][v] = 0;
                }
            }
        }

        scanner1.nextLine();
        while (scanner1.hasNextLine()) {
            current = scanner1.nextLine();

            scanner2 = new Scanner(current);
            scanner2.useDelimiter(",");

            lastRoute = currentRoute;
            currentRoute = scanner2.nextInt();

            scanner2.next();
            scanner2.next();

            source = destination;
            destination = scanner2.nextInt();

            if (lastRoute == currentRoute) {
                adjacencyList[source][destination] = cost;
            }
            scanner2.close();
        }
        scanner1.close();

        scanner1 = new Scanner(transfers);
        scanner1.nextLine();

        while (scanner1.hasNextLine()) {
            current = scanner1.nextLine();

            scanner2 = new Scanner(current);
            scanner2.useDelimiter(",");

            source = scanner2.nextInt();
            destination = scanner2.nextInt();

            transferType = scanner2.nextInt();

            if (transferType == 0) {
                adjacencyList[source][destination] = 2;
            } else if (transferType == 2) {
                minimumTime = scanner2.nextDouble();
                adjacencyList[source][destination] = minimumTime / HUNDRED;
            }
            scanner2.close();
        }
        scanner1.close();
    }

    /**
     * Computes and returns the shortest-path  from the source vertex {@code source} to the
     * destination {@code destination} in the adjacency list {@code adjacencylist[][]}.
     */
    public String shortestRoutePath(int source, int destination) {

        String shortestPath = "";
        String noPath = "No path exists here";

        double distanceTo[] = new double[adjacencyList.length];
        int edgeTo[] = new int[adjacencyList.length];
        int visited[] = new int[adjacencyList.length];

        if (source == destination) {
            return adjacencyList[source][source] + " through the following path " + source;
        }

        for (int i = 0; i < distanceTo.length; i++) {
            if (i != source) {
                distanceTo[i] = infinity;
            }

        }
        int currentStop = source;
        int count = 0;

        visited[source] = 1;
        distanceTo[source] = 0;

        while (count < distanceTo.length) {

            for (int i = 0; i < adjacencyList[currentStop].length; i++) {

                if (visited[i] == 0 && !Double.isNaN(adjacencyList[currentStop][i])) {
                    relax(distanceTo, edgeTo, i, currentStop);
                }
            }

            visited[currentStop] = 1;
            double shortest = maximum;

            for (int j = 0; j < distanceTo.length; j++) {

                if (visited[j] != 1 && shortest > distanceTo[j]) {
                    currentStop = j;
                    shortest = distanceTo[j];
                }
            }
            count++;
        }

        int i = source;
        int j = destination;

        if (distanceTo[destination] == infinity) {
            return noPath;
        }

        while (j != i) {
            shortestPath = "\n" + edgeTo[j] + shortestPath;
            j = edgeTo[j];
        }

        shortestPath = shortestPath + "\n" + destination;

        return distanceTo[destination] + " through the following path: " + shortestPath;

    }

    /**
     * Relax edge
     */
    private void relax(double[] distanceTo, int[] edgeTo, int i, int currentStop) {

        if (distanceTo[i] > distanceTo[currentStop] + adjacencyList[currentStop][i]) {
            distanceTo[i] = distanceTo[currentStop] + adjacencyList[currentStop][i];
            edgeTo[i] = currentStop;
        }
    }

    /**
     * This method {@code manageRequest()} is called from the interface and the user is prompted to input the
     * two bus stops separately.
     * The adjacency list {@code adjacencyList[][]} is then created and shortestRoutePath is called.
     * The output is returned from shortestRoutePath and prints the cost and path associated with the route taken.
     */

    public static void manageRequest() {

        int busStop1 = 0;
        int busStop2 = 0;

        //User enters first bus stop number
        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Please Enter the First Bus Stop Number:"));
        JTextField textField1 = new JTextField(25);
        panel1.add(textField1);

        Object[] option1 = {"Enter"};

        int result1 = JOptionPane.showOptionDialog(null, panel1, "Vancouver Bus Management System", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, option1, null);

        try {
            if (result1 == 0) {
                busStop1 = Integer.parseInt(textField1.getText());
            }
        } catch (NumberFormatException incorrectInput) {
            JOptionPane.showMessageDialog(null, "This is not a valid number. Please try again");
            manageRequest();
        }

        //User enters second bus stop number
        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("Please Enter the Second Bus Stop Number:"));
        JTextField textField2 = new JTextField(25);
        panel2.add(textField2);

        Object[] option2 = {"Enter"};

        int result2 = JOptionPane.showOptionDialog(null, panel2, "Vancouver Bus Management System", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, option2, null);

        try {
            if (result2 == 0) {
                busStop2 = Integer.parseInt(textField2.getText());
            }
        } catch (NumberFormatException incorrectInput) {
            JOptionPane.showMessageDialog(null, "This is not a valid number. Please try again");
            manageRequest();
        }

        shortestRoutePath path = new shortestRoutePath();

        try {
            path.adjacencyList();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }

        String output = "Cost is " + path.shortestRoutePath(busStop1, busStop2);
        JOptionPane.showMessageDialog(null, output);

    }

}

