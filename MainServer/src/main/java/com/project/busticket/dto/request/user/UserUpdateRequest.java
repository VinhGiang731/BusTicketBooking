package com.project.busticket.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String firstName;
    String lastName;

    @NotBlank(message = "NOT_BLANK")
    @Email(message = "EMAIL_INCORRECT")
    String email;

    @NotBlank(message = "NOT_BLANK")
    @Pattern(regexp = "^0[0-9]{9}$", message = "PHONE_INCORRECT")
    String phone;
}
