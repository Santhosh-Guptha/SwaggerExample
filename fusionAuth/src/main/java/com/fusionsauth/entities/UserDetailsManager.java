package com.fusionsauth.entities;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.UUID;

@Schema(
        description = "User Manager Model Information"
)
@Data
@Entity
@Table(name = "Users_Manager")
public class UserDetailsManager {

    @Schema(
            description = "Id"
    )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Schema(
            description = "FirstName"
    )
    @NotBlank(message = "First name should not be blank")
    private String firstName;
    @NotEmpty(message = "Lastname should not be empty")
    private String lastName;
    @NotNull(message = "Username should not be null")
    private String userName;
    @Email(message = "Enter a valid email")
    private String email;
    @Size(min = 10, max = 10, message = "Phone number size should be 10")
    private String phoneNumber;
    @NotBlank(message = "Description should not be blank")
    private String description;
    private String password;
    private String role;


}