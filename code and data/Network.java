package intro_to_programming;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

public class Network {
	
	int numOfNodes, numOfEdges;
	Hashtable< String , Node > nodes;
	
	public Network() {
		numOfNodes = 0;
		numOfEdges = 0;
		nodes = new Hashtable<String , Node>();
		
	}
	
	public void addEdge( String fromNode, String toNode, Integer weight ) {
		Node fNode = getAddNode(fromNode);
		Node tNode = getAddNode( toNode );
		fNode.addEdge( tNode , weight);
		numOfEdges ++;
	}

	private Node getAddNode(String fromNode) {
		Node result = nodes.get( fromNode );
		if( result == null ) {
			result = new Node(fromNode);
			nodes.put( fromNode , result);
			numOfNodes ++;
		}
		return result;
	}
	
	/**
	 * You need to implement this function
	 * @param fileName
	 */
	public void visualize( String fileName ) {
		// add your code here
	}
	
	/**
	 * you need to implement this function
	 * @param source
	 */
	public void applyDijkstra( String source ) {
		// add your code here
	}
	
	public static Network loadNetwork( String fileName ) throws FileNotFoundException {
		Network result = new Network();
        //parsing a CSV file into the constructor of Scanner class
        Scanner scanner = new Scanner(new File( fileName ));
        //setting space and new lines as delimiter pattern
        scanner.useDelimiter("\\s* \\s*|\n");
        int i = 0;
        while (scanner.hasNext()) {            
        	i++;
        	String fromNode = scanner.next();
        	String toNode = scanner.next();
        	Integer weight = scanner.nextInt();
        	result.addEdge(fromNode, toNode, weight);
        }
        //closes the scanner
        scanner.close();
        System.out.println( "Loaded a network of " + result.numOfNodes 
        		+ " nodes and " + result.numOfEdges + " edges"  );

		return result;
	}
	

}
