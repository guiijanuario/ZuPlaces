package br.com.catalisa.ZuPlaceApi.repository;

import br.com.catalisa.ZuPlaceApi.model.AddressModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveAddress() {
        AddressModel address = new AddressModel();
        address.setCep("87000-00");
        address.setLogradouro("Rua Juruá");
        address.setNumberAddress("30 A");

        AddressModel savedAddress = addressRepository.save(address);

        assertNotNull(savedAddress.getId());
        assertEquals("87000-00", savedAddress.getCep());
        assertEquals("Rua Juruá", savedAddress.getLogradouro());
    }

    @Test
    public void testFindById() {
        AddressModel address = new AddressModel();
        address.setCep("87000-01");
        address.setLogradouro("Avenida Amazonas");
        entityManager.persist(address);

        Optional<AddressModel> foundAddress = addressRepository.findById(address.getId());

        assertTrue(foundAddress.isPresent());
        assertEquals("87000-01", foundAddress.get().getCep());
        assertEquals("Avenida Amazonas", foundAddress.get().getLogradouro());
    }

    @Test
    public void testFindAllAddresses() {
        AddressModel address1 = new AddressModel();
        address1.setCep("87000-02");
        address1.setLogradouro("Rua ABC");
        entityManager.persist(address1);

        AddressModel address2 = new AddressModel();
        address2.setCep("87000-03");
        address2.setLogradouro("Rua XYZ");
        entityManager.persist(address2);

        Iterable<AddressModel> addresses = addressRepository.findAll();

        assertNotNull(addresses);
    }

    @Test
    public void testUpdateAddress() {
        AddressModel address = new AddressModel();
        address.setCep("87000-04");
        address.setLogradouro("Rua PQR");
        entityManager.persist(address);

        address.setLogradouro("Rua LMN");
        AddressModel updatedAddress = addressRepository.save(address);

        assertEquals("Rua LMN", updatedAddress.getLogradouro());
    }

    @Test
    public void testDeleteAddress() {
        AddressModel address = new AddressModel();
        address.setCep("87000-05");
        address.setLogradouro("Rua DEF");
        entityManager.persist(address);

        addressRepository.deleteById(address.getId());
        assertFalse(addressRepository.findById(address.getId()).isPresent());
    }
}

