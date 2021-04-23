import java.io.BufferedReader;

public class shortestRoutePath {

    /**
     * This is a skeleton file to find the shortest route path, display the route and
     * the associated costs of the route
     * This will use Dijkstra Implementation
     */

    
        final static int INF = 99999, V = 4;

    public class Dijkstra {

        private double[] distanceTo;          // distTo[v] = distance  of shortest s->v path
        private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
        private IndexMinPQ<Double> priorityQueue; //priority queue of vertices
        /**
         * Computes a shortest-paths tree from the source vertex {@code s} to every other
         * vertex in the edge-weighted digraph {@code G}.
         *
         * @param  G the edge-weighted digraph
         * @param  s the source vertex
         * @throws IllegalArgumentException if an edge weight is negative
         * @throws IllegalArgumentException unless {@code 0 <= s < V}
         */
        public Dijkstra(EdgeWeightedDigraph G, int s) {

            for (DirectedEdge e : G.edges()) {
                if (e.weight() < 0)
                    throw new IllegalArgumentException("edge " + e + " has negative weight");
            }

            distanceTo = new double[G.V()];
            edgeTo = new DirectedEdge[G.V()];

            validateVertex(s);

            for (int v = 0; v < G.V(); v++)
                distanceTo[v] = Double.POSITIVE_INFINITY;
            distanceTo[s] = 0.0;

            // relax vertices in order of distance from s
            priorityQueue = new IndexMinPQ<Double>(G.V());
            priorityQueue.insert(s, distanceTo[s]);

            while (!priorityQueue.isEmpty()) {
                int v = priorityQueue.delMin();
                for (DirectedEdge e : G.adj(v))
                    relax(e);
            }

            // check optimality conditions
            assert check(G, s);
        }

        // relax edge e and update priorityQueue if changed
        private void relax(DirectedEdge e){
            int v = e.from();
            int w = e.to();

            if(distanceTo[w] > (distanceTo[v] + e.weight())){
                distanceTo[w] = distanceTo[v] + e.weight();
                edgeTo[w] = e;
                if(priorityQueue.contains(w)){
                    priorityQueue.decreaseKey(w, distoTo[w]);
                } else {
                    priorityQueue.insert(w, distTo[w]);
                }
            }
        }

        /**
         * Returns the length of a shortest path from the source vertex {@code s} to vertex {@code v}.
         * @param  v the destination vertex
         * @return the length of a shortest path from the source vertex {@code s} to vertex {@code v};
         *         {@code Double.POSITIVE_INFINITY} if no such path
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public double distanceTo(int v) {
            validateVertex(v);
            return distanceTo[v];
        }

        /**
         * Returns true if there is a path from the source vertex {@code s} to vertex {@code v}.
         *
         * @param  v the destination vertex
         * @return {@code true} if there is a path from the source vertex
         *         {@code s} to vertex {@code v}; {@code false} otherwise
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public boolean hasPathTo(int v) {
            validateVertex(v);
            return distanceTo[v] < Double.POSITIVE_INFINITY;
        }

        /**
         * Returns a shortest path from the source vertex {@code s} to vertex {@code v}.
         *
         * @param  v the destination vertex
         * @return a shortest path from the source vertex {@code s} to vertex {@code v}
         *         as an iterable of edges, and {@code null} if no such path
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public Iterable<DirectedEdge> pathTo(int v) {
            validateVertex(v);
            if (!hasPathTo(v)){
                return null;
            }

            Stack<DirectedEdge> path = new Stack<DirectedEdge>();

            for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
                path.push(e);
            }
            return path;
        }


        // check optimality conditions:
        // (i) for all edges e:            distanceTo[e.to()] <= distanceTo[e.from()] + e.weight()
        // (ii) for all edge e on the SPT: distanceTo[e.to()] == distanceTo[e.from()] + e.weight()
        private boolean check(EdgeWeightedDigraph G, int s) {

            // check that edge weights are non-negative
            for (DirectedEdge e : G.edges()) {
                if (e.weight() < 0) {
                    System.err.println("Negative edge weight detected.");
                    return false;
                }
            }

            // check that distTo[v] and edgeTo[v] are consistent
            if (distanceTo[s] != 0.0 || edgeTo[s] != null) {
                System.err.println("Both distanceTo[s] and edgeTo[s] inconsistent.");
                return false;
            }

            for (int v = 0; v < G.V(); v++) {
                if (v == s) {
                    continue;
                }
                if (edgeTo[v] == null && distanceTo[v] != Double.POSITIVE_INFINITY) {
                    System.err.println("Both distTo[] and edgeTo[] inconsistent.");
                    return false;
                }
            }

            // check that all edges e = v->w satisfy distanceTo[w] <= distanceTo[v] + e.weight()
            for (int v = 0; v < G.V(); v++) {
                for (DirectedEdge e : G.adj(v)) {
                    int w = e.to();

                    if (distanceTo[v] + e.weight() < distanceTo[w]) {
                        System.err.println("edge " + e + " not relaxed.");
                        return false;
                    }
                }
            }

            // check that all edges e = v->w on SPT satisfy distanceTo[w] == distanceTo[v] + e.weight()
            for (int w = 0; w < G.V(); w++) {
                if (edgeTo[w] == null) continue;
                DirectedEdge e = edgeTo[w];
                int v = e.from();
                if (w != e.to()) return false;
                if (distanceTo[v] + e.weight() != distanceTo[w]) {
                    System.err.println("edge " + e + " on shortest path not tight.");
                    return false;
                }
            }
            return true;
        }

        // throw an IllegalArgumentException unless {@code 0 <= v < V}
        private void validateVertex(int v) {
            int V = distanceTo.length;

            if (v < 0 || v >= V)
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1) + ".");
        }

        /**
         * Unit tests the {@code DijkstraSP} data type.
         *
         * @param args the command-line arguments
         */
        public static void main(String[] args) {

            In in = new In(args[0]);
            EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
            int s = Integer.parseInt(args[1]);

            // compute shortest paths
            Dijkstra shortestPath = new Dijkstra(G, s);

            // print shortest path
            for (int t = 0; t < G.V(); t++) {
                if (sp.hasPathTo(t)) {
                    StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));

                    for (DirectedEdge e : sp.pathTo(t)) {
                        StdOut.print(e + "   ");
                    }
                    StdOut.println();
                }

                else {
                    StdOut.printf("%d to %d         no path\n", s, t);
                }
            }
        }

    }

        private void fileReader(){

            //BufferedReader br = new BufferedReader(new FileReader(filename));
            
        }

        private double getShortestDistance(){

        }

        private int timeRequired(){

        }
        
    }
    
    