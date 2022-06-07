package com.lbs.curriculoandroid;

public class Curriculo {

    public String id, nome, idade, genero, linkedin, github;
    public String linguagens;

    public Curriculo() {

    }

    public Curriculo(String nome){
        this.nome = nome;
    }

    public Curriculo(String nome, String idade, String genero, String linkedin, String github,
                     String linguagens) {
        this.nome = nome;
        this.idade = idade;
        this.genero = genero;
        this.linkedin = linkedin;
        this.github = github;
        this.linguagens = linguagens;
    }

    public Curriculo(String id, String nome, String idade, String genero) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.genero = genero;
    }

    @Override
    public String toString() {
        return  "Nome: " + nome + '\n' + "Idade: " + idade +
                '\n' + "Gênero: " + genero + '\n' + "LinkedIn: " + linkedin + '\n' +
                "GitHub: " + github + '\n' + "Linguagens: " + linguagens;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public void setGenero(String genero) { this.genero = genero; }

    public String setGeneros(String genero) { this.genero = genero; return genero;}

    public String getLinkedin() { return linkedin; }

    public void setLinkedin(String linkedin) { this.linkedin = linkedin; }

    public String getGithub() { return github; }

    public void setGithub(String github) { this.github = github;  }

    public String getLinguagens() {
        return linguagens;
    }

    public void setLinguagens(String linguagens) {
        this.linguagens = linguagens;
    }

    public String setLinguagem(String linguagens) {
        this.linguagens = linguagens;
        return linguagens;
    }
}
