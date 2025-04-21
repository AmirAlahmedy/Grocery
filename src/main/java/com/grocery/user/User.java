package com.grocery.user;

import com.grocery.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "is_active")
    private boolean isActive;

    private String roles;

}