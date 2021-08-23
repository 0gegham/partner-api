package com.sparkers.partner.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDto {
    private Long id;

    @NotBlank(message = "The name of the partner is required")
    private String name;

    @NotBlank(message = "The reference of the partner is required")
    private String reference;

    @NotBlank(message = "The local is required")
    private String locale;

    @NotNull(message = "Expiration time is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssxxx")
    private ZonedDateTime expirationTime;
}
