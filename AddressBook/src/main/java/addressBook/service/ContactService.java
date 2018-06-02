package addressBook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import addressBook.models.Contact;
import addressBook.dataAccess.ContactRepository;

@Service
@Transactional
public class ContactService {

	private final ContactRepository contactRepository;
	
	public ContactService(ContactRepository contactRepository){
		this.contactRepository = contactRepository;
	}
	
	public List<Contact> findAll(){
		List<Contact> contacts = new ArrayList<Contact>();
		for(Contact contact : contactRepository.findAll()){
			contacts.add(contact);
		}
		return contacts;
	}
	
	public Contact findById(int id){
		Optional<Contact> optionalContact = contactRepository.findById(id);
		Contact contact = new Contact();
		if(optionalContact.isPresent())
		{
			contact = optionalContact.get();
			return contact;
		}
		return contact;
	}
	
	public Contact findByName(String name){
		Contact findedContact = new Contact();
		for(Contact contact : contactRepository.findAll()){
			if(contact.getName().compareTo(name) == 0)
			{
				findedContact = contact;
			}
		}
		return findedContact;
	}
	
	public void saveContact(Contact contact){
		contactRepository.save(contact);
	}
	
	public void deleteContact(int id){
		contactRepository.deleteById(id);
	}
}
