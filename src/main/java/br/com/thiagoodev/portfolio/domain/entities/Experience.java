package br.com.thiagoodev.portfolio.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "experiences")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Experience {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(nullable = false)
    private String position;
    private String description;
    @Column(nullable = false)
    private String company;
    @Column(nullable = false)
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
