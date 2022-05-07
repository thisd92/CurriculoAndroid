package com.lbs.curriculoandroid;

public class Curriculo {

    public int id;
    public String nome, idade, genero, linkedin, github;

    public Curriculo() {

    }

    public Curriculo(String nome, String idade, String genero, String linkedin, String github) {
        this.nome = nome;
        this.idade = idade;
        this.genero = genero;
        this.linkedin = linkedin;
        this.github = github;
    }

    public Curriculo(int id, String nome, String idade, String genero) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.genero = genero;
    }

    @Override
    public String toString() {
        return  nome + '\n' + idade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String titulo) {
        this.nome = titulo;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getLinkedin() { return linkedin; }

    public void setLinkedin(String linkedin) { this.linkedin = linkedin; }

    public String getGithub() { return github; }

    public void setGithub(String github) { this.github = github;  }
}
