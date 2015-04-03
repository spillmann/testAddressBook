package de.spillmann.address;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.spillmann.address.AddressBookEntry;
import de.spillmann.address.FileAddressBookReporter;
import de.spillmann.address.exception.InvalidAddressException;

/**
 * The style of Javadoc how I handle please refer to the classes 
 * {@link AddressBookAnalyser}, {@link AddressBookReporter} and 
 * {@link FileAddressBookReporter}.
 * <p>
 * @author Martin Spillmann
 * @version 1.0
 * @version 1.0
 */
public class FileAddressTest {

	private	AddressBookEntry entry1;
	private	AddressBookEntry entry2;
	private	AddressBookEntry entry3;
	private	AddressBookEntry entry4;
	private	AddressBookEntry entry5;
	private List<AddressBookEntry> entries;

	private FileAddressBookReporter reporter;
	
	@Before
	public void setup() {
		
		reporter = new FileAddressBookReporter();

		entry1 = new AddressBookEntry("Bill McKnight", "Male", LocalDate.of(1977, 3, 16));
		entry2 = new AddressBookEntry("Paul Robinson", "Male", LocalDate.of(1985, 1, 15));
		entry3= new AddressBookEntry("Gemma Lane", "Female", LocalDate.of(1991, 11, 20));
		entry4 = new AddressBookEntry("Sarah Stone", "Female", LocalDate.of(1980, 9, 20));
		entry5 = new AddressBookEntry("Wes Jackson", "Male", LocalDate.of(1974, 8, 14));
	}


	/**
	 * Tests to determine the number of all female addresses.
	 */
	@Test
	public void addressFemaleCountTest() {

		entries = new ArrayList<>();
		entries.add(entry1);
		entries.add(entry2);
		entries.add(entry3);
		entries.add(entry4);
		entries.add(entry5);

		assertEquals(2, reporter.countFemalePerson(entries));
	}

	/**
	 * Tests to determine the days between two dates. The first date is in 
	 * February , the second in March.
	 */
	@Test
	public void addressDaysBetween() {

		entries = new ArrayList<>();
		entry1.setBirthdate(LocalDate.of(1995, 2, 28));
		entry2.setBirthdate(LocalDate.of(1995, 3, 1));
		entries.add(entry1);
		entries.add(entry2);

		assertEquals(1, reporter.calculateDayDifference(entries, "Bill McKnight", "Paul Robinson"));
	}
	

	/**
	 * Tests to determine the days between two dates ​​in a leap year. The 
	 * first date is in February , the second in March. 
	 */
	@Test
	public void addressLeapYearDaysBetween() {

		entries = new ArrayList<>();
		entry1.setBirthdate(LocalDate.of(1996, 2, 28));
		entry2.setBirthdate(LocalDate.of(1996, 3, 1));
		entries.add(entry1);
		entries.add(entry2);

		assertEquals(2, reporter.calculateDayDifference(entries, "Bill McKnight", "Paul Robinson"));
	}

	
	/**
	 * Tests to determine the oldest person in an address book.
	 */
	@Test
	public void addressOldestPersonTest() {

		entries = new ArrayList<>();
		entries.add(entry1);
		entries.add(entry2);
		entries.add(entry3);
		entries.add(entry4);
		entries.add(entry5);

		assertEquals("Wes Jackson", reporter.calculateOldestPerson(entries).getName());
	}
	
	
	/** 
	 * Tests the response to an invalid year. 
	 */
	@Test(expected = InvalidAddressException.class)
	public void addressDateInvalidYearTest() throws InvalidAddressException {
		
		reporter.getAddressDate("16/03/7");
	}

	
	/** 
	 * Tests the response to an invalid month. 
	 */
	@Test(expected = InvalidAddressException.class)
	public void addressDateInvalidMonthTest() throws InvalidAddressException {
		
		reporter.getAddressDate("16/3/87");
	}

	
	/** 
	 * Tests the response to an invalid day. 
	 */
	@Test(expected = InvalidAddressException.class)
	public void addressDateInvalidDayTest() throws InvalidAddressException {
		
		reporter.getAddressDate("6/03/87");
	}

	
	/** 
	 * Tests the response to invalid characters for the date. 
	 */
	@Test(expected = InvalidAddressException.class)
	public void addressDateInvalidNumberTest() throws InvalidAddressException {
		
		reporter.getAddressDate("xx/03/87");
	}
	
	
	/** 
	 * Tests the response to an empty name. 
	 */
	@Test(expected = InvalidAddressException.class)
	public void addressNameInvalidValueTest() throws InvalidAddressException {
		
		reporter.getName("");
	}
	
	
	/** 
	 * Tests the response to an empty sex. 
	 */
	@Test(expected = InvalidAddressException.class)
	public void addressSexInvalidTest() throws InvalidAddressException {
		
		reporter.getSex("");
	}

	
	/** 
	 * Tests the response to invalid characters for the sex. 
	 */
	@Test(expected = InvalidAddressException.class)
	public void addressSexInvalidTypeTest() throws InvalidAddressException {
		
		reporter.getSex("xxx");
	}
	
	
	/** 
	 * Tests the response to valid types for the sex. 
	 */
	@Test
	public void addressSexValidTest() throws InvalidAddressException {
		
		assertEquals("Female", reporter.getSex(" Female "));
		assertEquals("Male", reporter.getSex(" Male "));
	}
}
