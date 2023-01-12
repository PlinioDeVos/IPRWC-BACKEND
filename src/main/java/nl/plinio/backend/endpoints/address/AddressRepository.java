package nl.plinio.backend.endpoints.address;

import nl.plinio.backend.endpoints.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    @Override
    List<Address> findAll();
}
