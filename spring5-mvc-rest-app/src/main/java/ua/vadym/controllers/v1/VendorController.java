package ua.vadym.controllers.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.vadym.api.v1.model.VendorDTO;
import ua.vadym.api.v1.model.VendorListDTO;
import ua.vadym.services.VendorService;

@Api(description = "There is simple Vendor controller")
@RestController
@RequestMapping("/api/v1/vendors/")
public class VendorController {

    private final VendorService service;

    public VendorController(VendorService service) {
        this.service = service;
    }

    @ApiOperation(value = "Get a list of Vendors.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors() {
        return new VendorListDTO(service.getAllVendors());
    }

    @ApiOperation(value = "Get Vendor by id.")
    @GetMapping({"{id}"})
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id) {
        return service.getVendorById(id);
    }

    @ApiOperation(value = "Create new Vendor.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendor) {
        return service.createNewVendor(vendor);
    }

    @ApiOperation(value = "Update Vendor.")
    @PutMapping({"{id}"})
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendor) {
        return service.saveVendorByDTO(id, vendor);
    }

    @ApiOperation(value = "Patch Vendor's fields.")
    @PatchMapping({"{id}"})
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendor) {
        return service.patchVendor(id, vendor);
    }

    @ApiOperation(value = "Delete Vendor by id.")
    @DeleteMapping({"{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id) {
        service.deleteVendorById(id);
    }
}
