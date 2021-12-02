package com.proiect.controllers;

import com.proiect.entities.Role;
import com.proiect.repositories.RoleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository=roleRepository;
    }

    @GetMapping("/")
    public Iterable<Role> getRoles() { return roleRepository.findAll(); }
}
