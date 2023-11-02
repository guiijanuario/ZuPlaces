package br.com.catalisa.ZuPlaceApi.repository;

import br.com.catalisa.ZuPlaceApi.model.ContactModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveContact() {
        ContactModel contact = new ContactModel();
        contact.setName("John Doe");
        contact.setEmail("john.doe@example.com");
        ContactModel savedContact = contactRepository.save(contact);
        assertNotNull(savedContact.getId());
        assertEquals("John Doe", savedContact.getName());
        assertEquals("john.doe@example.com", savedContact.getEmail());
    }

    @Test
    public void testFindById() {
        ContactModel contact = new ContactModel();
        contact.setName("Jane Smith");
        contact.setEmail("jane.smith@example.com");
        entityManager.persist(contact);
        Optional<ContactModel> foundContact = contactRepository.findById(contact.getId());
        assertTrue(foundContact.isPresent());
        assertEquals("Jane Smith", foundContact.get().getName());
        assertEquals("jane.smith@example.com", foundContact.get().getEmail());
    }

    @Test
    public void testFindAllContacts() {
        ContactModel contact1 = new ContactModel();
        contact1.setName("Alice");
        contact1.setEmail("alice@example.com");
        entityManager.persist(contact1);

        ContactModel contact2 = new ContactModel();
        contact2.setName("Bob");
        contact2.setEmail("bob@example.com");
        entityManager.persist(contact2);

        List<ContactModel> contacts = contactRepository.findAll();
        assertEquals(2, contacts.size());
    }

    @Test
    public void testUpdateContact() {
        ContactModel contact = new ContactModel();
        contact.setName("Charlie");
        contact.setEmail("charlie@example.com");
        entityManager.persist(contact);

        contact.setName("David");
        ContactModel updatedContact = contactRepository.save(contact);

        assertEquals("David", updatedContact.getName());
    }

    @Test
    public void testDeleteContact() {
        ContactModel contact = new ContactModel();
        contact.setName("Eve");
        contact.setEmail("eve@example.com");
        entityManager.persist(contact);

        contactRepository.deleteById(contact.getId());
        assertFalse(contactRepository.findById(contact.getId()).isPresent());
    }

}

