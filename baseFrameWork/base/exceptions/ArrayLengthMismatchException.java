package base.exceptions;

public class ArrayLengthMismatchException extends RuntimeException{

	/**
	 * 
	 */
	
	// suggested by eclipse to do to avoid warning
	private static final long serialVersionUID = 1L;
	
	public ArrayLengthMismatchException(String message) {
		super(message);
	}

}
