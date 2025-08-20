package br.com.thiagoodev.blogapi.domain.entities;

import br.com.thiagoodev.blogapi.domain.exceptions.InvalidUuidFormatException;
import br.com.thiagoodev.blogapi.domain.helpers.EmailValidator;
import br.com.thiagoodev.blogapi.domain.helpers.PhoneValidator;
import br.com.thiagoodev.blogapi.domain.helpers.UUIDValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private String uuid;
    private String name;
    private String username;
    private String password;
    private String email;
    private boolean isVerified;
    private String phone;
    private final List<UserPermission> permissions;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public User(
        String uuid,
        String name,
        String username,
        String password,
        String email,
        boolean isVerified,
        String phone,
        List<UserPermission> permissions,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
    ) {
        if(uuid == null || uuid.isEmpty()) {
            throw new InvalidUuidFormatException("UUID cannot be empty");
        }
        if(!UUIDValidator.isValidUUID(uuid)) {
            throw new InvalidUuidFormatException("UUID '" + uuid + "' is not a valid UUID format.");
        }
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if(username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if(password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if(email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if(!EmailValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Email '" + email + "' is not a valid email.");
        }
        if(phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be empty");
        }
        if(!PhoneValidator.isValidPhoneNumber(phone)) {
            throw new IllegalArgumentException("Phone '" + phone + "' is not a valid phone number.");
        }
        if(permissions == null || permissions.isEmpty()) {
            throw new IllegalArgumentException("Permissions cannot be empty");
        }
        if(createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }

        this.uuid = uuid;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isVerified = isVerified;
        this.phone = phone;
        this.permissions = new ArrayList<>(permissions);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public User(
        String name,
        String username,
        String password,
        String email,
        String phone
    ) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if(username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if(password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if(email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if(!EmailValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Email '" + email + "' is not a valid email.");
        }
        if(phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be empty");
        }
        if(!PhoneValidator.isValidPhoneNumber(phone)) {
            throw new IllegalArgumentException("Phone '" + phone + "' is not a valid phone number.");
        }

        this.uuid = null;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isVerified = false;
        this.phone = phone;
        this.permissions = new ArrayList<>(List.of(UserPermission.USER));
        this.createdAt = LocalDateTime.now();
    }

    public String getUuid() { return this.uuid; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }
    public boolean getIsVerified() { return this.isVerified; }
    public void setIsVerified(boolean isVerified) { this.isVerified = isVerified; }
    public String getPhone() { return this.phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public List<UserPermission> getPermissions() { return Collections.unmodifiableList(this.permissions); }
    public LocalDateTime getCreatedAt() { return this.createdAt; }
    public LocalDateTime getUpdatedAt() { return this.updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public LocalDateTime getDeletedAt() { return this.deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
    public String getEmail() { return this.email; }

    public void setEmail(String email) {
        setIsVerified(email.equals(this.email));
        this.email = email;
    }

    public void addPermission(UserPermission permission) {
        if(!this.permissions.contains(permission)) {
            this.permissions.add(permission);
        }
    }

    public void removePermission(UserPermission permission) {
        this.permissions.remove(permission);
    }

    public boolean isAdmin() {
        return this.permissions.contains(UserPermission.ADMIN);
    }

    public boolean isModerator() {
        return this.permissions.contains(UserPermission.MODERATOR);
    }
}