package pIIIsoln;

import java.io.Serializable;

/**
 * 
 * @author alex
 *
 */
public abstract class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8807821421422769921L;
	protected String email;
	protected String password;
	
	public User(){
		this.email = "";
		this.password = "password";
	}
	
	public User(String email){
		this.email = email;
		this.password = "password";
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
