package fabflix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class analyze {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		float ts = 0;
		float tj = 0;
	
		
		Scanner s;
		try {		
			File file = new File("log.txt");

			s = new Scanner(file);
			long counter = 0;
			while(s.hasNextLine()) {
				counter++;
				String line = s.nextLine();
				String times[] = line.split(";");
				ts += Long.parseLong(times[0]);
				tj += Long.parseLong(times[1]);

			}
			long div = 1000000;
			ts = ts/div;


			tj = tj/div;


			System.out.printf("TS = %2f ms\n", ts/counter);
			System.out.printf("TJ = %2f ms\n", tj/counter);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}}
