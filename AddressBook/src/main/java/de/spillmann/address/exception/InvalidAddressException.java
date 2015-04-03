package de.spillmann.address.exception;

import de.spillmann.address.AddressBookAnalyser;
import de.spillmann.address.AddressBookReporter;
import de.spillmann.address.FileAddressBookReporter;


/**
 * The style of Javadoc how I handle please refer to the classes 
 * {@link AddressBookAnalyser}, {@link AddressBookReporter} and 
 * {@link FileAddressBookReporter}.
 * <p>
 * @author Martin Spillmann
 * @version 1.0
 * @version 1.0
 */
public class InvalidAddressException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidAddressException() {
	}

	public InvalidAddressException(String message) {
		super(message);
	}

	public InvalidAddressException(Throwable cause) {
		super(cause);
	}

	public InvalidAddressException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidAddressException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
