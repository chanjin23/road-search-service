package com.didim.project1.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
    private String currentPassword;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,20}$",
            message = "비밀번호는 8자 이상 20자 이하여야 하며, 알파벳, 숫자, 특수문자를 포함해야 합니다."
    )
    private String newPassword;
}
