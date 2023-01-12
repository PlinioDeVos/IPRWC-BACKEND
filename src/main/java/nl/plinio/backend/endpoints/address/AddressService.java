package nl.plinio.backend.endpoints.address;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.plinio.backend.endpoints.address.model.Address;
import nl.plinio.backend.endpoints.address.model.AddressDto;
import nl.plinio.backend.exception.EnitityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address findAddress(UUID id) {
        return addressRepository.findById(id).orElseThrow(() -> new EnitityNotFoundException(Address.class));
    }

    public Page<AddressDto> getAllAddresses(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        List<AddressDto> addressResponseList = new ArrayList<>();
        Page<Address> existingAddresses = addressRepository.findAll(pageable);
        existingAddresses.forEach(address -> addressResponseList.add(new AddressDto(address)));
        return new PageImpl<>(addressResponseList, pageable, existingAddresses.getTotalElements());
    }

    public AddressDto createAddress(Address address) {
        return new AddressDto(addressRepository.save(address));
    }

    public AddressDto updateAddress(UUID id, Address address) {
        Address existingAddress = findAddress(id);
        existingAddress.setCountry(address.getCountry());
        existingAddress.setProvince(address.getProvince());
        existingAddress.setStreet(address.getStreet());
        existingAddress.setStreetNumber(address.getStreetNumber());
        existingAddress.setPostalCode(address.getPostalCode());
        return new AddressDto(addressRepository.save(existingAddress));
    }

    public void deleteAddress(UUID id) {
        try {
            addressRepository.deleteById(id);
        } catch (IllegalArgumentException ex) {
            throw new EnitityNotFoundException(Address.class);
        }
    }
}
