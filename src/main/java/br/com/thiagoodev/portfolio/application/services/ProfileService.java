package br.com.thiagoodev.portfolio.application.services;

import br.com.thiagoodev.portfolio.domain.entities.Profile;
import br.com.thiagoodev.portfolio.infrastructure.persistence.repositories.ProfileRepository;
import br.com.thiagoodev.portfolio.infrastructure.services.storage.StorageService;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final StorageService storageService;

    public ProfileService(
            ProfileRepository profileRepository,
            StorageService storageService
    ) {
        this.profileRepository = profileRepository;
        this.storageService = storageService;
    }

    public Profile get() {
        return profileRepository
                .findFirstByOrderByCreatedAtDesc()
                .map(
                        profile -> profile
                                .toBuilder()
                                .curriculum(storageService.storageUrlResolver(profile.getCurriculum()))
                                .profileImage(storageService.storageUrlResolver(profile.getProfileImage()))
                                .build()
                )
                .orElse(new Profile());
    }
}
