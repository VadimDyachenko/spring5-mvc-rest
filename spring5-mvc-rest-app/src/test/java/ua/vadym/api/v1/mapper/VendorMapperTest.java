package ua.vadym.api.v1.mapper;

import org.junit.Test;
import ua.vadym.api.v1.model.VendorDTO;
import ua.vadym.domain.Vendor;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class VendorMapperTest {

    private static final String VENDOR_NAME = "Vendor Name";

    private VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void mapVendorToVendorDto() {
        //given
        Vendor vendor = Vendor.builder().id(1L).name(VENDOR_NAME).build();
        VendorDTO expected = new VendorDTO();
        expected.setName(VENDOR_NAME);

        //when-then
        assertThat(vendorMapper.vendorToVendorDto(vendor), is(expected));
    }

    @Test
    public void mapVendorDtoToVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDOR_NAME);
        vendorDTO.setUrl("url");

        Vendor expected = Vendor.builder().name(VENDOR_NAME).build();
        expected.setName(VENDOR_NAME);

        //when-then
        assertThat(vendorMapper.vendorDtoToVendor(vendorDTO), is(expected));
    }
}