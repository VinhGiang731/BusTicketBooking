package com.project.busticket.dto.request.busoperator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusOperatorRequest {
    @NotNull(message = "NOT_NULL")
    String busOperatorName;

    @NotBlank(message = "NOT_BLANK")
    @Pattern(regexp = "^0[0-9]{9}$", message = "PHONE_INCORRECT")
    String contactPhone;

    @NotBlank(message = "NOT_BLANK")
    String imgUrl;
}
