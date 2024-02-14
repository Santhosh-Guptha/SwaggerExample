package com.swagger.service;

import com.swagger.entities.Contact;
import com.swagger.repositories.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    private ContactRepo contactRepo;

    public Contact saveContact(Contact contact){
        contactRepo.save(contact);
        return contact;
    }

    public List<Contact> getAllContacts() {
        return contactRepo.findAll();
    }

    public Contact getContactById(String id) {
        return contactRepo.findById(id).get();
    }
}
