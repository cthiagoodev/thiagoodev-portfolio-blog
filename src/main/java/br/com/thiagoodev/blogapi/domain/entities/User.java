package br.com.thiagoodev.blogapi.domain.entities;

import br.com.thiagoodev.blogapi.domain.exceptions.UserAlreadyEnabledException;
import br.com.thiagoodev.blogapi.domain.exceptions.UserNotEnabledException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements UserDetails {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private UUID uuid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private boolean isVerified;
    @Column(unique = true)
    private String phone;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_permissions",
        joinColumns = @JoinColumn(name = "user_uuid"),
        inverseJoinColumns = @JoinColumn(name = "permission_uuid")
    )
    private Set<Permission> permissions = new HashSet<>();
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Override
    public String getUsername() { return email; }
    @Override
    public String getPassword() { return password; }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return this.permissions; }
    @Override
    public boolean isEnabled() { return this.isVerified && deletedAt == null; }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
        this.isVerified = false;
    }

    public void verify() {
        if (deletedAt != null) throw new UserNotEnabledException("User already deleted");
        if (isVerified) throw new UserAlreadyEnabledException("User already verified");
        this.isVerified = true;
    }
}