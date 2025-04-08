package com.example.controledehabitos;


public class Habits {
    private int id;
    private String nome;
    private String descricao;
    private boolean feitoHoje;

    public Habits(int id, String nome, String descricao, boolean feitoHoje) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.feitoHoje = feitoHoje;
    }

    public Habits(String nome, String descricao, boolean feitoHoje) {
        this.nome = nome;
        this.descricao = descricao;
        this.feitoHoje = feitoHoje;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public boolean isFeitoHoje() { return feitoHoje; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setFeitoHoje(boolean feitoHoje) { this.feitoHoje = feitoHoje; }
}
