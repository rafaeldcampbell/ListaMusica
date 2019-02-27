package pettele.listamusica.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pettele.listamusica.config.ConexaoBD;
import pettele.listamusica.R;
import pettele.listamusica.objetos.Musica;

public class Musica_DAO {

    private ConexaoBD conexao;
    private Context contexto;

    public Musica_DAO(Context contexto) {
        conexao = new ConexaoBD(contexto);
        this.contexto = contexto;
    }

    public long inserir(Musica musica){
        SQLiteDatabase bd = conexao.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(contexto.getString(R.string.colunaNomeMusica), musica.getNome_musica());
        valores.put(contexto.getString(R.string.colunaNomeAlbum), musica.getNome_album());
        valores.put(contexto.getString(R.string.colunaNomeArtista), musica.getNome_artista());
        return bd.insert(contexto.getString(R.string.nomeTabela), null, valores);
    }

    public List<Musica> lerTodos(){
        SQLiteDatabase bd = conexao.getReadableDatabase();
        List<Musica> musicas = new ArrayList<>();
        String[] projecao = {contexto.getString(R.string.colunaNomeMusica), contexto.getString(R.string.colunaNomeAlbum), contexto.getString(R.string.colunaNomeArtista)};
        Cursor cursor = bd.query(contexto.getString(R.string.nomeTabela), projecao, null, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do{
                String mus = cursor.getString(0);
                String album = cursor.getString(1);
                String artista = cursor.getString(2);
                musicas.add(new Musica(mus, album, artista));
            }while(cursor.moveToNext());
        }
        return musicas;
    }

    public long atualizar(Musica atual, Musica anterior){
        SQLiteDatabase bd = conexao.getWritableDatabase();
        ContentValues valores = new ContentValues();
        if(atual.getNome_musica().compareTo("") != 0 && atual.getNome_musica().compareTo(anterior.getNome_musica()) != 0){
            valores.put(contexto.getString(R.string.colunaNomeMusica), atual.getNome_musica());
        }
        if(atual.getNome_album().compareTo("") != 0 && atual.getNome_album().compareTo(anterior.getNome_album()) != 0){
            valores.put(contexto.getString(R.string.colunaNomeAlbum), atual.getNome_album());
        }
        if(atual.getNome_artista().compareTo("") != 0 && atual.getNome_artista().compareTo(anterior.getNome_artista()) != 0){
            valores.put(contexto.getString(R.string.colunaNomeArtista), atual.getNome_artista());
        }
        String selecao = contexto.getString(R.string.colunaNomeMusica)+" = ? AND "+contexto.getString(R.string.colunaNomeAlbum)+" = ?";
        String[] selecaoArgs = {anterior.getNome_musica(), anterior.getNome_album()};
        return bd.update(contexto.getString(R.string.nomeTabela), valores, selecao, selecaoArgs);
    }

    public long delete(Musica musica){
        SQLiteDatabase bd = conexao.getWritableDatabase();
        String selecao = contexto.getString(R.string.colunaNomeMusica)+" = ? AND "+contexto.getString(R.string.colunaNomeAlbum)+" = ?";
        String[] selecaoArgs = {musica.getNome_musica(), musica.getNome_album()};
        return bd.delete(contexto.getString(R.string.nomeTabela), selecao, selecaoArgs);
    }

}
