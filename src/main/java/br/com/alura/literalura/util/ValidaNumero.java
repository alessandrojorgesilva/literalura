package br.com.alura.literalura.util;

public class ValidaNumero {
    public static boolean isNumero(String num){
        try {
            Integer.parseInt(num);

            if(Integer.parseInt(num) >= 1 && Integer.parseInt(num) <= 15){
                return true;
            }

        }catch (NumberFormatException e){
            return false;
        }
        return false;
    }
}
