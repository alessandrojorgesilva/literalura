package br.com.alura.literalura.principal;

import br.com.alura.literalura.controller.LivroController;
import br.com.alura.literalura.model.*;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConverterDados;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private LivroController controller;
    private ConsumoApi api = new ConsumoApi();
    private final String URL = "https://gutendex.com/books/?search=";
    private final ObjectMapper mapper = new ObjectMapper();
    private ConverterDados converte = new ConverterDados();
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    public Principal() {}

    public Principal(LivroController controller, AutorRepository autorRepository, LivroRepository livroRepository) {
        this.controller = controller;
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    public void exibirMenu() {

        var opcao = -1;

        while(opcao != 0){
            var menu = """                
                1-  Buscar livro pelo titulo
                2-  Listar livros registrados
                3-  Listar autores registrados
                4-  Listar autores vivos em um determinado ano
                5-  Listar livros em um determinado idioma
                0 - Sair   
                
                Escolha uma opção:                              
                """;

            System.out.println(menu);
            opcao= leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    BuscarAutoresVivos();

                    break;
                case 5:
                    buscarLivroPorIdioma();
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        Livro livro = null;
        Autor autor;
        Optional<Autor> autorRegistrado;
        System.out.println("Insira o nome do livro que deseja procurar:");
        var nomeLivro = leitura.nextLine();
        var endereco = URL + nomeLivro.replace(" ", "+");
        var json = controller.buscarLivroPorTitulo(endereco);
        Gutendex gutendex = (converte.obterDados(json, Gutendex.class));

        if(!gutendex.dadosLivros().isEmpty()){
            for(DadosLivro dadosLivro: gutendex.dadosLivros()){
                if(dadosLivro.titulo().equalsIgnoreCase(nomeLivro)){
                    if(!livroRepository.findByTituloIgnoreCase(dadosLivro.titulo()).isPresent()){

                        Autor a = new Autor(dadosLivro.autores().get(0));
                        autorRegistrado = autorRepository.findByNomeAndAnoNascimentoAndAnoFalecimento(a.getNome(), a.getAnoNascimento(), a.getAnoFalecimento());

                        if(!autorRegistrado.isPresent()){

                            autor = new Autor(dadosLivro.autores().get(0));
                            Autor autorPersisitido = this.autorRepository.save(autor);

                            livro = new Livro(dadosLivro.titulo(), dadosLivro.idiomas().get(0), dadosLivro.numeroDownload());
                            livro.setAutor(autorPersisitido);

                            List<Livro> livros = new ArrayList<>();
                            livros.add(livro);
                            autor.setLivros(livros);
                            autorRepository.save(autor);
                            break;
                        } else {
                            livro = new Livro(dadosLivro.titulo(), dadosLivro.idiomas().get(0), dadosLivro.numeroDownload());
                            livro.setAutor(autorRegistrado.get());
                            livroRepository.save(livro);
                            break;
                        }
                    } else {
                        livro = livroRepository.findByTituloIgnoreCase(nomeLivro).get();
                        System.out.println("Livro já foi cadastrado na base dados");
                    }

                }
            }
        }else {
            System.out.println("Nenhum livro encontrado. Verifique se digitou o nome correto!");
        }

        System.out.println(livro);
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = livroRepository.findAll();
        livros.stream().forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        autores.stream().forEach(System.out::println);
    }

    private void BuscarAutoresVivos() {
        System.out.println("Informe o ano que deseja consultar:");
        var ano = leitura.nextInt();
        List<Autor> autores = autorRepository.AutoresVivosEmUmAno(ano);
        if(!autores.isEmpty()){
            autores.stream().forEach(System.out::println);
        }else{
            System.out.println("Nenhum resulta foi encontrado");
        }

    }

    private void buscarLivroPorIdioma() {
        System.out.println("\n");
        System.out.println("----------------------------");
        System.out.println("Insira o idioma para fazer a busca:");
        for(Idioma i: Idioma.values()){
            System.out.println(i.getIdioma());
        }
        System.out.println("----------------------------");
        System.out.println("\n");

        var sigla = leitura.nextLine();

        Optional<List<Livro>> livros = livroRepository.findByIdiomaIgnoreCase(sigla);
          if (!livros.get().isEmpty()){
              for(Livro livro: livros.get()){
                  System.out.println(livro);
              }
        }else{
            System.out.println("Não existem livros nesse idiona no banco de dados");
        }



    }
}
