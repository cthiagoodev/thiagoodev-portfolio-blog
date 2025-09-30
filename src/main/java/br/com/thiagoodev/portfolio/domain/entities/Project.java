package br.com.thiagoodev.portfolio.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "projects")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Project {
    @EqualsAndHashCode.Include
    @Id
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String fullName;
    @Column(nullable = false)
    private String htmlUrl;
    private String description;
    private String language;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "project_topics",
        uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "topic"}),
        joinColumns = @JoinColumn(name = "project_id")
    )
    @Column(name = "topic")
    private Set<String> topics;
    private String homepage;
    private LocalDateTime pushedAt;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
