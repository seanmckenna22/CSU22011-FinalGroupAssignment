public class Edge {

    public double weight;
    public int fromV;
    public int endV;

    public Edge(int fromV, int endV, double weight)
    {
        this.fromV = fromV;
        this.endV = endV;
        this.weight = weight;
    }
}
