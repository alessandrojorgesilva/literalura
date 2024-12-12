package br.com.alura.literalura.model;

public enum Idioma {
    ES("es - Espanhol"),
    FR("fr - Francês"),
    EN("en - Inglês"),
    IT("it - Italiano"),
    JA("ja - Japonês"),
    PL("pl - Polonês"),
    PT("pt - Português"),
    RU("ru - Russo"),
    ZH("zh - Mandarim");


    private String idioma;

    Idioma(String idioma){
        this.idioma = idioma;
    }

    public String getIdioma(){
       return this.idioma;
    }
}
