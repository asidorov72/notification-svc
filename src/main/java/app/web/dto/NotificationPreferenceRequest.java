package app.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationPreferenceRequest {
   /*
    {
      "userId": "550e8400-e29b-41d4-a716-446655440000",
      "isNotificationEnabled": true,
      "contactInfo": "user@example.com"
    }
    */
    private String userId;
    private boolean notificationEnabled;
    private String contactInfo;
}
