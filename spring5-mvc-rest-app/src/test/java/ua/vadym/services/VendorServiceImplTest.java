package ua.vadym.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.vadym.domain.Vendor;
import ua.vadym.api.v1.mapper.VendorMapper;
import ua.vadym.api.v1.model.VendorDTO;
import ua.vadym.exceptions.ApplicationException;
import ua.vadym.repository.VendorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VendorServiceImplTest {

    private static final long ID = 1L;
    private static final String VENDOR_NAME = "Vendor name";
    private static final String PATCHED_NAME = "Patched Name";

    @Mock
    VendorRepository repository;

    private VendorService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new VendorServiceImpl(repository, VendorMapper.INSTANCE);
    }

    @Test
    public void getAllVendors() {
        //given
        List<Vendor> vendors = Arrays.asList(Vendor.builder().build(), Vendor.builder().build());
        when(repository.findAll()).thenReturn(vendors);

        //when
        List<VendorDTO> actual = service.getAllVendors();

        //then
        assertNotNull(actual);
        assertEquals(2, actual.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void getVendorById() {
        //given
        Vendor vendor = Vendor.builder().id(ID).name(VENDOR_NAME).build();
        when(repository.findById(ID)).thenReturn(Optional.of(vendor));

        //when
        VendorDTO actual = service.getVendorById(ID);

        //then
        assertEquals(VENDOR_NAME, actual.getName());
        assertEquals("/api/v1/vendors/1", actual.getUrl());
        verify(repository, times(1)).findById(ID);
    }

    @Test(expected = ApplicationException.class)
    public void findById_shouldThrowExceptionIfNotFound() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        //then-when
        service.getVendorById(ID);
    }

    @Test
    public void createNewVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDOR_NAME);

        Vendor toSaveVendor = Vendor.builder().name(VENDOR_NAME).build();
        Vendor savedVendor = Vendor.builder().id(ID).name(VENDOR_NAME).build();
        when(repository.save(any(Vendor.class))).thenReturn(savedVendor);

        VendorDTO expected = new VendorDTO();
        expected.setName(VENDOR_NAME);
        expected.setUrl("/api/v1/vendors/1");

        //when
        VendorDTO actual = service.createNewVendor(vendorDTO);

        //then
        assertThat(actual, is(expected));
        verify(repository, times(1)).save(toSaveVendor);
    }

    @Test
    public void saveVendorByDto() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDOR_NAME);

        Vendor savedVendor = Vendor.builder().id(ID).name(VENDOR_NAME).build();
        when(repository.save(any(Vendor.class))).thenReturn(savedVendor);

        VendorDTO expected = new VendorDTO();
        expected.setName(VENDOR_NAME);
        expected.setUrl("/api/v1/vendors/1");

        ArgumentCaptor<Vendor> argumentCaptor = ArgumentCaptor.forClass(Vendor.class);

        //when
        VendorDTO actual = service.saveVendorByDTO(ID, vendorDTO);

        //then
        assertThat(actual, is(expected));
        verify(repository, times(1)).save(argumentCaptor.capture());
        assertEquals(Long.valueOf(ID), argumentCaptor.getValue().getId());
    }

    @Test
    public void patchVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(PATCHED_NAME);

        Vendor initialVendor = Vendor.builder().id(ID).name(VENDOR_NAME).build();
        Vendor patchedVendor = Vendor.builder().id(ID).name(PATCHED_NAME).build();
        when(repository.findById(ID)).thenReturn(Optional.of(initialVendor));

        Vendor savedVendor = Vendor.builder().id(ID).name(PATCHED_NAME).build();
        when(repository.save(any(Vendor.class))).thenReturn(savedVendor);

        VendorDTO expected = new VendorDTO();
        expected.setName(PATCHED_NAME);
        expected.setUrl("/api/v1/vendors/1");

        ArgumentCaptor<Vendor> argumentCaptor = ArgumentCaptor.forClass(Vendor.class);

        //when
        VendorDTO actual = service.patchVendor(ID, vendorDTO);
        assertThat(actual, is(expected));
        verify(repository, times(1)).findById(ID);
        verify(repository, times(1)).save(argumentCaptor.capture());
        assertThat(patchedVendor, is(argumentCaptor.getValue()));
    }

    @Test(expected = ApplicationException.class)
    public void patchVendor_shouldThrowExceptionIfNotFound() {
        //given
        VendorDTO vendorDTO = new VendorDTO();

        when(repository.findById(ID)).thenReturn(Optional.empty());

        //then-when
        service.patchVendor(ID, vendorDTO);
    }

    @Test
    public void deleteVendorById() {
        //when
        service.deleteVendorById(ID);

        //then
        verify(repository, times(1)).deleteById(ID);
    }
}