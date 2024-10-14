package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import com.example.utils.DatabaseUtils
import groovy.sql.Sql