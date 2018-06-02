package addressBook.dataAccess;

import org.springframework.data.repository.CrudRepository;

import addressBook.models.Address;

public interface AddressRepository extends CrudRepository<Address, Integer>  {

}
