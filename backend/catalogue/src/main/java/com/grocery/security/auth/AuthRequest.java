package com.grocery.security.auth;

import lombok.Data;

public record AuthRequest(String username, String password) {
}
