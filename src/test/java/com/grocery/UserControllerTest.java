package com.grocery;

import com.grocery.security.JwtUtil;
import com.grocery.security.auth.AuthRequest;
import com.grocery.user.User;
import com.grocery.user.UserController;
import com.grocery.user.UserService;
import com.grocery.user.contracts.GenerateTokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userInfoService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testWelcome() {
        String response = userController.welcome();
        assertEquals("Welcome this endpoint is not secure", response);
    }

    @Test
    void testAddNewUser() {
        User user = new User();
        when(userInfoService.addUser(any(User.class))).thenReturn("User added successfully");

        String response = userController.addNewUser(user);

        assertEquals("User added successfully", response);
    }

    @Test
    void testUserProfile() {
        String response = userController.userProfile();
        assertEquals("Welcome to User Profile", response);
    }

    @Test
    void testAdminProfile() {
        String response = userController.adminProfile();
        assertEquals("Welcome to Admin Profile", response);
    }

    @Test
    void testAuthenticateAndGetToken_Success() {
        AuthRequest authRequest = new AuthRequest("user", "password");
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtil.generateToken(anyString())).thenReturn("token");

        ResponseEntity<GenerateTokenResponse> response = userController.authenticateAndGetToken(authRequest);

        assertEquals("token", response.getBody().token());
    }

    @Test
    void testAuthenticateAndGetToken_Failure() {
        AuthRequest authRequest = new AuthRequest("user", "password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new UsernameNotFoundException("Invalid user request!"));

        assertThrows(UsernameNotFoundException.class, () -> userController.authenticateAndGetToken(authRequest));
    }
}