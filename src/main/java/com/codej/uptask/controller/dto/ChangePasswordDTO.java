package com.codej.uptask.controller.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {

    @NotBlank
    private  String password;
    @NotBlank
    private  String confirmPassword;
    @NotBlank
    private  String tokenPassword;

}
