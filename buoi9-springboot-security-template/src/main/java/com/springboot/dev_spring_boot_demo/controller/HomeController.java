package com.springboot.dev_spring_boot_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/default")
    public String defaultPage() {
        return "default/layout";
    }
}
