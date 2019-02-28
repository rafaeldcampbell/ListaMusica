package pettele.listamusica;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import pettele.listamusica.DAO.Musica_DAO;
import pettele.listamusica.objetos.Musica;

public class InfoMusica extends AppCompatActivity {

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
        setContentView(R.layout.activity_info_musica);

        /*
        * Recebendo os valores que foram passados pela intent
        * */
        musicaTexto = getIntent().getStringExtra(getString(R.string.colunaNomeMusica));
        albumTexto = getIntent().getStringExtra(getString(R.string.colunaNomeAlbum));
        artistaTexto = getIntent().getStringExtra(getString(R.string.colunaNomeArtista));

        musicaView = (TextView) findViewById(R.id.infoMusica);
        albumView = (TextView) findViewById(R.id.infoAlbum);
        artistaView = (TextView) findViewById(R.id.infoArtista);

        /*
        * Carregando os valores em tela
        * */
        musicaView.setText(musicaTexto);
        albumView.setText(albumTexto);
        artistaView.setText(artistaTexto);

        musica_dao = new Musica_DAO(this);
    }

    public void Atualizar(View view) {
        /*
        * Chama a tela de atualização, passando as informações originais
        * */
        Intent it = new Intent(this, AtualizarMusica.class);
        it.putExtra(getString(R.string.colunaNomeMusica), musicaTexto);
        it.putExtra(getString(R.string.colunaNomeAlbum), albumTexto);
        it.putExtra(getString(R.string.colunaNomeArtista), artistaTexto);
        startActivityForResult(it, 1);
    }

    @Override
    protected void onActivityResult(int requisicao, int resultado, @Nullable Intent info) {
        /*
        * Atualizando as informações da tela depois do usuário ter atualizado no cadastro
        * resultado recebe (1) se foi alterado e (0) se não for
        * */
        if(resultado == 1) {
            musicaTexto = info.getStringExtra(getString(R.string.colunaNomeMusica));
            albumTexto = info.getStringExtra(getString(R.string.colunaNomeAlbum));
            artistaTexto = info.getStringExtra(getString(R.string.colunaNomeArtista));
            musicaView.setText(musicaTexto);
            albumView.setText(albumTexto);
            artistaView.setText(artistaTexto);
        }
    }

    public void Deletar(View view) {
        /*
        * Primeiro, deve criar uma janela de diálogo para verificar se o usuário realmente deseja cancelar
        * Caso o usuário queira continuar, chama o método Delete, do contrário, só fecha a janela
        * */
        AlertDialog alerta;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tem certeza?");
        builder.setMessage("Você está prestes a deletar esse título!");
        builder.setPositiveButton("Quero continuar!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                musica_dao.delete(new Musica(musicaTexto, albumTexto, artistaTexto));
                finish();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Não deve fazer nada além de fechar a caixa
            }
        });
        alerta = builder.create();
        alerta.show();
    }
}
