package de.spillmann.address;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.spillmann.address.exception.InvalidAddressException;


/**
 * The class <code>FileAddressBookReporter</code> is a file based implementation of {@link AddressBookReporter}. 
 * <p>
 * @author 	Martin Spillmann
 * @version	1.0
 * @since	1.0
 */
public class FileAddressBookReporter implements AddressBookReporter {

	private static Logger logger = Logger.getLogger(FileAddressBookReporter.class.toString());


	/** The index of the name in the address line. */ 
	private final int ADDRESS_NAME = 0;
	/**  The index of the sex in the address line. */ 
	private final int ADDRESS_SEX = 1;

	/** The index of the birthday in the address line. */ 
	private final int ADDRESS_BIRTHDATE = 2;

	/** The index of the day in the birthday literal. */ 
	private final int DAY = 0;

	/** The index of the month in the birthday literal. */ 
	private final int MONTH = 1;

	/** The index of the year in the birthday literal. */ 
	private final int YEAR = 2;

	/** The line number of the address being used in the file. */
	int addressbookLine = 0;
	
	
	@Override
	public List<AddressBookEntry> getAllAddressEntries() throws InvalidAddressException, IOException {

		List<AddressBookEntry> addresses = null;
		String addressLine = null;
		BufferedReader reader = null;
		
		try {
			File file = new File("data" + File.separator + "addressBook.dat");
			reader = new BufferedReader(new FileReader(file));
			
			addresses = new ArrayList<>();
			while ((addressLine = reader.readLine()) != null) {
			
				addressbookLine++;
				
				String[] addressValues = addressLine.split(",");
				AddressBookEntry addressBookEntry = new AddressBookEntry();
				addressBookEntry.setName(getName(addressValues[ADDRESS_NAME]));
				addressBookEntry.setSex(getSex(addressValues[ADDRESS_SEX]));
				addressBookEntry.setBirthdate(getAddressDate(addressValues[ADDRESS_BIRTHDATE]));
				addresses.add(addressBookEntry);
			}
		} catch (InvalidAddressException e) {
			logger.log(Level.ERROR, e.getMessage());
			throw e;
		} catch (IOException e) {
			logger.log(Level.ERROR, e.getMessage());
			throw e;
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.log(Level.ERROR, e.toString());
					throw e;
				}
			}
		}
		
		return addresses;
	}

	
	@Override
	public long countFemalePerson(List<AddressBookEntry> femalePersonList) {

		Stream<AddressBookEntry> femalePerson = femalePersonList.stream().filter((address) -> address.getSex().equals("Female"));
		return femalePerson.count();	
	}
	
	
	@Override
	public AddressBookEntry calculateOldestPerson(List<AddressBookEntry> allPersonList) {
		Optional<AddressBookEntry> oldestPerson = allPersonList.stream().max((o1, o2) -> {

			if(o1.getBirthdate().isBefore(o2.getBirthdate())) {
				return 1;
			}
			return -1;}
		);
		
		return oldestPerson.get();	
	}

	
	@Override
	public long calculateDayDifference(List<AddressBookEntry> list,	String nameOlderPerson, String nameYoungerPerson) {

		Stream<AddressBookEntry> olderPerson = list.stream().filter((address) -> address.getName().matches(nameOlderPerson + ".*"));
		AddressBookEntry olderPersonAddress = olderPerson.findFirst().get();
		logger.log(Level.INFO, "Name of the older Person: " + olderPersonAddress.getName());
		
		Stream<AddressBookEntry> youngerPerson = list.stream().filter((address) -> address.getName().matches(nameYoungerPerson + ".*"));
		AddressBookEntry youngerPersonAddress = youngerPerson.findFirst().get();
		logger.log(Level.INFO, "Name of the younger Person: " + youngerPersonAddress.getName());

		long daysBetween = ChronoUnit.DAYS.between(olderPersonAddress.getBirthdate(), youngerPersonAddress.getBirthdate());
		logger.log(Level.INFO, "The older Person was born " + daysBetween + " erlier than the younger");

		return daysBetween;
	}

	
	/**
	 * Validated and formatted the name of an address.
	 * <p>
	 * @param name
	 * @return the tested and formatted name
	 * @throws InvalidAddressException if a name is not valid
	 */
	protected String getName(String name) throws InvalidAddressException {
	
		name = name.trim();
		
		if (name.length() == 0) {
			throw new InvalidAddressException("Address in Line " + this.addressbookLine + " contains an invalid name");
		}
		return name;
	}

	
	/**
	 * Validated and formatted the sex of an address.
	 * <p>
	 * @param sex
	 * @return the tested and formatted sex
	 * @throws InvalidAddressException if the sex is not valid
	 */
	protected String getSex(String sex) throws InvalidAddressException {
		
		sex = sex.trim();
		
		if (sex.length() == 0 || !(sex.equalsIgnoreCase("female") || sex.equalsIgnoreCase("male"))) {
			throw new InvalidAddressException("Address in Line " + this.addressbookLine + " contains an invalid sex");
		}
		return sex;
	}

	
	/**
	 * Generates a {@link LocalDate} from a text literal. The text literal is 
	 * first checked for a valid date format.
 	 * <p>
	 * @param date
	 * @return a address date
	 * @throws InvalidAddressException
	 */
	protected LocalDate getAddressDate(String date) throws InvalidAddressException {
		
		LocalDate birthdate = null;
		date = date.trim();

		String[] birthdateValues = date.split("/");

		if((birthdateValues[YEAR].length() != 2 && birthdateValues[YEAR].length() != 4) || 
		   birthdateValues[MONTH].length() != 2 || birthdateValues[DAY].length() != 2) {
			throw new InvalidAddressException("Address  in Line " + this.addressbookLine + " contains an invalid date.");
		}

		if(birthdateValues[YEAR].length() == 2) {
			date = "19" + birthdateValues[2];
		}
		
		try {
			birthdate = LocalDate.of(Integer.parseInt(birthdateValues[YEAR].trim()), Integer.parseInt(birthdateValues[MONTH].trim()), Integer.parseInt(birthdateValues[DAY].trim()));
		} catch (NumberFormatException e) {
			logger.log(Level.ERROR, "Address  in Line " + this.addressbookLine + " contains an invalid date.");
			throw new InvalidAddressException(e);
		}
		
		return birthdate;
	}
}
