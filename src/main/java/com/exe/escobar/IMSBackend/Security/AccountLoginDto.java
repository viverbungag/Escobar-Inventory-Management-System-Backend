package com.exe.escobar.IMSBackend.Security;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountLoginDto {

    private String accountUsername;
    private String accountPassword;
}
