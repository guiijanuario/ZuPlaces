package br.com.catalisa.ZuPlaceApi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class ResourceModelTest {

    @Mock
    private SpaceModel spaceModel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGettersAndSetters() {
        ResourceModel resource = new ResourceModel();
        resource.setId(1L);
        resource.setName("Água potável");

        assertEquals(1L, resource.getId());
        assertEquals("Água potável", resource.getName());
    }

    @Test
    public void testSpaces() {
        List<SpaceModel> spaces = new ArrayList<>();
        spaces.add(spaceModel);

        ResourceModel resource = new ResourceModel();
        resource.setId(1L);
        resource.setName("Água potável");
        resource.setSpaces(spaces);

        assertEquals(1, resource.getSpaces().size());
        assertEquals(spaceModel, resource.getSpaces().get(0));
    }
}

