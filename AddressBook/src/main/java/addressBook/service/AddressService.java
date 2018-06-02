package addressBook.service;

//import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import addressBook.dataAccess.AddressRepository;
//import addressBook.dataAccess.ContactRepository;
import addressBook.models.Address;

@Service
@Transactional
public class AddressService {

	@Autowired 
	private AddressRepository addressRepository;
/*	@Autowired
	private ContactRepository contactRepository;*/
	
	
	public void saveAddress(Address address) {
		addressRepository.save(address);
	}
	
	public void deleteAddress(int id){
		addressRepository.deleteById(id);
	}
}
