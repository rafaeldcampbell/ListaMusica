package pettele.listamusica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import pettele.listamusica.DAO.Musica_DAO;
import pettele.listamusica.objetos.Musica;

public class AtualizarMusica extends AppCompatActivity {

    private TextView musicaView;
    private TextView albumView;
    private TextView artistaView;

    private String musicaTexto;
    private String albumTexto;
    private String artistaTexto;

    private Musica_DAO musica_dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_musica);

        /*
         * Recebendo os valores que foram passados pela intent
         * */
        musicaTexto = getIntent().getStringExtra(getString(R.string.colunaNomeMusica));
        albumTexto = getIntent().getStringExtra(getString(R.string.colunaNomeAlbum));
        artistaTexto = getIntent().getStringExtra(getString(R.string.colunaNomeArtista));

        musicaView = (TextView) findViewById(R.id.atualizar_musica_musica);
        albumView = (TextView) findViewById(R.id.atualizar_musica_album);
        artistaView = (TextView) findViewById(R.id.atualizar_musica_artista);

        musicaView.setText(musicaTexto);
        albumView.setText(albumTexto);
        artistaView.setText(artistaTexto);

        musica_dao = new Musica_DAO(this);
    }

    public void Atualizar(View view) {
        String novaMusica = musicaView.getText().toString();
        String novoAlbum = albumView.getText().toString();
        String novoArtista = artistaView.getText().toString();
        Long num = musica_dao.atualizar(new Musica(novaMusica, novoAlbum, novoArtista), new Musica(musicaTexto, albumTexto, artistaTexto));
        Intent it = new Intent();
        /*
        * O método atualizar retorna o número de linhas atualizadas
        * Sendo o numero maior que zero, sabemos que alguma linha foi alterada e,
        * como as informações são chave, sabemos que correu tudo certo.
        * Logo, se atualizou, passamos as informações atualizadas para a Activity
        * Do contrário, passamos apenas o código 0 (não alterar)
        * */
        if(num > 0L){
            it.putExtra(getString(R.string.colunaNomeMusica), novaMusica);
            it.putExtra(getString(R.string.colunaNomeAlbum), novoAlbum);
            it.putExtra(getString(R.string.colunaNomeArtista), novoArtista);
            setResult(1, it);
        } else{
            setResult(0, it);
        }
        finish();
    }

    /*
    * Apenas retorna código 0 (não alterar)
    * */
    public void Cancelar(View view) {
        Intent it = new Intent();
        setResult(0, it);
        finish();
    }
}
