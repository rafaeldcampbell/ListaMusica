package pettele.listamusica.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import pettele.listamusica.R;
import pettele.listamusica.objetos.Musica;

public class MusicaAdapter extends RecyclerView.Adapter<MusicaAdapter.musicaViewHolder> {

    private List<Musica> musicas;
    private Context contexto;

    public MusicaAdapter(List<Musica> musicas, Context contexto) {
        this.musicas = musicas;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public musicaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull musicaViewHolder musicaViewHolder, int i) {

    }


    @Override
    public int getItemCount() {
        return musicas.size();
    }

    class musicaViewHolder extends RecyclerView.ViewHolder{

        TextView musica;
        TextView album;
        TextView artista;


        public musicaViewHolder(@NonNull View itemView) {
            super(itemView);
            musica = (TextView) itemView.findViewById(R.id.titulo);
            album = (TextView) itemView.findViewById(R.id.album);
            artista = (TextView) itemView.findViewById(R.id.artista);
        }
    }
}
