/* Class representing an edge */

public class Edge {
	
	final Node start;		// Node at one end 
	final Node end;			// Node at other end
	final int weight;		// weight of this edge
	
	public Edge()
	{
		start = end = null;
		weight = -1;
	}
	
	public Edge(Node x, Node y, int weight)
	{
		start = x;
		end = y;
		this.weight = weight;
	}
}
