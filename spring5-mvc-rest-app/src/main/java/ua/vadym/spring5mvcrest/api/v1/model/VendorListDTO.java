package ua.vadym.spring5mvcrest.api.v1.model;

import java.util.Collections;
import java.util.List;

public class VendorListDTO {
    private final List<VendorDTO> vendors;

    public VendorListDTO(List<VendorDTO> vendors) {
        this.vendors = vendors == null ? Collections.emptyList() : vendors;
    }

    public List<VendorDTO> getVendors() {
        return this.vendors;
    }
}
