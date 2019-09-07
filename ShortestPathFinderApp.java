import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.ListIterator;

public class ShortestPathFinderApp {
    public static void main(String[] arg) {
        String filename = arg[0];
        AdjacencyList al = new AdjacencyList(filename);
        Node vertex = new Node();
        Edge line = new Edge();
        Map <Node, List<Edge>> adjacent = al.getAdjacencyList();
        for (Node var : adjacent.keySet()) {
            if(var.getCost() == 0)
                var.setCost(-1);
        }
        //al.printAdjacency();
        dijkstraShortestPath dij = new dijkstraShortestPath(adjacent);
        for (Node var : adjacent.keySet()) {
            if(var.getAddr().equals(arg[1])) {
                dij.dijkstra(adjacent, var);
            }
        }
        System.out.println("Node\tCost\tNext-Hop");
        for (Node var : adjacent.keySet()) {
            String hop = null;
            if(var.getParent() != null)
                if(var.getParent().getAddr().equals(arg[1]))
                    hop = var.getAddr();
                else
                    hop = var.getParent().getAddr();
            System.out.println(var.getAddr()+"\t"+var.getCost()+"\t"+hop);
        }
    }
}

class dijkstraShortestPath {
    Map<Node, Integer> distance = new HashMap<Node, Integer>();
    Map<Node, Node> parents = new HashMap<Node, Node>();
    Set<Node> visitedNodes = new HashSet<Node>();
    Set<Node> unvisitedNodes = new HashSet<Node>();
    Map<Node, List<Edge>> point;

    dijkstraShortestPath(Map <Node, List<Edge>> adjacent) {
        this.point = adjacent;
    }
    public void dijkstra(Map <Node, List<Edge>> adjacent, Node nodeAddr) {
        Node root = nodeAddr;
        root.setCost(0);
        distance.put(root, 0);
        root.setVisitStatus(false);
        unvisitedNodes.add(root);
        while(unvisitedNodes.size() > 0) {
            Node start = leastBest(unvisitedNodes);
            visitedNodes.add(start);
            start.setVisitStatus(true);
            unvisitedNodes.remove(start);
            shortDistance(start);
        }
    }

    private Node leastBest(Set<Node> begin) {
        Node least = null;
        for (Node var : begin) {
            if(least == null)
                least = var;
            else {
                if(leastDistance(var) < leastDistance(least))
                    least = var;
            }
        }
        return least;
    }

    private int leastDistance(Node end) {
        Integer dist = end.getCost();
        if(dist == -1)
            return Integer.MAX_VALUE;
        else
            return dist;
    }

    private void shortDistance(Node start) {
        List<Node> neighbour = new ArrayList<Node>();
        for (Node var : point.keySet()) {
            if(var.getAddr() == start.getAddr()) {
                List<Edge> list = point.get(var);
                for (ListIterator<Edge> it = list.listIterator(); it.hasNext();) {
                    Edge edge = it.next();
                    if(!visitedNodes.contains(edge.end))
                        neighbour.add(edge.end);
                }
            }
        }
        for (Node var : neighbour) {
            if(leastDistance(var) > leastDistance(start) + actualDistance(start, var)) {
                distance.put(var, leastDistance(start) + actualDistance(start, var));
                var.setCost(leastDistance(start) + actualDistance(start, var));
                parents.put(var, start);
                var.setParent(start);
                unvisitedNodes.add(var);
                var.setVisitStatus(false);
            }
        }
    }

    private int actualDistance(Node start, Node end) {
        for (Node var : point.keySet()) {
            if(var == start) {
                List<Edge> list = point.get(var);
                for (ListIterator<Edge> it = list.listIterator(); it.hasNext();) {
                    Edge edge = it.next();
                    if(end == edge.end)
                        return edge.weight;
                }
            }
        }
        throw new RuntimeException("Error not happening");
    }
}
