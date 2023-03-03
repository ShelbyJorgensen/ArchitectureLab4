/*
 * Author: Shelby Jorgensen
 * Class: CSCI 302
 */

package Lab4;

import java.io.*;
import java.util.Scanner;

public class Lab4 {
	public static void main(String[] args) throws IOException {
		// Import both the transmitted file and received files
		FileInputStream transFile = new FileInputStream("transmitfile.bin");
		FileInputStream recFile = new FileInputStream(new File("receivefile4.bin"));
		
		// Extract the contents of the bin file, save as a string for easy printing
		Scanner inpt = new Scanner(transFile);
		String transFileBinaryStr = inpt.nextLine();
		// Transfer the contents of the string into a byte array, converting from string to bytes
		int transFileSize = transFileBinaryStr.length();
		byte[] transFileBinary = new byte[transFileSize];
		int count = transFileSize - 1;
		for(int i = 0; i < transFileSize; i++) {
			transFileBinary[count] = Byte.parseByte(String.valueOf(transFileBinaryStr.charAt(i)));
			count--;
		}
		
		System.out.println("Transmitted file content: " + transFileBinaryStr);
		System.out.println("Total number of bytes read: " + transFileSize + " bytes\n");
		
		// Due to m being 8 bit words, find the largest mutliple of 8 that goes into the byte size
		int m = (transFileSize / 8) * 8;
		// Find K using the equation 2^K-1 >= M + K
		int k = 0;
		while((Math.pow(2, k) - 1) <= (m + k)) {
			k++;
		}
		
		System.out.println("M data bits is: " + m);
		System.out.println("K check bits is: " + k + "\n");
		
		// Byte array to hold the check bits found at the check locations
		byte[] checkBits = new byte[k];
		int[] checkLocations = new int[k];
		count = 0;
		
		// If a array location is a base 2 number, that bit is a check bit, save the location into array
		for(int i = 1; i < transFileBinary.length; i++) {
			if(checkInputBase2(i)) {
				checkLocations[count] = i - 1;
				count++;
			}
		}
		
		// Find the check bits based off the previously found locations
		for(int i = 0; i < checkBits.length; i++) {
			checkBits[i] = transFileBinary[checkLocations[i]];
		}
		
		System.out.print("Location of the k check bits are: ");
		for(int i = 0; i < checkBits.length; i++) {
			System.out.print(checkLocations[i] + " ");
		}
		System.out.print("\n" + "The k check bit values are: ");
		for(int i = checkBits.length - 1; i >= 0; i--) {
			System.out.print(checkBits[i] + " ");
		}
		
		// Extract the contents of the bin file, save as a string for easy printing
		inpt = new Scanner(recFile);
		String recFileBinaryStr = inpt.nextLine();
		// Transfer the contents of the string into a byte array, converting from string to bytes
		int recFileSize = recFileBinaryStr.length();
		byte[] recFileBinary = new byte[recFileSize];
		count = recFileSize - 1;
		for(int i = 0; i < recFileSize; i++) {
			recFileBinary[count] = Byte.parseByte(String.valueOf(recFileBinaryStr.charAt(i)));
			count--;
		}
		
		
		System.out.println("\n\n" + "Received file content: " + recFileBinaryStr);
		System.out.println("Total number of bytes read: " + recFileSize + " bytes" + "\n");
		// Ensure the received file and transmitted file are the same size, else exit the program
		checkFileSizes(recFileSize, transFileSize);
		
		// Byte array to hold the check bits found at the check locations
		byte[] recCheckBits = new byte[k];
		int[] recCheckLocations = new int[k];
		// If a array location is a base 2 number, that bit is a check bit, save the location into array
		count = 0;
		for(int i = 1; i < transFileBinary.length; i++) {
			if(checkInputBase2(i)) {
				recCheckLocations[count] = i - 1;
				count++;
			}
		}
		
		// Find the check bits based off the previously found locations
		for(int i = 0; i < recCheckBits.length; i++) {
			recCheckBits[i] = recFileBinary[recCheckLocations[i]];
		}
		
		System.out.print("Location of the k check bits are: ");
		for(int i = 0; i < recCheckBits.length; i++) {
			System.out.print(recCheckLocations[i] + " ");
		}
		System.out.print("\n" + "The k check bit values are: ");
		for(int i = recCheckBits.length - 1; i >= 0; i--) {
			System.out.print(recCheckBits[i] + " ");
		}
		
		// Byte array to hold the syndrome
		byte[] syndrome = new byte[checkBits.length];
		int syndromeLocation = 0;
		
		// XOR the bits in the transmittedCheckBits and receivedCheckBits, save the result as part of the syndrome
		for(int i = 0; i < checkBits.length; i++) {
			syndrome[i] = (byte) (checkBits[i] ^ recCheckBits[i]);
			// Syndrome location is just the syndrome byte array converted to base 10, so compute the location as bytes are saved
			if(syndrome[i] == 1) {
				syndromeLocation += syndrome[i] * Math.pow(2, i);
			}
		}
			
		System.out.print("\n\n" + "The syndrome word is: ");
		for(int i = syndrome.length - 1; i >= 0; i--) {
			System.out.print(syndrome[i] + " ");
		}
		
		// Based of the syndrome location and result, print out any errors detected
		if(syndromeLocation == 0) {
			System.out.println("\n" + "No error detected in the received file.");
		} else if(syndromeLocation == 1) {
			System.out.println("\n" + "Only one syndrome bit is set to 1. The error bit is one of the check bits. No correction needeed.");
		} else {
			System.out.println("\n" + "The location of the error bit in the received data is: " + syndromeLocation);
		}
	}
	
	/*
	 * Method to check the file size of two binary files
	 */
	public static void checkFileSizes(int file1, int file2) {
		if(file1 != file2) {
			System.out.println("Files are not the same size!");
			System.exit(0);
		}
	}
	
	/*
	 * Method that checks if the provided input is a power of 2 number
	 */
	public static boolean checkInputBase2(int checkNum) {
		while(checkNum % 2 == 0) {
			checkNum = checkNum / 2;
		}
		if(checkNum == 1) {
			return true;
		} else {
			return false;
		}
	}
}
