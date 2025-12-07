package com.msclient.EntityDataBase;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")  // nom de la table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String pseudo;

    @Column(nullable = false)
    private String mdp;

    // --- Constructeurs ---
    public User() {}

    public User(String pseudo, String mdp) {
        this.pseudo = pseudo;
        this.mdp = mdp;
    }

    // --- Getters / Setters ---
    public Long getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}