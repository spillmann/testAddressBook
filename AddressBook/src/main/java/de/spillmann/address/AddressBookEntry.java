package de.spillmann.address;

import java.time.LocalDate;


/**
 * The style of Javadoc how I handle please refer to the classes 
 * {@link AddressBookAnalyser}, {@link AddressBookReporter} and 
 * {@link FileAddressBookReporter}.
 * <p>
 * @author Martin Spillmann
 * @version 1.0
 * @version 1.0
 */
public class AddressBookEntry {
	private String name;
	private String sex; 
	private LocalDate birthdate;
	
	
	
	@Override
	public String toString() {
		return "Address [name=" + name + ", sex=" + sex + ", birthdate="
				+ birthdate + "]";
	}


	public AddressBookEntry() {
		super();
	}

	
	public AddressBookEntry(String name, String sex, LocalDate birthdate) {
		super();
		this.name = name;
		this.sex = sex;
		this.birthdate = birthdate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public LocalDate getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	
	

}
