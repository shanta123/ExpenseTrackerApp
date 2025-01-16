package authservice.model;

import authservice.entities.UserInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto extends UserInfo {

    private String firstName;

    private String lastName;

    private Long phoneNumber;

    private String email;
}
