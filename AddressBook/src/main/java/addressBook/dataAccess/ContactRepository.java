package addressBook.dataAccess;

import org.springframework.data.repository.CrudRepository;

import addressBook.models.Contact;

public interface ContactRepository extends CrudRepository<Contact, Integer> {

}
