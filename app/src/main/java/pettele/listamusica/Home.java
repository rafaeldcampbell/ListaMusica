package pettele.listamusica;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import pettele.listamusica.DAO.Musica_DAO;
import pettele.listamusica.adapter.MusicaAdapter;
import pettele.listamusica.objetos.Musica;

/*
 * Classe associada a activity home, que inclui a lista das músicas
 * */

public class Home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MusicaAdapter musicaAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    private List<Musica> musicas;
    private Musica_DAO musica_dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        /*
        * Primeiro precisamos instanciar um objeto de Musica_DAO, para que tenhamos conexão com o banco de dados
        * Depois, chamamos o método que irá preencher nossa lista de músicas
        * */
        musica_dao = new Musica_DAO(this);
        /*
        * Para implementar a função de recarregar a página ao puxá-la para baixo
        * podemos usar o elemento SwipeRefreshLayout.
        * Primeiro, devemos colocá-lo no layout, depois conseguir uma referência para o elemento
        * e, por último, adicionar um Listener para tratar os eventos de recarregar
        * */
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.puxeParaRecarregar);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            /*
            * É o método chamado quando a tela é puxada para baixo
            * */
            @Override
            public void onRefresh() {
                atualizaRecycler();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        /*
         * Para popular a recyclerView, precisamos encontrá-la e passar o nosso MusicaAdapter,
         * que terá todas as informações para preencher a lista.
         * Também precisamos passar um gerenciador de layout, para que ela saiba como deve
         * exibir os elementos. Para esse exemplo, usaremos uma lista linear
         * */

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        populaListaMusicas();
        musicaAdapter = new MusicaAdapter(this.musicas, this);
        recyclerView.setAdapter(musicaAdapter);

    }

    /*
    * É responsável por construir o Array que contem as informações a serem exibidas
    * */
    private void populaListaMusicas() {
        this.musicas = musica_dao.lerTodos();
    }

    /*
    * É responsável por repopular a recyclerView
    * */
    public void atualizaRecycler(){
        populaListaMusicas();
        musicaAdapter = new MusicaAdapter(this.musicas, this);
        recyclerView.setAdapter(musicaAdapter);
        recyclerView.refreshDrawableState();
    }

    /*
    *
    * Será chamado todas as vezes que a tela estiver em foco*/
    @Override
    protected void onResume() {
        super.onResume();
        /*
         * Para popular a recyclerView, precisamos encontrá-la e passar o nosso MusicaAdapter,
         * que terá todas as informações para preencher a lista.
         * Também precisamos passar um gerenciador de layout, para que ela saiba como deve
         * exibir os elementos. Para esse exemplo, usaremos uma lista linear
         * */
        atualizaRecycler();
    }
}
