package cz.fi.muni.pa165.rest.exceptions;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
public class NotAuthorizedException extends RuntimeException {
	public NotAuthorizedException(String message) {
		super(message);
	}
}
