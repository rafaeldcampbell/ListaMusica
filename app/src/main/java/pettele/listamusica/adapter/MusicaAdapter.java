package pettele.listamusica.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import pettele.listamusica.AdicionarMusica;
import pettele.listamusica.InfoMusica;
import pettele.listamusica.R;
import pettele.listamusica.config.Preferencias;
import pettele.listamusica.objetos.Musica;

public class MusicaAdapter extends RecyclerView.Adapter<MusicaAdapter.MusicaViewHolder> {

    private List<Musica> musicas;
    private Context contexto;

    /*
    * Método construtor, responsável por retornar um instância de MusicaAdapter, recebendo
    * um contexto e uma lista de elementos Musica como parâmetro
    * */
    public MusicaAdapter(List<Musica> musicas, Context contexto) {
        this.musicas = musicas;
        this.contexto = contexto;
    }

    /*
    * Responsável por retornar uma instância de MusicaViewHolder
    * associando-a com o layout criado para o elemento.
    * Recebe, também, a posição na lista, pois pode retornar um
    * layout diferente, permitindo retornar um layout diferente
    * para cada posição.
    * */
    @NonNull
    @Override
    public MusicaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int tipoView) {
        LayoutInflater li = LayoutInflater.from(contexto);
        View view;
        if(tipoView == 0){
            view = li.inflate(R.layout.layout_primeira_view, null);
        } else{
            view = li.inflate(R.layout.layout_view, null);
        }
        return new MusicaViewHolder(view, tipoView);
    }

    /*
    * Chamado para popular a lista, o método onBindViewHolder tem a função de
    * receber uma instância de musicaViewHolder e popula-la com as informações
    * da lista, referente a posição que recebe. Ou seja, será responsável por
    * popular cada elemento da RecyclerView com um dos objetos da lista
    * */
    @Override
    public void onBindViewHolder(@NonNull MusicaViewHolder musicaViewHolder, int posicao) {
        if(posicao == 0){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(contexto, R.array.ordenar, R.layout.layout_spinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            musicaViewHolder.spinner.setAdapter(adapter);
            /*
            * Seleciona o elemento que ficará exibido como selecionado no Spinner,
            * baseado na preferencia já salva
            * */
            switch (new Preferencias(contexto).recordar()){
                case "nome_musica":
                    musicaViewHolder.spinner.setSelection(0);
                    break;
                case "nome_album":
                    musicaViewHolder.spinner.setSelection(1);
                    break;
                case "nome_artista":
                    musicaViewHolder.spinner.setSelection(2);
                    break;
                default:
                    musicaViewHolder.spinner.setSelection(0);
                    break;
            }
            /*
             * Recebe os eventos de clique no Spinner e salva a informação relativa ao valor
             * escolhido como preferência, para serem clicados
             * */
            musicaViewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int posicao, long l) {
                    switch (posicao) {
                        case 0:
                            new Preferencias(contexto).Salvar(contexto.getString(R.string.colunaNomeMusica));
                            break;
                        case 1:
                            new Preferencias(contexto).Salvar(contexto.getString(R.string.colunaNomeAlbum));
                            break;
                        case 2:
                            new Preferencias(contexto).Salvar(contexto.getString(R.string.colunaNomeArtista));
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    //Não fazer nada
                }
            });
            musicaViewHolder.botao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contexto.startActivity(new Intent(contexto, AdicionarMusica.class));
                }
            });
        }else{
            final Musica musica = musicas.get(posicao-1);
            musicaViewHolder.musica.setText(musica.getNome_musica());
            musicaViewHolder.album.setText("Álbum: "+musica.getNome_album());
            musicaViewHolder.artista.setText("Artista(s): "+musica.getNome_artista());

            /*
            * Estamos dando uma atividade para o clique em um dos elementos da lista.
            * Quando o usuário clicar no elemento, abrirá uma página com as informações relacionadas
            * */
            musicaViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(contexto, InfoMusica.class);
                    /*
                    * Para que a página possa identificar qual o elemento, devemos passar essa informação
                    * para a intent; assim poderemos recuperá-la dentro da activity de informações
                    * */
                    it.putExtra(contexto.getString(R.string.colunaNomeMusica), musica.getNome_musica());
                    it.putExtra(contexto.getString(R.string.colunaNomeAlbum), musica.getNome_album());
                    it.putExtra(contexto.getString(R.string.colunaNomeArtista), musica.getNome_artista());
                    contexto.startActivity(it);
                }
            });
        }

        /*
        *
        * */
        musicaViewHolder.itemView.setLayoutParams(new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    /*
    * É responsável por retornar o tipo de View a depender da posição.
    * Sobreescrevendo-o, deve retornar 0 caso seja a primeira posição e 1, caso contrário.
    * Dessa forma, as demais funções que recebem essa informação saberão qual view devem carregar
    * */
    @Override
    public int getItemViewType(int posicao) {
        if(posicao == 0){
            return 0;
        } else{
            return 1;
        }
    }

    public void alteraLista(List<Musica> musicas){
        this.musicas = musicas;
    }

    /*
    * Responsável por retornar o número de elementos que a RecyclerView terá.
    */
    @Override
    public int getItemCount() {
        return musicas.size()+1;
    }

    /*
    * Responsável por fazer a comunicação entre uma view e seus elementos; para isso,
    * recebe como parâmetros uma view e associa os elementos da view aos seus atributos,
    * permitindo que sejam mais facilmente acessados.
    * */
    class MusicaViewHolder extends RecyclerView.ViewHolder{

        TextView musica;
        TextView album;
        TextView artista;
        ImageButton botao;
        Spinner spinner;

        /*
        * Método construtor, responsável por retornar uma instância de MusicaViewHolder,
        * mas sem estar associado a uma View em específico
        * */

        public MusicaViewHolder(@NonNull View itemView, int tipoView) {
            super(itemView);
            /*
            * Caso seja a primeira posição, deve retornar os elementos da View
            * superior, que inclui o botão de adicionar e o spinner para ordenar.
            * Caso não seja a primeira, retorna os atributos padrões, que são as
            * informações a serem exibidas. A ideia é evitar que se tente buscar um
            * elemento que não pertença à view em questão
            * */
            if(tipoView == 0){
                botao = (ImageButton) itemView.findViewById(R.id.button);
                spinner = (Spinner) itemView.findViewById(R.id.spinner);
            }else{
                musica = (TextView) itemView.findViewById(R.id.titulo);
                album = (TextView) itemView.findViewById(R.id.album);
                artista = (TextView) itemView.findViewById(R.id.artista);
            }
        }
    }
}
