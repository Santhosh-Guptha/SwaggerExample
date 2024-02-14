package com.swagger.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "Contact_Manager")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	private String name;
	
	private String phone;
}
