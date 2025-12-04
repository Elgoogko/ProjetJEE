package com.example.TP4.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CtrHello {

    private List<String> list = new ArrayList<String>();
    @Autowired
    private JdbcTemplate jdbc;

    @RequestMapping("/")
    public String index() {return "index";}

    @RequestMapping("/affiche")
    public String affiche(Model model, @RequestParam(name="nom") String nom) {
        nom = nom.toUpperCase();
        list.add(nom);
        model.addAttribute("nom", nom);
        model.addAttribute("list", list);
        System.out.println(nom);
        return "hello";}

    @RequestMapping("/getE")
    public String getAllUsers() {
        System.out.println(jdbc.queryForList("SELECT * FROM users"));
        return "index";
    }



}
