package br.com.thiagoodev.blogapi.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Permission implements GrantedAuthority {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(nullable = false, unique = true)
    private String authority;

    @Override
    public String getAuthority() { return this.authority; }

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_MODERATOR = "ROLE_MODERATOR";

    public static Permission userRole() {
        return Permission.builder().authority(ROLE_USER).build();
    }

    public static Permission adminRole() {
        return Permission.builder().authority(ROLE_ADMIN).build();
    }

    public static Permission moderatorRole() {
        return Permission.builder().authority(ROLE_MODERATOR).build();
    }
}
