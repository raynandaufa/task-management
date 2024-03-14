package com.task.management.dto;

import com.task.management.validation.UniqueUsername;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {
    @NotEmpty
    @UniqueUsername
    private String username;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
    @NotEmpty
    private String division;
}
