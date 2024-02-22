package com.fusionsauth.controller;

import com.fusionsauth.dto.AuthReq;

import com.fusionsauth.dto.JwtResponse;
import com.fusionsauth.dto.RefreshTokenRequest;
import com.fusionsauth.entities.RefreshToken;
import com.fusionsauth.entities.UserDetailsManager;
import com.fusionsauth.securityservicr.JwtService;
import com.fusionsauth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD Rest APIs for the USER Resource",
        description = "All the APIs will perform CRUD operations and some APIs will perform for authentication and some are for RefreshToken"
)
@RestController
@RequestMapping("/home")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;



    @Operation(
            summary = "Create User Rest Api",
            description = "This API will create a user by the given json file"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status code 201 Created."
    )
    @PostMapping("/save")
    public ResponseEntity<UserDetailsManager> saveUser(@Valid @RequestBody UserDetailsManager userDetails) throws Exception {
        return new ResponseEntity<>(userService.saveUser(userDetails), HttpStatus.CREATED);
    }

    @Operation(
            summary = "GetAll Users Rest Api",
            description = "This API will fetch all the users from the database and shows in a json format."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status code 200 OK."
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDetailsManager>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/getById")
    public ResponseEntity<UserDetailsManager> getUserById(@RequestParam int id) throws Exception {
        return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
    }

    @PutMapping("/updateById")
    public ResponseEntity<UserDetailsManager> updateUserById(@RequestParam int id,@RequestBody UserDetailsManager userDetails){
        return new ResponseEntity<>(userService.updateUserDetails(id,userDetails),HttpStatus.OK);
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<UserDetailsManager> deleteUserById(@RequestParam int id){
        return new ResponseEntity<>(userService.deleteUserById(id),HttpStatus.OK);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam String oldPassword ,@RequestParam String newPassword) throws Exception {
        return new ResponseEntity<>(userService.changePassword(oldPassword,newPassword),HttpStatus.ACCEPTED);
    }


    @PostMapping("auth")
    public JwtResponse authenticateToken(@RequestBody AuthReq req){
        if(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUserName(),req.getPassword())).isAuthenticated()) {
            RefreshToken refreshToken = jwtService.createRefreshToken(req.getUserName());
            return JwtResponse.builder()
                    .accessToken(jwtService.generateToken(req.getUserName()))
                    .token(refreshToken.getToken())
                    .build();
        }
        else {
            throw new UsernameNotFoundException("Invalid Username or Password!..");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequestDTO){
        return jwtService.findByToken(refreshTokenRequestDTO.getToken())
                .map(jwtService::verifyExpiration)
                .map(RefreshToken::getUserDetailsManager)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getUserName());
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }




}
