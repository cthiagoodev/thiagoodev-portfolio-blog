package br.com.thiagoodev.blogapi.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private final String uuid;
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
            throw new IllegalArgumentException("UUID cannot be empty");
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
        if(phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be empty");
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
        this.email = email;
        setIsVerified(false);
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