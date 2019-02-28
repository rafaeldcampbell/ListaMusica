package pettele.listamusica.config;

import android.content.Context;
import android.content.SharedPreferences;

/*
* Guardará a informação sobre como o usuário prefere que seja organizada sua lista
* */

public class Preferencias {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String ARQUIVO = "preferencias";
    private static final String CHAVE_ORDENAR = "chave_para_ordenar";

    /*
    * Retorna uma instância de Preferência, já com conexão com o arquivo
    * */
    public Preferencias(Context contexto) {
        this.sharedPreferences = contexto.getSharedPreferences(ARQUIVO, contexto.MODE_PRIVATE);
        this.editor = this.sharedPreferences.edit();
    }

    /*
    * Salva o nome da coluna por onde se deseja ordenar
    * */
    public void Salvar(String nomeColuna){
        editor.putString(CHAVE_ORDENAR, nomeColuna);
        editor.apply();
    }

    /*
    * Retorna o valor que indica a coluna por onde se deseja ordenar
    * */
    public String recordar(){
        return sharedPreferences.getString(CHAVE_ORDENAR, "nome_musica");
    }
}
