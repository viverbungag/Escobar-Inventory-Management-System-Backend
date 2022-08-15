package com.exe.escobar.IMSBackend.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/login")
public class SecurityController {

    @Autowired
    SecurityService securityService;

    @PostMapping
    public void login(@RequestBody AccountLoginDto accountLoginDto){
        securityService.login(accountLoginDto);
    }
}
