package addressBook.controllers;

import java.util.ArrayList;
import java.util.List;

import addressBook.models.Contact;

public class ValidateContact {

	private Contact contact;
	private Boolean error;
	private List<String> errorMessages;
	private String errorMsg;
	
	public ValidateContact(Contact contact){
		this.contact = contact;
		error = false;
		errorMessages = new ArrayList<String>();
		validate();
	}
	
	public void validate(){
		error = false;
        validate_name();
        validate_email();
        validate_phone();
        validate_street();
        validate_number();
    }
	
	public void validate_name(){
		errorMsg = "";
    	String regex ="[A-Za-z ]+";
        if (contact.getName().matches(regex) == false || contact.getName().length() < 3 || contact.getName().length() > 45){
            error = true;
            errorMsg = "*Incorrect name!";
        }
        errorMessages.add(errorMsg);
    }
	
	public void validate_email(){
		errorMsg = "";
    	String regex = "[A-Za-z0-9._-]+[@][A-Za-z.]+";
        if(contact.getAddress().getEmail().matches(regex) == false || contact.getAddress().getEmail().length() < 12){
            error = true;
            errorMsg = "*Incorrect email!";
        }
        errorMessages.add(errorMsg);
    }
    
    public void validate_phone(){
    	errorMsg = "";
    	String regex = "[0-9]+";
    	String phone = "" + contact.getAddress().getPhone();
        if (phone.matches(regex) == false || phone.length() != 10){
            error = true;
            errorMsg = "*Incorrect phone!";
        }
        errorMessages.add(errorMsg);
    }
    
    public void validate_street(){
		errorMsg = "";
		String regex = "[A-Za-z0-9. ,-]+";
        if(contact.getAddress().getStreet().matches(regex) == false || contact.getAddress().getStreet().length() < 3){
            error = true;
            errorMsg = "*Incorrect street!";
        }
        errorMessages.add(errorMsg);
    }
    
    public void validate_number(){
    	errorMsg = "";
    	String regex = "[0-9]+";
    	String number = "" + contact.getAddress().getNumber();
        if (number.matches(regex) == false || number.length() < 1 || number.length() > 3){
            error = true;
            errorMsg = "* Incorrect number!";
        }
        errorMessages.add(errorMsg);
    }
    
    public Boolean isValid(){
        if (error == false){
            return true;
        }
        return false;
    }
    public List<String> getErrors(){
        return errorMessages;
    } 
}
