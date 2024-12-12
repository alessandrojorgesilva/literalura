package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNome(String nome);
    Optional<Autor> findByNomeAndAnoNascimentoAndAnoFalecimento(String nome, Long anoNascimento, Long anoFalescimento);

    @Query("SELECT autor From Autor autor where :ano >= autor.anoNascimento AND :ano <= autor.anoFalecimento")
    List<Autor> AutoresVivosEmUmAno(int ano);

}