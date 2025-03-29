package com.grocery.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    // Basic CRUD operations are inherited from JpaRepository

    // Add custom query methods if needed, e.g.,
    // List<Address> findByCityAndState(String city, String state);
}
