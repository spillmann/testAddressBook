package de.spillmann.address;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/**
 * The <code>AddressBookAnalyser</code> is the main class for the investigation
 * of the solutions of the tasks. For this purpose, the implementation 
 * {@link FileAddressBookReporter} is accessed via {@link AddressBookReporter}.  
 * <p>
 * For the first version a configuration option for the location and names 
 * of the address book is not implemented. The name of the 
 * <i>address book file</i> is <i>addressBook.dat</i> and is located in the 
 * main directory in the subdirectory <i>data</i> of the program.    
 * <p>
 * @author	Martin Spillmann
 * @version	1.0
 * @since	1.0
 */
public class AddressBookAnalyser {

	private static Logger logger = Logger.getLogger(AddressBookAnalyser.class.toString());
	
	/**
	 * ich bin ein test 123_4_567_78_99
	 */
	
	/**
	 * Creates an instance of the class <code>AddressBookAnalyser</code> and 
	 * begins to discover the solutions to your tasks. 
	 * <p>
	 * @param Access data to the address book. In the implementation without function.
	 * @throws IOException if an error occurred with the file handling
	 */
	public static void main(String[] args) throws IOException {
	
		AddressBookAnalyser analyser = new AddressBookAnalyser();
		analyser.analyse();	
	}

	
	/**
	 * Determines the solutions to your tasks and outputs them in the Console.
	 * <p>
	 * @throws IOException if an error occurred with the file handling
	 */
	private void analyse() throws IOException {
		
		try {
			List<AddressBookEntry> addressBookEntries = new ArrayList<>();
			AddressBookReporter reporter = new FileAddressBookReporter();
			addressBookEntries = reporter.getAllAddressEntries();
	
			/** 1. How many Women are in the address book? */
			long femalePerson = reporter.countFemalePerson(addressBookEntries);
			System.out.printf("1. The addressbook contains %s female persons.\n", femalePerson);
			
			/** 2. Who is the oldest Person in the address book? */
			AddressBookEntry oldestPerson = reporter.calculateOldestPerson(addressBookEntries);
			System.out.printf("2. The oldest person is %s\n", oldestPerson.getName());

			
			/** How many days older is Bill than Paul? */
			long daysBetween = reporter.calculateDayDifference(addressBookEntries, "Bill", "Paul");
			System.out.printf("3. Bill is %d days older than Paul.\n", daysBetween);

		} catch (Exception e) {
			logger.log(Level.ERROR, "The application was terminated based on a technical error.");
		} 
	}
}
