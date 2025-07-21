package br.com.thiagoodev.blogapi.domain.entities;

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

    public User(
        String uuid,
        String name,
        String username,
        String password,
        String email,
        boolean isVerified,
        String phone,
        List<UserPermission> permissions
    ) {
        this.uuid = uuid;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isVerified = isVerified;
        this.phone = phone;
        this.permissions = permissions;

        if(this.uuid.isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be empty");
        }
        if(this.name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if(this.username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if(this.password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if(this.email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if(this.phone.isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be empty");
        }
        if(this.permissions.isEmpty()) {
            throw new IllegalArgumentException("Permissions cannot be empty");
        }
    }

    String getUuid() { return this.uuid; }
    String getName() { return this.name; }
    void setName(String name) { this.name = name; }
    String getUsername() { return this.username; }
    void setUsername(String username) { this.username = username; }
    String getPassword() { return this.password; }
    void setPassword(String password) { this.password = password; }
    boolean getIsVerified() { return this.isVerified; }
    void setIsVerified(boolean isVerified) { this.isVerified = isVerified; }
    String getPhone() { return this.phone; }
    void setPhone(String phone) { this.phone = phone; }
    List<UserPermission> getPermissions() { return this.permissions; }
    String getEmail() { return this.email; }

    void setEmail(String email) {
        this.email = email;
        setIsVerified(false);
    }

    void addPermission(UserPermission permission) {
        if(!this.permissions.contains(permission)) {
            this.permissions.add(permission);
        }
    }

    void removePermission(UserPermission permission) {
        this.permissions.remove(permission);
    }

    boolean isAdmin() {
        return this.permissions.contains(UserPermission.ADMIN);
    }

    boolean isModerator() {
        return this.permissions.contains(UserPermission.MODERATOR);
    }
}
