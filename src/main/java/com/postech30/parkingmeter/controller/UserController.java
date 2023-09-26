package com.postech30.parkingmeter.controller;

import jakarta.transaction.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@Validated
@Transactional
public class UserController {
}
