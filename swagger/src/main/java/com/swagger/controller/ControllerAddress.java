package com.swagger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.swagger.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.swagger.entities.Contact;

@RestController
public class ControllerAddress {

	@Autowired
	private ContactService contactService;


	
	@GetMapping("/{id}")
	public Contact getContact(@PathVariable String id) {
		return contactService.getContactById(id);
	}
	
	@GetMapping("/")
	public List<Contact> getAllContacts(){
		return contactService.getAllContacts();
	}
	
	@PostMapping("/")
	public Contact save(@RequestBody Contact contact) {
		contactService.saveContact(contact);
		return contact;
	}
}
