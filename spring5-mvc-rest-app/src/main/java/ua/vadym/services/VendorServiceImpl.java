package ua.vadym.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vadym.api.v1.mapper.VendorMapper;
import ua.vadym.api.v1.model.VendorDTO;
import ua.vadym.domain.Vendor;
import ua.vadym.exceptions.ApplicationException;
import ua.vadym.exceptions.ResourceNotFoundError;
import ua.vadym.repository.VendorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository repository, VendorMapper vendorMapper) {
        this.vendorRepository = repository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(this::getVendorDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VendorDTO getVendorById(long id) {
        return vendorRepository.findById(id)
                .map(this::getVendorDTO)
                .orElseThrow(() -> new ApplicationException(new ResourceNotFoundError("Vendor with id:" + id + " not found.")));
    }

    @Override
    @Transactional
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        Vendor savedVendor = vendorRepository.save(vendor);

        return getVendorDTO(savedVendor);
    }

    @Override
    @Transactional
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        vendor.setId(id);
        Vendor savedVendor = vendorRepository.save(vendor);

        return getVendorDTO(savedVendor);
    }

    @Override
    @Transactional
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        Vendor patchedVendor = vendorRepository.findById(id)
                .map(vendor -> {
                    String name = vendorDTO.getName();
                    if(name != null) {
                        vendor.setName(name);
                    }
                    return vendor;
                })
                .orElseThrow(() -> new ApplicationException(new ResourceNotFoundError("Vendor with id:" + id + " not found.")));
        return getVendorDTO(vendorRepository.save(patchedVendor));
    }

    @Override
    @Transactional
    public void deleteVendorById(long id) {
        vendorRepository.deleteById(id);
    }

    private VendorDTO getVendorDTO(Vendor vendor) {
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);
        vendorDTO.setUrl("/api/v1/vendors/" + vendor.getId());
        return vendorDTO;
    }
}
