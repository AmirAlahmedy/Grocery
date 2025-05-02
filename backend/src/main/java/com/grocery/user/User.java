package com.grocery.user;

import com.grocery.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private static Logger log = LoggerFactory.getLogger(User.class);
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String userId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Transient
    private String fullName;
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

    @PrePersist
    public void logNewUserAttempt() {
        log.info("Attempting to add new user with username: {}", email);
    }

    @PostPersist
    public void logNewUserAdded() {
        log.info("Added user '{}' with ID: {}", email, userId);
    }

    @PreRemove
    public void logUserRemovalAttempt() {
        log.info("Attempting to delete user: {}", email);
    }

    @PostRemove
    public void logUserRemoval() {
        log.info("Deleted user: {}", email);
    }

    @PreUpdate
    public void logUserUpdateAttempt() {
        log.info("Attempting to update user: {}", email);
    }

    @PostUpdate
    public void logUserUpdate() {
        log.info("Updated user: {}", email);
    }

    @PostLoad
    public void logUserLoad() {
        fullName = firstName + " " + lastName;
    }

}