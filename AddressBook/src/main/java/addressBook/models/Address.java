package addressBook.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name="address")
public class Address implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "idaddress", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idAddress;
	@Column(name = "email", length = 45, nullable = true)
	private String email;
	@Column(name = "phone", length = 15, nullable = true)
	private String phone;
	@Column(name = "street", length = 45, nullable = true)
	private String street;
	@Column(name = "number", length = 5, nullable = true)
	private int number;

	@OneToOne( cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="address")
	private Contact contact = null;
	
	public Address(){
		
	}
	
	public Address(String email, String phone, Contact contact) {
		super();
		this.email = email;
		this.phone = phone;
		this.contact = contact;
	}
	
	public int getId() {
		return idAddress;
	}
	public void setId(int id) {
		this.idAddress = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "Address [id=" + idAddress + ", email=" + email + ", phone=" + "]";
	}
}
