package com.example.apirestmovies;

public class Pelicula {

    private String imagen;
    private String titulo;
    private String genero;
    private String ano;

    public Pelicula(String titulo) {
        this.titulo = titulo;
    }

    public Pelicula(String titulo, String imagen) {
        this.titulo = titulo;
        this.imagen = imagen;
    }

    public Pelicula(String imagen, String titulo, String genero, String ano){
        this.imagen = imagen;
        this.titulo = titulo;
        this.genero = genero;
        this.ano = ano;
    }

    public String getImagen() { return imagen; }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getAno() {
        return ano;
    }
    public void setAno(String ano) {
        this.ano = ano;
    }
}
