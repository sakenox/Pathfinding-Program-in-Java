package application;

import java.util.*;

public class Graph {
	
	static Map<String, Node> streetNameMap = new HashMap<>();
	
    public static List<String> aStar(Node start, Node goal) {
        Set<Node> visited = new HashSet<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(node ->
        (double) (node.distanceFromStart + node.heuristic(goal))));
        Map<Node, Node> parentMap = new HashMap<>();

        start.distanceFromStart = 0;
        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current == goal) {
                return reconstructPath(parentMap, goal);
            }

            visited.add(current);

            for (Connection connection : current.connections) {
                Node neighbor = connection.destination;
                if (!visited.contains(neighbor)) {
                    double tentativeDistance = current.distanceFromStart + connection.distance;

                    if (tentativeDistance < neighbor.distanceFromStart || !queue.contains(neighbor)) {
                        neighbor.distanceFromStart = tentativeDistance;
                        parentMap.put(neighbor, current);
                        queue.add(neighbor);
                    }
                }
            }
        }

        return null; // No path found
    }

    private static List<String> reconstructPath(Map<Node, Node> parentMap, Node goal) {
        List<String> path = new ArrayList<>();
        Node current = goal;

        while (current != null) {
            path.add(current.street);
            current = parentMap.get(current);
        }

        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        String filename = "cerrik.csv";
        String filenam = "cerrik_list.csv";
        
        Map<String, Node> nodes = Node.loadNodesFromCSV(filename, filenam, streetNameMap);

        // Example usage
        Node startNode = nodes.get("1");
        Node endNode = nodes.get("10");

        if (startNode != null && endNode != null) {
            List<String> path = aStar(startNode, endNode);

            if (path != null) {
                System.out.println("Shortest Path: " + String.join(" -> ", path));
            } else {
                System.out.println("No path found.");
            }
        } else {
            System.out.println("Start or end node not found.");
        }
    }
}

