package br.com.catalisa.ZuPlaceApi.repository;

import br.com.catalisa.ZuPlaceApi.model.ResourceModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ResourceRepositoryTest {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveResource() {
        ResourceModel resource = new ResourceModel();
        resource.setName("Água potável");
        ResourceModel savedResource = resourceRepository.save(resource);
        assertNotNull(savedResource.getId());
        assertEquals("Água potável", savedResource.getName());
    }

    @Test
    public void testFindById() {
        ResourceModel resource = new ResourceModel();
        resource.setName("Água potável");
        entityManager.persist(resource);
        Optional<ResourceModel> foundResource = resourceRepository.findById(resource.getId());
        assertTrue(foundResource.isPresent());
        assertEquals("Água potável", foundResource.get().getName());
    }

    @Test
    public void testFindAllResources() {
        ResourceModel resource1 = new ResourceModel();
        resource1.setName("Água potável");
        entityManager.persist(resource1);

        ResourceModel resource2 = new ResourceModel();
        resource2.setName("Internet");
        entityManager.persist(resource2);

        List<ResourceModel> resources = resourceRepository.findAll();
        assertEquals(2, resources.size());
    }

    @Test
    public void testUpdateResource() {
        ResourceModel resource = new ResourceModel();
        resource.setName("Água potável");
        entityManager.persist(resource);

        resource.setName("Água mineral");
        ResourceModel updatedResource = resourceRepository.save(resource);

        assertEquals("Água mineral", updatedResource.getName());
    }

    @Test
    public void testDeleteResource() {
        ResourceModel resource = new ResourceModel();
        resource.setName("Água potável");
        entityManager.persist(resource);

        resourceRepository.deleteById(resource.getId());
        assertFalse(resourceRepository.findById(resource.getId()).isPresent());
    }
}

