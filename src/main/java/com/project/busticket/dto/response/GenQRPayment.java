package com.project.busticket.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GenQRPayment {
    String bin = "970405";
    String accountNo = "2008206211037";
    String accountName = "Đinh Vĩnh Giang";
    String amount;
    String content = "Thanks you so much!";
}
