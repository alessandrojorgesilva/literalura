package br.com.alura.literalura.controller;

import br.com.alura.literalura.service.ConsumoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Controller
public class LivroController {

    @Autowired
    private ConsumoApi service;

    public String buscarLivroPorTitulo(String endereco){
        return   service.obterDados(endereco);
    }
}
