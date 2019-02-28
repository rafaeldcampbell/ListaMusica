package pettele.listamusica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pettele.listamusica.DAO.Musica_DAO;
import pettele.listamusica.objetos.Musica;

/*
* Classe responsável por adicionar novas músicas ao banco de dados
*
* */

public class AdicionarMusica extends AppCompatActivity {

    private EditText musica;
    private EditText album;
    private EditText artista;

    private Musica_DAO musica_dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_musica);

        musica = (EditText) findViewById(R.id.adicionar_musica_musica);
        album = (EditText) findViewById(R.id.adicionar_musica_album);
        artista = (EditText) findViewById(R.id.adicionar_musica_artista);
        musica_dao = new Musica_DAO(this);

    }

    public void Adicionar(View view) {
        String nome_musica = musica.getText().toString();
        String nome_album = album.getText().toString();
        String nome_artista = artista.getText().toString();

        /*
        * Verificando se os campos estão vazios; caso algum esteja, retorna um aviso
        * */
        if(!nome_musica.isEmpty() && !nome_album.isEmpty() && !nome_artista.isEmpty()){
            long id = musica_dao.inserir(new Musica(nome_musica, nome_album, nome_artista));

            /*
            * O método retornará um id, que corresponde a linha onde foi inserido o elemento.
            * Caso o id seja maior ou igual a zero, significa que foi inserido corretamente
            * Caso contrário, algum erro aconteceu. Como estamos tratando outros erros, o
            * que pode acontecer é tentar inserir um valor com mesmo nome de música e album,
            * que são as chaves do banco de dados; nesse caso, devemos informar que o valor
            * está repetido
            * */

            if(id>=0){
                /*
                * Se a inserção der certo, deve avisar o sucesso e fechar a tela
                * */
                Toast.makeText(this, "Adicionado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "Este título já foi adicionado", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this, "Preencha os campos corretamente", Toast.LENGTH_SHORT).show();
        }
    }
}
