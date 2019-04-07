package ua.vadym.api.v1.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class VendorListDTOTest {
    @Test
    public void getVendors_shouldReturnEmptyList() {
        VendorListDTO list = new VendorListDTO(null);
        assertTrue(list.getVendors().isEmpty());
    }

    @Test
    public void getVendors() {
        List<VendorDTO> vendorDTOList = Arrays.asList(new VendorDTO(), new VendorDTO());
        VendorListDTO list = new VendorListDTO(vendorDTOList);
        assertThat(vendorDTOList, is(list.getVendors()));
    }
}