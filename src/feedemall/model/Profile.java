package feedemall.model;

public class Profile {

	String username;
	String password;
	String email;
	String name;
	String address;
	String phoneno;
	String zipcode;
	
	public Profile(String name, String email, String address, String phoneNumber) {
		this.email = email;
		this.name = name;
		this.address = address;
		this.phoneno = phoneNumber;
	}
	
	public Profile(String username, String name,
			String email, String address, String phoneNumber) {
		
		this(name, email, address, phoneNumber);
		this.username = username;
	}
	
	public Profile(String username, String password, String name,
			String email, String address, String phoneNumber) {

		this(username, name, email, address, phoneNumber);
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
}
