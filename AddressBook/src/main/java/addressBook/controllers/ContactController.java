package addressBook.controllers;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import sun.misc.BASE64Decoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@SuppressWarnings("restriction")
@Controller
public class ContactController {
	
	@Autowired
	ContactService contactService;
	@Autowired
	AddressService addressService;
	
	@Value("${spring.default.image}")
	private String userDefaultImage;
	
	private String mode;
	
	@GetMapping("/")
	public String home(HttpServletRequest request){
		request.setAttribute("contacts", contactService.findAll());
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
		request.setAttribute("pictureFile", userDefaultImage.toString());
		request.setAttribute("titleString", "Add Contact");
		mode = "MODE_NEW";
		request.setAttribute("mode", mode);
		
		return "index";
		
	}
	
	@PostMapping("/save-contact")
	public String saveContact(@ModelAttribute Contact contact, @ModelAttribute Address address, @RequestParam("pictureFile") MultipartFile pictureFile, BindingResult bindingResult, HttpServletRequest request){
		address.setId(Integer.parseInt(request.getParameter("id_address")));
		contact.setId(Integer.parseInt(request.getParameter("id_contact")));
		contact.setAddress(address);
		ValidateContact validateContact = new ValidateContact(contact);
		if(validateContact.isValid()){
			if(!pictureFile.isEmpty()){
				try{
					contact.setPicture(pictureFile.getBytes());
				} catch(IOException e){e.printStackTrace();}
			}
			else{
				String image = request.getParameter("pictureString");
				StringTokenizer parts = new StringTokenizer(image, ","); 
				String imageString = parts.nextToken();
				imageString = parts.nextToken();
				byte[] imageByte = null;
				BASE64Decoder decoder = new BASE64Decoder();
				try {
					imageByte = decoder.decodeBuffer(imageString);
				} catch (IOException e) {e.printStackTrace();}
				contact.setPicture(imageByte);
			}
			address.setContact(contact);
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
	public String searchContact(@RequestParam String name, HttpServletRequest request){
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
		Contact contact = contactService.findById(idContact);
		request.setAttribute("contact", contact);
		request.setAttribute("address", contact.getAddress());
		request.setAttribute("pictureFile", contact.getPicture());
		request.setAttribute("titleString", "Edit Contact");
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
