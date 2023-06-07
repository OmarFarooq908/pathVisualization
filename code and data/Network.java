package intro_to_programming;


import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Color;
import org.apache.commons.collections15.Transformer;
import java.awt.Paint;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

import java.io.*;
import java.lang.*;
import java.util.*;

public class Network {
	// A private method is defined below to get the Shortest Path
	private Set<Integer> getShortestPathEdges(Graph<String, Integer> graph, Hashtable<String, Node> nodes) {
	    //We create new HashSet to store the shortest path edges
	    Set<Integer> shortestPathEdges = new HashSet<>();

	    //We loop through all the nodes in the nodes Hashtable
	    for (Node node : nodes.values()) {
		//Checks if the current node has a parent, which indicates that it is a part of the shortest path.
		if (node.parent != null) {
		    //We get the Name of the parent node
		    String fromNode = node.parent.name;
		    //We get the Name of the current node
		    String toNode = node.name;
		    //We find the edge between the parent node and the current node in the graph
		    Integer edge = graph.findEdge(fromNode, toNode);
		    //We check the condition for the existence of an edge between two nodes
		    if (edge != null) {
			//We add the edge to the shortestPathEdges set
			shortestPathEdges.add(edge);
		    }
		}
	    }
	    //We return the set of shortest path edges
	    return shortestPathEdges;
	}
	
	//Declaration of variables for number of nodes and edges
	int numOfNodes, numOfEdges;

	//We used hashtables data structure to map the nodes label to the Node object
	Hashtable< String , Node > nodes;
	
	//We used a constructor to initialize the Network class
	public Network() {
		numOfNodes = 0;
		numOfEdges = 0;
		nodes = new Hashtable<String , Node>();
		
	}
	
	//The method below is used to add an Edge to the Network
	public void addEdge( String fromNode, String toNode, Integer weight ) {
		//We use getAddNode method to get the nodes
		Node fNode = getAddNode(fromNode);
		Node tNode = getAddNode( toNode );

		//We use the addEdge method to add the edge to the Network.
		//We provided the "to Node" as the perimeter as this is directed graph.
		fNode.addEdge( tNode , weight);

		//We increase the number of edges after we have added the edge to the network
		//Hence the variable below represents the number of edges in the network after addition
		numOfEdges ++;
	}
	
	//The method below takes the String fromNode as input and returns a Node object
	//If the Node object does not exists, it creates and adds it to the hashtable
	private Node getAddNode(String fromNode) {
		//We get the Node object associated with the fromNode String key from the nodes hashtables
		Node result = nodes.get( fromNode );
		//If the Node object is not found in the hashtable, create a new Node object with fromNode as its Name
		if( result == null ) {

			result = new Node(fromNode);

			//Add the new Node object to the hashtable with fromNode as its key
			nodes.put( fromNode , result);

			//Increase the numOfNodes counter
			numOfNodes ++;
		}
		//Return the Node object (either found in the hashtable or newly created)
		return result;
	}

	/**
	 * You need to implement this function
	 * @param fileName
	 */
	public void visualize(String fileName) {
	    // Step 1: Import necessary JUNG libraries
	    
	    // Step 2: Create a JUNG graph object
	    Graph<String, Integer> graph = new DirectedSparseMultigraph<String, Integer>();

	    // Step 3: Add each node as a vertex in the JUNG graph
	    for (String node : nodes.keySet()) {
		graph.addVertex(node);
	    }

	    // Step 4: Add each edge as a directed or undirected edge in the JUNG graph
	    int edgeId = 0;
	    for (String node : nodes.keySet()) {
		Node n = nodes.get(node);
		for (Node neighbor : n.getNeighbors()) {
		    int weight = n.getWeight(neighbor);
		    graph.addEdge(edgeId++, node, neighbor.name, EdgeType.DIRECTED);
		}
	    }

	    // Step 5: Create a layout for the graph
	    Layout<String, Integer> layout = new FRLayout<>(graph);

	    // Step 6: Create a visualization viewer and pass in the JUNG graph and layout
	    VisualizationViewer<String, Integer> vv = new VisualizationViewer<String, Integer>(layout);
	    vv.setPreferredSize(new Dimension(600, 600));
	    vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
	    vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

	    // Step 7: Highlight the shortest path
	    for (Node node : nodes.values()) {
		if (node.parent != null) {
		    String fromNode = node.parent.name;
		    String toNode = node.name;
		    Integer edge = graph.findEdge(fromNode, toNode);
		    if (edge != null) {
			graph.addEdge(edge, fromNode, toNode, EdgeType.DIRECTED);
		    }
		}
	    }
	    Set<Integer> shortestPathEdges = getShortestPathEdges(graph, nodes);
	    Transformer<Integer, Paint> edgePaint = new Transformer<Integer, Paint>() {
		public Paint transform(Integer edge) {
		    if (shortestPathEdges.contains(edge)) {
			return Color.BLUE; // Red color for shortest path edges
		    } else {
			return Color.RED; // Default color for other edges
		    }
		}
	    };
	    vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);

	    // Step 8: Display the visualization viewer
	    JFrame frame = new JFrame("Network Visualization");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().add(vv);
	    frame.pack();
	    frame.setVisible(true);
	}
	/**
	 * you need to implement this function
	 * @param source
	 */
	/*
	public void applyDijkstra( String source ) {
		// add your code here
	}
	*/
	public void applyDijkstra(String source) {
	    // Set all nodes to have infinite cost and no parent
	    for (Node node : nodes.values()) {
		node.shortestPathCost = Integer.MAX_VALUE;
		node.parent = null;
	    }

	    // Set the cost of the source node to be 0
	    Node sourceNode = nodes.get(source);
	    sourceNode.shortestPathCost = 0;

	    // Create a priority queue to store the nodes
	    PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.shortestPathCost));
	    queue.add(sourceNode);

	    while (!queue.isEmpty()) {
		// Get the node with the smallest cost
		Node current = queue.poll();

		// Iterate through the current node's neighbors
		for (Node neighbor : current.getNeighbors()) {
		    int newCost = current.shortestPathCost + current.getWeight(neighbor);

		    // If the new cost is less than the neighbor's current cost, update it
		    if (newCost < neighbor.shortestPathCost) {
			neighbor.shortestPathCost = newCost;
			neighbor.parent = current;
			queue.add(neighbor);
		    }
		}
	    }
	}

	public static Network loadNetwork( String fileName ) throws FileNotFoundException {
		Network result = new Network();
        //parsing a CSV file into the constructor of Scanner class
        Scanner scanner = new Scanner(new File( fileName ));
        //setting space and new lines as delimiter pattern
        scanner.useDelimiter("\\s|\\n");
        int i = 0;
        while (scanner.hasNext()) {            
        	i++;
        	String fromNode = scanner.next();
        	String toNode = scanner.next();
        	String str_weight = scanner.next();
		int weight = Integer.parseInt(str_weight.trim());
        	result.addEdge(fromNode, toNode, weight);
        }
        //closes the scanner
        scanner.close();
        System.out.println( "Loaded a network of " + result.numOfNodes 
        		+ " nodes and " + result.numOfEdges + " edges"  );

		return result;
	}
	

}
