package app.service;

import app.model.NotificationPreference;
import app.repository.NotificationPreferenceRepository;
import app.web.dto.NotificationPreferenceRequest;
import app.web.dto.NotificationPreferenceResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static app.web.mapper.NotificationPreferenceMapper.toNotificationPreferenceResponse;

@Service
public class NotificationPreferenceService {

    private final NotificationPreferenceRepository preferenceRepository;

    public NotificationPreferenceService(NotificationPreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public NotificationPreferenceResponse upsert(NotificationPreferenceRequest request) {

        Optional<NotificationPreference> optionalNotificationPreference = preferenceRepository
                .findById(UUID.fromString(request.getUserId()));

        // Update the preference if it exists
        if (optionalNotificationPreference.isPresent()) {
            NotificationPreference notificationPreference = optionalNotificationPreference.get();
            notificationPreference.setEnabled(request.isNotificationEnabled());
            notificationPreference.setContactInfo(request.getContactInfo());
            notificationPreference.setUpdatedOn(java.time.LocalDateTime.now());
            NotificationPreference updatedPreference = preferenceRepository.save(notificationPreference);

            return toNotificationPreferenceResponse(updatedPreference);

        }

        // Create a new preference if it doesn't exist
        NotificationPreference newPreference = NotificationPreference.builder()
                .userId(UUID.fromString(request.getUserId()))
                .enabled(request.isNotificationEnabled())
                .contactInfo(request.getContactInfo())
                .type(app.model.NotificationType.EMAIL)
                .createdOn(java.time.LocalDateTime.now())
                .updatedOn(java.time.LocalDateTime.now())
                .build();

        NotificationPreference savedPreference = preferenceRepository.save(newPreference);

        return toNotificationPreferenceResponse(savedPreference);
    }
}
