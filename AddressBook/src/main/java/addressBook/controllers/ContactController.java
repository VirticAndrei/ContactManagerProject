package addressBook.controllers;



import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import addressBook.models.Address;
import addressBook.models.Contact;
import addressBook.service.ContactService;
import addressBook.service.AddressService;

@Controller
public class ContactController {
	
	@Autowired
	ContactService contactService;
	@Autowired
	AddressService addressService;
	
	private String mode;
	
	@GetMapping("/")
	public String home(HttpServletRequest request){
		Contact contact = new Contact();
		request.setAttribute("contact", contact);
		mode = "MODE_HOME";
		request.setAttribute("mode", mode);
		return "index";
	}
	
	@GetMapping("/all-contacts")
	public String allCotacts(HttpServletRequest request){
		request.setAttribute("contacts", contactService.findAll());
		mode = "MODE_CONTACTS";
		request.setAttribute("mode", mode);
		return "index";
	}
	
	@GetMapping("/new-contact")
	public String newContact(HttpServletRequest request){
		Contact contact = new Contact();
		Address address = new Address();
		request.setAttribute("contact", contact);
		request.setAttribute("address", address);
		mode = "MODE_NEW";
		request.setAttribute("mode", mode);
		return "index";
	}
	
	@PostMapping("/save-contact")
	public String saveContact(@ModelAttribute Contact contact, @ModelAttribute Address address, @RequestParam("file") MultipartFile file, BindingResult bindingResult, HttpServletRequest request){
		contact.setAddress(address);
		ValidateContact validateContact = new ValidateContact(contact);
		if(validateContact.isValid()){
			if(file!=null){
				try{contact.setPicture(file.getBytes());}
				catch(IOException ex){}
			}
			addressService.saveAddress(address);
			contactService.saveContact(contact);
			request.setAttribute("contacts", contactService.findAll());
			mode = "MODE_CONTACTS";
			request.setAttribute("mode", mode);
		}
		else{
			request.setAttribute("errorMessages", validateContact.getErrors());
			request.setAttribute("mode", mode);
		}
		return "index";
	}
	
	@GetMapping("/search-contact")
	public String saveContact(@RequestParam String name, HttpServletRequest request){
		Contact sendContact = contactService.findByName(name);
		request.setAttribute("contact", sendContact);
		request.setAttribute("address", sendContact.getAddress());
		if(sendContact.getName()==null){
			mode = "MODE_SEARCH_NOT_FOUND";
			request.setAttribute("mode", mode);
		}
		else{
			mode = "MODE_SEARCH";
			request.setAttribute("mode", mode);
		}
		
		return "index";
	}
	
	@GetMapping("/update-contact")
	public String updateContact(@RequestParam int idContact, HttpServletRequest request){
		request.setAttribute("contact", contactService.findById(idContact));
		request.setAttribute("address", contactService.findById(idContact).getAddress());
		mode = "MODE_UPDATE";
		request.setAttribute("mode", mode);
		return "index";
	}
	
	@GetMapping("/delete-contact")
	public String deleteContact(@RequestParam int idContact, HttpServletRequest request){
		Contact contact = contactService.findById(idContact);
		int idAddress = contact.getAddress().getId();
		addressService.deleteAddress(idAddress);
		request.setAttribute("contacts", contactService.findAll());
		mode = "MODE_CONTACTS";
		request.setAttribute("mode", mode);
		return "index";
	}
}
