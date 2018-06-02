package addressBook.service;

import java.util.ArrayList;
import java.util.Collections;
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
		Collections.sort(contacts, 
                (o1, o2) -> o1.getName().compareTo(o2.getName()));
		return contacts;
	}
	
	public Contact findById(int id){
		Optional<Contact> optionalContact = contactRepository.findById(id);
		Contact contact = new Contact();
		if(optionalContact.isPresent()){
			contact = optionalContact.get();
			return contact;
		}
		return contact;
	}
	
	public Contact findByName(String name){
		Contact findedContact = new Contact();
		for(Contact contact : contactRepository.findAll()){
			if(contact.getName().compareTo(name) == 0){
				findedContact = contact;
			}
		}
		return findedContact;
	}
	
	public void saveContact(Contact contact){
		contactRepository.save(contact);
	}

}
