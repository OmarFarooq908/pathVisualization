package intro_to_programming;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		String fileName = "/media/omar/Eagle/MOmarFarooq/Learning/FreeLancing/Task6/code and data/code and data/Assignment2_Data.csv";

		//Load the CSV File
		Network result = Network.loadNetwork(fileName);

		//Apply the Dijkstra's algorithm
		result.applyDijkstra("3");

		//Visualize the output graph
		result.visualize(fileName);

	}

}
