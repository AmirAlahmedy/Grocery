# Grocery Store Application

This is a Spring Boot application for managing a grocery store. It includes user authentication and authorization features, allowing users to register, log in, and access different profiles based on their roles.

## Features

- User registration
- User authentication with JWT
- Role-based access control
- Secure and non-secure endpoints

## Technologies Used

- Java
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/AmirAlahmedy/Grocery.git
    cd grocery-store
    ```

2. Build the project:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

## Usage

### Endpoints

- `GET /auth/welcome` - Public endpoint, no authentication required.
- `POST /auth/addNewUser` - Register a new user.
- `POST /auth/generateToken` - Authenticate and get a JWT token.
- `GET /auth/user/userProfile` - Access user profile (requires `ROLE_USER`).
- `GET /auth/admin/adminProfile` - Access admin profile (requires `ROLE_ADMIN`).

### Example Requests

#### Register a New User

```sh
curl -X POST http://localhost:8080/auth/addNewUser -H "Content-Type: application/json" -d '{"username": "user@example.com", "password": "password"}'
```

#### Authenticate and Get Token

```sh
curl -X POST http://localhost:8080/auth/generateToken -H "Content-Type: application/json" -d '{"username": "user@example.com", "password": "password"}'
```

#### Access User Profile

```sh
curl -X GET http://localhost:8080/auth/user/userProfile -H "Authorization: Bearer <your-jwt-token>"
```

## Project Structure

- `src/main/java/com/grocery/security/auth/AuthRequest.java` - Defines the authentication request.
- `src/main/java/com/grocery/user/UserController.java` - Handles user-related endpoints.
- `src/main/java/com/grocery/user/UserInfoService.java` - Manages user information and authentication.

## Credits
I followed these tutorials to create this project:
    - [Spring Boot + Spring Security + JWT](https://www.geeksforgeeks.org/spring-boot-3-0-jwt-authentication-with-spring-security-using-mysql-database/)


## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.