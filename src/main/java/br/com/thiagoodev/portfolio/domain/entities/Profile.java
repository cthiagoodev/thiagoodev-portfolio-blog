package br.com.thiagoodev.portfolio.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "profile")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Profile {
    @EqualsAndHashCode.Include
    @Id
    private UUID uuid;
    private String curriculum;
    private String profileImage;
    private String linkedinUrl;
    private String githubUrl;
    private String xUrl;
    private String communityUrl;
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
