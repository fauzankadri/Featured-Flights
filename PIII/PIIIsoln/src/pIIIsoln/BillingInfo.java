package pIIIsoln;

/**
 * @author alex
 *
 */
public class BillingInfo {
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String creditCardNum;
	private String expiryDate;
	
	/**
	 * a representation of an user billing Info
	 * creates a empty Billing Info
	 */
	public BillingInfo(){
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.address = "";
		this.creditCardNum = "";
		this.expiryDate = "";
	}
	/**
	 * creates a representation of an user billing info given the
	 * parameters below
	 * @param firstName firstName of the user
	 * @param lastName  lastName of the user
	 * @param email		user's email
	 * @param adress	user's address
	 * @param creditCardNum  user's credit card number
	 * @param expiryDate	user's expire date
	 */
	public BillingInfo(String firstName, String lastName, String email,
			String address, String creditCardNum, String expiryDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.creditCardNum = creditCardNum;
		this.expiryDate = expiryDate;
	}
	/**
	 * returns this firstName
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * sets firstName field to the givenFirstName
	 * @param givenFirstName the given firstName
	 */
	public void setFirstName(String givenFirstName) {
		this.firstName = givenFirstName;
	}
	/**
	 * returns this lastName
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * sets setLastName field to the givenLastName parameter
	 * @param lastName the lastName to set
	 */
	public void setLastName(String givenLastName) {
		this.lastName = givenLastName;
	}
	/**
	 * returns this email
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param gienEmail the givenEmail to set email to
	 */
	public void setEmail(String givenEmail) {
		this.email = givenEmail;
	}
	/**
	 * returns this address
	 * @return this address
	 */
	public String getAdress() {
		return address;
	}
	/**
	 * sets the address to the givenAddress
	 * @param givenAdress the address to set 
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * returns the creditCardNum
	 * @return the creditCardNum
	 */
	public String getCreditCardNum() {
		return creditCardNum;
	}
	/**
	 * sets the creditCardNum to the givenCreditCardNum
	 * @param givenCreditCardNum the creditCardNum to set
	 */
	public void setCreditCardNum(String givenCreditCardNum) {
		this.creditCardNum = givenCreditCardNum;
	}
	/**
	 * returns expiryDate
	 * @return this expiryDate
	 */
	public String getExpiryDate() {
		return expiryDate;
	}
	/**
	 * sets the expiryDate to the givenExpiryDate
	 * @param givenExpiryDate the expiryDate to set
	 */
	public void setExpiryDate(String givenExpiryDate) {
		this.expiryDate = givenExpiryDate;
	}
	/**
	 * returns the billingInfo as a string in the format
	 * LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate
	 */
	@Override
	public String toString() {
		return (this.getLastName() + "," + this.getFirstName() + ","
				+ this.getEmail() + "," + this.getAdress() + "," +
				this.getCreditCardNum() + "," + this.getExpiryDate());
	}	
}
	
