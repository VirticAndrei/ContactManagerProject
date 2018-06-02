package addressBook.controllers;

import java.util.ArrayList;
import java.util.List;

import addressBook.models.Contact;

/**/
public class ValidateContact {

	private final Contact contact;
	private static Boolean error;
	private static List<String> errorMessages;
	private static String errorMsg;
	

	public ValidateContact(Contact contact){
		this.contact = contact;
		error = false;
		errorMessages = new ArrayList<String>();
		validate();
	}
	
	private void validate(){
		error = false;
        validateName();
        validateEmail();
        validatePhone();
        validateStreet();
        validateNumber();
    }
	
	private void validateName(){
		errorMsg = "";
    	final String regex ="[A-Za-z ]+";
        if (!contact.getName().matches(regex)
        		|| contact.getName().length() < 3 || contact.getName().length() > 45){
            error = true;
            errorMsg = "*Incorrect name!";
        }
        errorMessages.add(errorMsg);
    }
	
	private void validateEmail(){
		errorMsg = "";
    	final String regex = "[A-Za-z0-9._-]+[@][A-Za-z.]+";
        if(!contact.getAddress().getEmail().matches(regex) 
        		|| contact.getAddress().getEmail().length() < 12){
            error = true;
            errorMsg = "*Incorrect email!";
        }
        errorMessages.add(errorMsg);
    }
    
	private void validatePhone(){
    	errorMsg = "";
    	final String regex = "[0-9]+";
    	final String phone = contact.getAddress().getPhone().toString();
        if (!phone.matches(regex) || phone.length() != 10){
            error = true;
            errorMsg = "*Incorrect phone!";
        }
        errorMessages.add(errorMsg);
    }
    
	private void validateStreet(){
		errorMsg = "";
		final String regex = "[A-Za-z0-9. ,-]+";
        if(!contact.getAddress().getStreet().matches(regex) 
        		|| contact.getAddress().getStreet().length() < 3){
            error = true;
            errorMsg = "*Incorrect street!";
        }
        errorMessages.add(errorMsg);
    }

	private void validateNumber(){
    	errorMsg = "";
    	String regex = "[0-9]+";
    	final String number = "" + contact.getAddress().getNumber();
        if (!number.matches(regex) || number.length() < 1 
        		|| number.length() > 3){
            error = true;
            errorMsg = "* Incorrect number!";
        }
        errorMessages.add(errorMsg);
    }

    public Boolean isValid(){
        Boolean valid;
    	if (!error){
            valid = true;
        }
    	else{
    		valid = false;
    	}
        return valid;
    }

    public List<String> getErrors(){
        return errorMessages;
    } 
}
