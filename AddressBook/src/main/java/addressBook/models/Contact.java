package addressBook.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.xml.bind.DatatypeConverter;


@Entity(name="contact")
public class Contact implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "idcontact", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idContact;
	@Column(name = "name", length = 50, nullable = false)
	private String name;
	@Column(name = "picture", length = 16777215, nullable = true)
	@Lob
	private byte[] picture;
	
	@OneToOne
	@JoinColumn(name="address_id")
    private Address address = null;
	
	public Contact(){
		
	}
	
	public Contact(String name, String email, String phone, byte[] picture, Address address) {
		super();
		this.name = name;
		this.picture = picture;
		this.address = address;
	}
	
	public int getId() {
		return idContact;
	}
	public void setId(int id) {
		this.idContact = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicture() {
		String imageSrc = "";
		if(picture != null)
		{
			String base64 =  DatatypeConverter.printBase64Binary(picture);
			imageSrc = "data:image/png;base64," + base64;
		}
		return imageSrc;
	}
	public void setPicture(byte[] picture){
		this.picture = picture;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Contact [id=" + idContact + ", name=" + name +  ", address=" + address + "]";
	}
}
