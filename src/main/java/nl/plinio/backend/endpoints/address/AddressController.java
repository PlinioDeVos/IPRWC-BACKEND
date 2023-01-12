package nl.plinio.backend.endpoints.address;

import lombok.RequiredArgsConstructor;
import nl.plinio.backend.endpoints.address.model.Address;
import nl.plinio.backend.endpoints.address.model.AddressDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@PreAuthorize("hasAuthority('admin')")
@RestController
@RequestMapping("address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<AddressDto> getAllAddresses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        return addressService.getAllAddresses(page, size, sort);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressDto getAddress(@PathVariable UUID id) {
        return new AddressDto(addressService.findAddress(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDto createAddress(@RequestBody Address address) {
        return addressService.createAddress(address);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressDto updateAddress(@PathVariable UUID id, @RequestBody Address address) {
        return addressService.updateAddress(id, address);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
    }
}
