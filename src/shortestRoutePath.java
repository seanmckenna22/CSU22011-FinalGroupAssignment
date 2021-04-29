import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.util.*;

public class shortestRoutePath {

    /**
     * This finds the shortest route path, display the route and
     * the associated costs of the route
     * This will use Dijkstra Implementation
     */
    public double[] distanceTo;          // distanceTo[v] = distance  of shortest s->v path
    public Edge[] edgeTo;        // edgeTo[v] = last edge on shortest s->v path
    public double infinity = Double.POSITIVE_INFINITY;
    public double max = Double.MAX_VALUE;
    public String shortestPath;
    public boolean[] visited;

        /**
         * Computes a shortest-paths tree from the source vertex {@code s} to every other
         * vertex in the edge-weighted digraph {@code G}.
         *
         * @param G the edge-weighted digraph
         * @param s the source vertex
         * @throws IllegalArgumentException if an edge weight is negative
         * @throws IllegalArgumentException unless {@code 0 <= s < V}
         */
        public shortestRoutePath(Graph G, int s, int dest) {

            this.distanceTo = new double[G.numberOfStops];
            this.edgeTo = new Edge[G.numberOfStops];
            this.visited = new boolean[G.numberOfStops];

            for (int v = 0; v < distanceTo.length; v++) {
                distanceTo[v] = infinity;
                visited[v] = false;
            }
            distanceTo[s] = 0.0;

            // relax vertices in order of distance from s
           // priorityQueue = new IndexMinPQ<Double>(G.V());
            //priorityQueue.insert(s, distanceTo[s]);

            for (int i = 0; i < G.numberOfStops - 1; i++) {
                int j = minimumDistance(distanceTo, visited);

                if(j < 0)
                    continue;

                visited[j] = true;

                for(Edge e : G.adjacent.get(j))
                    relax(e);
            }

            shortestPath = getShortestPath(s, dest);
            // check optimality conditions
           // assert check(G, s);
        }

        // relax edge e
        private void relax(Edge e) {
            int v = e.fromV;
            int w = e.endV;

            if (distanceTo[w] > (distanceTo[v] + e.weight)) {
                distanceTo[w] = distanceTo[v] + e.weight;
                edgeTo[w] = e;

            }
        }

        /**
         * Returns the length of a shortest path from the source vertex {@code s} to vertex {@code v}.
         *
         * @param v the destination vertex
         * @return the length of a shortest path from the source vertex {@code s} to vertex {@code v};
         * {@code Double.POSITIVE_INFINITY} if no such path
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public int minimumDistance(double [] distanceTo, boolean [] visited) {

            double minimum = max;
            int number = -1;

            for (int i = 0; i < visited.length; i++) {
                if ((distanceTo[i] <= minimum) && !visited[i]) {
                    minimum = distanceTo[i];
                    number = i;
                }
            }
            return number;

            //validateVertex(v);
            //return distanceTo[v];
        }

        /**
         * Returns true if there is a path from the source vertex {@code s} to vertex {@code v}.
         *
         * @param v the destination vertex
         * @return {@code true} if there is a path from the source vertex
         * {@code s} to vertex {@code v}; {@code false} otherwise
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        //public boolean hasPathTo(int v) {
          //  validateVertex(v);
            //return distanceTo[v] < Double.POSITIVE_INFINITY;
        //}

        /**
         * Returns a shortest path from the source vertex {@code s} to vertex {@code v}.
         *
         * @param v the destination vertex
         * @return a shortest path from the source vertex {@code s} to vertex {@code v}
         * as an iterable of edges, and {@code null} if no such path
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public String getShortestPath(int busStop1, int busStop2) {

            Stack<Integer> busStops = new Stack<Integer>();
            busStops.push(busStop2);

            while(true){

                int temporary = edgeTo[busStop2].fromV;
                busStops.push(temporary);
                if(temporary == busStop1)
                    break;
            }
            shortestPath = ("The Shortest Path from Bus Stop" + busStop1 + " to " + busStop2 + " is: ");

            while(!busStops.isEmpty()){
                shortestPath += (busStops.pop() + "\n");
            }

            return shortestPath;
        }

        public static void manageRequest(){

            String file1 = "stop_times.txt";
            String file2 = "transfers.txt";
            String file3 = "stops.txt";

            //User enters first bus stop number
            JPanel panel1 = new JPanel();
            panel1.add(new JLabel("Please Enter the First Bus Stop Number:"));
            JTextField textField1 = new JTextField(50);
            panel1.add(textField1);

            Object[] option1 = {"Enter"};

            int busStop1 = JOptionPane.showOptionDialog(null, panel1, "Vancouver Bus Management System", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, option1, null);

            //User enters second bus stop number
            JPanel panel2 = new JPanel();
            panel2.add(new JLabel("Please Enter the Second Bus Stop Number:"));
            JTextField textField2 = new JTextField(50);
            panel2.add(textField2);

            Object[] option2 = {"Enter"};

            int busStop2 = JOptionPane.showOptionDialog(null, panel2, "Vancouver Bus Management System", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, option2, null);

            Graph graph = new Graph(file1, file2, file3);
            shortestRoutePath path = new shortestRoutePath(graph, busStop1, busStop2);

            System.out.println(path.shortestPath);
            System.out.println(path.distanceTo);
        }
    }
