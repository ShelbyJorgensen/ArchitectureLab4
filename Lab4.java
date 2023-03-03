package Lab4;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class Lab4 {
	public static void main(String[] args) throws IOException {
		FileInputStream transFile = new FileInputStream("transmitfile.bin");
		FileInputStream recFile1 = new FileInputStream(new File("receivefile1.bin"));
		FileInputStream recFile2 = new FileInputStream(new File("receivefile2.bin"));
		FileInputStream recFile3 = new FileInputStream(new File("receivefile3.bin"));
		FileInputStream recFile4 = new FileInputStream(new File("receivefile4.bin"));
		
		Scanner inpt = new Scanner(transFile);
		String transFileBinary = inpt.nextLine();
		int transFileSize = transFileBinary.length();
		System.out.println("Transmitted file content: " + transFileBinary);
		System.out.println("Total number of bytes read: " + transFileSize + " bytes\n");
		
		int m = (transFileSize / 8) * 8;
		int k = 0;
		while((Math.pow(2, k) - 1) <= (m + k)) {
			k++;
		}
		
		System.out.println("M data bits is: " + m);
		System.out.println("K check bits is: " + k + "\n");
		
		System.out.println("Location of the k check bits are: ");
		System.out.println("The k check bit values are: " + "\n");
		
		System.out.println("Received file content: ");
		System.out.println("Total number of bytes read: " + " bytes" + "\n");
		
		System.out.println("Location of the k check bits are: ");
		System.out.println("The k check bit values are: " + "\n");
		
		System.out.println("The syndrome word is: ");
		System.out.println("The location of the error bit in the received data is: ");
		
	}
	
	/*
	 * Method to turn any provided into into the base 2 representation
	 */
	public static int convertToPowerTwo(int convNum) {
		int count = 1;
		while(convNum != 2) {
			convNum = convNum / 2;
			count++;
		}
		return count;
	}
	
	/*
	 * Method to add leading zeros onto binary strings, ensuring string size is equal to main memory in base 2
	 */
	public static String checkBinaryString(int checkNum, String binStr) {
		StringBuilder strBin = new StringBuilder(binStr);
		while(strBin.length() < checkNum) {
			strBin.insert(0, "0");
		}
		
		return strBin.toString();
	}
}
