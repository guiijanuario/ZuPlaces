package br.com.catalisa.ZuPlaceApi.model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressModelTest {

    @Test
    public void testGettersAndSetters() {
        AddressModel address = new AddressModel();
        address.setId(1L);
        address.setCep("87000-00");
        address.setLogradouro("Rua Juruá");
        address.setNumberAddress("30 A");
        address.setComplement("Ponto de referência");
        address.setBairro("Jardim Antunes");
        address.setLocalidade("Maringá");
        address.setUf("PR");
        address.setLatitude(123.456);
        address.setLongitude(789.012);

        assertEquals(1L, address.getId());
        assertEquals("87000-00", address.getCep());
        assertEquals("Rua Juruá", address.getLogradouro());
        assertEquals("30 A", address.getNumberAddress());
        assertEquals("Ponto de referência", address.getComplement());
        assertEquals("Jardim Antunes", address.getBairro());
        assertEquals("Maringá", address.getLocalidade());
        assertEquals("PR", address.getUf());
        assertEquals(123.456, address.getLatitude());
        assertEquals(789.012, address.getLongitude());
    }

    @Test
    public void testSpaceAssociation() {
        AddressModel address = new AddressModel();
        SpaceModel space = new SpaceModel();

        address.setSpace(space);

        assertEquals(space, address.getSpace());
    }
}

