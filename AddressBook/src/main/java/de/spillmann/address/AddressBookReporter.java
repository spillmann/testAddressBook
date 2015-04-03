package de.spillmann.address;

import java.io.IOException;
import java.util.List;

import de.spillmann.address.exception.InvalidAddressException;


/**
 * A <code>AddressBookReporter</code> represents access to a wide variety of 
 * address books.
 * <p>
 * Some <code>AddressBookReporter</code> are file, other database-based or web 
 * service oriented. The <code>AddressBookReporter</code> can analyze a wide 
 * variety of address structures. The type of address book entries are 
 * {@link AddressBookEntry}. 
 * <p>
 * @author 	Martin Spillmann
 * @version	1.0
 * @since	1.0
 */
public interface AddressBookReporter {

	/**
	 * Returns a list of all address book entries. There are no guarantees 
	 * concerning the order in which the elements are returned. The list 
	 * entries are objects of class {@link AddressBookEntry}. 
	 * <p>
	 * @return Entries of the address book
	 * @throws InvalidAddressException
	 * @throws IOException
	 */
	public List<AddressBookEntry> getAllAddressEntries() throws InvalidAddressException, IOException;
	
	
	/**
	 * Returns the number of females in the address book.
	 * <p>
	 * @param	femalePersonList
	 * @return	Number of females in the address book
	 */
	public long countFemalePerson(List<AddressBookEntry> femalePersonList);
	
	
	/**
	 * Determines the oldest person in the address book. 
	 * <p>
	 * @param	allPersonList
	 * @return	The address book entry of the oldest person
	 */
	public AddressBookEntry calculateOldestPerson(List<AddressBookEntry> allPersonList);	

	
	/**
	 * Determines the number of days that lie between two date values. 
	 * <p>
	 * @param	list
	 * @param	nameOlderPerson
	 * @param	nameYoungerPerson
	 * @return	The days are between two dates
	 */
	public long calculateDayDifference(List<AddressBookEntry> list, String nameOlderPerson, String nameYoungerPerson);
}
