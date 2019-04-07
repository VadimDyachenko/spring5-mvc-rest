package ua.vadym.spring5mvcrest.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.vadym.spring5mvcrest.api.v1.model.VendorDTO;
import ua.vadym.spring5mvcrest.domain.Vendor;

@Mapper
public interface VendorMapper {

    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO vendorToVendorDto(Vendor vendor);

    Vendor vendorDtoToVendor(VendorDTO vendorDTO);
}
