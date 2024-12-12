package br.com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Long numeroDowloads;
    @ManyToOne
    private Autor autor;

    public Livro(){}

    public Livro(String titulo, String idioma, Long numeroDowloads) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.numeroDowloads = numeroDowloads;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getNumeroDowloads() {
        return numeroDowloads;
    }

    public void setNumeroDowloads(Long numeroDowloads) {
        this.numeroDowloads = numeroDowloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return  "\n" +
                "----- LIVRO -----\n" +
                "Titulo: " + titulo + "\n" +
                "Autor:  " + autor.getNome() + "\n"+
                "Idioma: " + idioma + "\n" +
                "Numero de Dowloads: " + numeroDowloads +
                "\n";


    }
}
