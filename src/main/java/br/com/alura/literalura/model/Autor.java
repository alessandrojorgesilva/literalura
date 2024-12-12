package br.com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private Long anoNascimento;
    private Long anoFalecimento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {}

    public Autor(DadosAutor dados) {
        this.nome = dados.nome();
        this.anoNascimento = dados.anoNascimento();
        this.anoFalecimento = dados.anoFalescimento();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Long anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Long getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Long anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {

        String livrosAutor = livros.stream()
                .map(Livro::getTitulo)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Nenhuma obra cadastrada");


        return  "\n" +
                "Autor: " + nome + "\n" +
                "Ano de nascimento: " + anoNascimento + "\n" +
                "Ano falecimento: " + anoFalecimento +  "\n" +
                "Livros: " + livrosAutor +
                "\n";
    }
}