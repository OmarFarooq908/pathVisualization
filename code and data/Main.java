package intro_to_programming;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		String fileName = "C:\\Users\\ASUS\\Downloads\\Assignment2_Data.csv";
		Network result = Network.loadNetwork(fileName);

	}

}
