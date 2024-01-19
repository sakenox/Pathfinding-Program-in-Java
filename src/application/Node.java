package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {
    String street;
    List<Connection> connections;
    double distanceFromStart; // Added this line
    double x,y;
    private static Map<String, Node> streetNameMap = new HashMap<>();

    public Node(String street, double x, double y) {
        this.street = street;
        this.connections = new ArrayList<>();
        this.distanceFromStart = Double.POSITIVE_INFINITY;
        this.x = x;
        this.y = y;
    }
    
    // Method to get a node by street name
    public static Node getNodeByStreetName(String streetName) {
        return streetNameMap.get(streetName);
    }
    
    public String getStreet() {
    	return street;
    }

    public void addConnection(Node destination, double distance) {
        connections.add(new Connection(destination, distance));
    }

    // Added this method to calculate heuristic (estimate to the goal)
    public double heuristic(Node goal) {
        // Assuming each node has coordinates (x, y) representing its position on a map
        // Replace getX() and getY() with the actual methods to get the coordinates from your Node class
    	double dx = Math.abs(getX() - goal.getX());
    	double dy = Math.abs(getY() - goal.getY());

        // Using Euclidean distance formula
        return (double) Math.sqrt(dx * dx + dy * dy);
    }
    
    public double getX() {
    	return x;
    }
    
    public double getY() {
    	return y;
    }
    
    public static Map<String, Node> loadNodesFromCSV(String nodeFilename, String connectionFilename, Map<String, Node> streetNameMap) {
        Map<String, Node> nodes = new HashMap<>();

        // Load nodes from the first CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(nodeFilename))) {
            String header = br.readLine(); // Read and skip the header line
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                int streetID = Integer.parseInt(parts[0].trim());
                String streetName = parts[1].trim();
                double xCoord = Double.parseDouble(parts[2].trim());
                double yCoord = Double.parseDouble(parts[3].trim());

                Node node = nodes.computeIfAbsent(String.valueOf(streetID), key -> new Node(streetName, xCoord, yCoord));

                // Add to the street name map
                streetNameMap.put(streetName, node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load connections from the second CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(connectionFilename))) {
            String header = br.readLine(); // Read and skip the header line
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                int startID = Integer.parseInt(parts[0].trim());
                int endID = Integer.parseInt(parts[1].trim());
                double distance = Double.parseDouble(parts[2].trim());

                Node start = nodes.get(String.valueOf(startID));
                Node end = nodes.get(String.valueOf(endID));

                if (start != null && end != null) {
                    start.addConnection(end, distance);
                    end.addConnection(start, distance); // Assuming undirected graph
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nodes;
    }

}