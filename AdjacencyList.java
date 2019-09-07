//Class representing an adjacency list
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;


public class AdjacencyList {
	
	private Map <Node, List<Edge>> adjacency= new HashMap<Node, List<Edge>>();
	
	/* Input file is of the format
	 * 
	 * x-y:w where x-y represents the edge, and w is the edge weight
	 * 
	 */
	public AdjacencyList(String file)
	{
		try
		{
			Scanner fileScanner = new Scanner(new File(file)) ;
			Map <String, Node> nodeMap = new HashMap<String, Node>();
			
			while(fileScanner.hasNext())
			{
				String str = fileScanner.next("[a-zA-Z]+-[a-zA-Z]+:[0-9]+");
				
				//Get the source node
				String[] tmp = str.split(":");
				//System.out.println(tmp[0]);
				String[] node = tmp[0].split("-");
				//System.out.println("node1:" + node[0] + " node2:" + node[1] + " weight:" + tmp[1]);
				
				String addr1 = node[0];
				String addr2 = node[1];
				int weight = Integer.parseInt(tmp[1]);
				
				if (!nodeMap.containsKey(addr1))
					nodeMap.put(addr1, new Node(addr1));
				
				if (!nodeMap.containsKey(addr2))
					nodeMap.put(addr2, new Node(addr2));
				
				addEdge(nodeMap.get(addr1), nodeMap.get(addr2), weight);
				
			}
			
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("File " + file + " Not found");
			System.exit(-1);
		}
	}
	
	public void addEdge(Node source, Node target, int weight)
	{
		List<Edge> list;
	    if(!adjacency.containsKey(source)) 
	    {
	    	System.out.println("Creating List for " + source.getAddr());
	    	
	        list = new ArrayList<Edge>();
	        adjacency.put(source, list);
	    }
	    else 
	    {
	    	list = adjacency.get(source);
	    }
	    list.add(new Edge(source, target, weight));
	 }
	
	/* Method to print the adjacency list */
	public void printAdjacency()
	{
		for (Node node : adjacency.keySet())
		{
			List<Edge> list = adjacency.get(node);
			for (ListIterator<Edge> it = list.listIterator(); it.hasNext();)
			{
				Edge edge = it.next();
				System.out.print(edge.start.getAddr() + "-" + edge.end.getAddr() + "(" + edge.weight + ") ");
			}
			System.out.println();
		}
	}
	
	/* Accesor */
	public Map<Node, List<Edge>> getAdjacencyList()
	{
		return adjacency;
	}
}
