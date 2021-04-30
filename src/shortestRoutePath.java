import javax.swing.*;
import java.io.*;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.util.Scanner;

public class shortestRoutePath {

    /**
     * This finds the shortest route path, display the route and
     * the associated costs of the route
     * This will use Dijkstra Implementation
     */
    public final int STOPS = 12479;
    public double adjacencyList[][] = new double[STOPS][STOPS];

    double distanceTo[] = new double[adjacencyList.length];
    int edgeTo[] = new int[adjacencyList.length];
    int visited[] = new int[adjacencyList.length];
    double infinity = Double.POSITIVE_INFINITY;
    double maximum = Double.MAX_VALUE;
    public final double HUNDRED = 100;

    public shortestRoutePath(String file1, String file2) {

        try {
            adjacencyList(file1, file2);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Computes a shortest-paths tree from the source vertex {@code s} to every other
     * vertex in the edge-weighted digraph {@code G}.
     *
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public String getShortestPath(int source, int destination) {

        String shortestPath = "Shortest Path is ";
        String noPath = "No path exists here";

        if (source == destination) {
            return adjacencyList[source][source] + " through the following path " + source;
        }

        for (int i = 0; i < visited.length; i++) {
            if (i != source) {
                distanceTo[i] = infinity;
            }
        }
        int currentStop = source;
        int count = 0;

        visited[source] = 1;
        distanceTo[source] = 0;

        // relax vertices in order of distance from s
        // priorityQueue = new IndexMinPQ<Double>(G.V());
        //priorityQueue.insert(s, distanceTo[s]);

        while (count < distanceTo.length) {

            for (int i = 0; i < adjacencyList[currentStop].length; i++) {
                if (visited[i] == 0 && !Double.isNaN(adjacencyList[currentStop][i])) {
                    relax(distanceTo, edgeTo, i, currentStop);
                }
            }
            count++;
        }

        if (distanceTo[destination] == infinity) {
            return noPath;
        }

        while (source != destination) {
            shortestPath = ", " + edgeTo[destination] + shortestPath;
            destination = edgeTo[destination];
        }

        shortestPath = shortestPath + ", " + destination;

        return distanceTo[destination] + " through the following path " + shortestPath;

    }

    // relax edge
    private void relax(double[] distanceTo, int[] edgeTo, int i, int currentStop) {

        if (distanceTo[i] > (distanceTo[currentStop] + adjacencyList[currentStop][i])) {
            distanceTo[i] = distanceTo[currentStop] + adjacencyList[currentStop][i];
            edgeTo[i] = currentStop;
        }
    }

    /**
     * Returns a shortest path from the source vertex {@code s} to vertex {@code v}.
     *
     * @return a shortest path from the source vertex {@code s} to vertex {@code v}
     * as an iterable of edges, and {@code null} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public void adjacencyList(String file1, String file2) throws FileNotFoundException {

        File stopTimes = new File(file1);
        File transfers = new File(file2);

        Scanner scanner1 = new Scanner(stopTimes);
        Scanner scanner2 = null;

        double cost = 1;
        String current;
        int currentRoute = 0;
        int lastRoute = 0;
        int source = 0;
        int destination = 0;
        int transferType;
        double minimumTime;

        for (int u = 0; u < adjacencyList.length; u++) {
            for (int v = 0; v < adjacencyList.length; v++) {
                if (u != v) {
                    adjacencyList[u][v] = maximum;
                } else adjacencyList[u][v] = 0;
            }
        }

        scanner1.nextLine();
        while (scanner1.hasNextLine()) {
            current = scanner1.nextLine();

            scanner2 = new Scanner(current);
            scanner2.useDelimiter(",");

            lastRoute = currentRoute;
            currentRoute = scanner2.nextInt();

            scanner2.nextInt();
            scanner2.nextInt();

            source = destination;
            destination = scanner2.nextInt();

            if (lastRoute == currentRoute) {
                adjacencyList[source][destination] = cost;
            }
            scanner2.close();
        }
        scanner1.close();

        cost = 2;
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
                adjacencyList[source][destination] = cost;
            } else {
                minimumTime = scanner2.nextDouble();
                adjacencyList[source][destination] = minimumTime / HUNDRED;
            }
            scanner2.close();
        }
        scanner1.close();
    }

    public static void manageRequest() {

        int busStop1 = 0;
        int busStop2 = 0;

        String file1 = "stop_times.txt";
        String file2 = "transfers.txt";
        String file3 = "stops.txt";

        //User enters first bus stop number
        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Please Enter the First Bus Stop Number:"));
        JTextField textField1 = new JTextField(50);
        panel1.add(textField1);

        Object[] option1 = {"Enter"};

        int result1 = JOptionPane.showOptionDialog(null, panel1, "Vancouver Bus Management System", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, option1, null);

        if (result1 == 0) {
            busStop1 = Integer.parseInt(textField1.getText());
        }

        //User enters second bus stop number
        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("Please Enter the Second Bus Stop Number:"));
        JTextField textField2 = new JTextField(50);
        panel2.add(textField2);

        Object[] option2 = {"Enter"};

        int result2 = JOptionPane.showOptionDialog(null, panel2, "Vancouver Bus Management System", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, option2, null);

        if (result2 == 0) {
            busStop2 = Integer.parseInt(textField2.getText());
        }

        shortestRoutePath path = new shortestRoutePath(file1, file2);

        JOptionPane.showMessageDialog(null, path.getShortestPath(busStop1, busStop2));
    }

}

