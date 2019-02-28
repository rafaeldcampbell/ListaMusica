package pettele.listamusica.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pettele.listamusica.R;

/*
* Responsável por criar uma conexão com o banco de dados
* */
public class ConexaoBD extends SQLiteOpenHelper {

    private static final String NOME_BD = "bd.db";
    private static final int VERSAO_BD = 1;
    private Context context;

    /*
    * Retorna uma instância de ConexaoBD,
    * que se comunica com um banco de dados
    * com nome "bd.db"
    * */
    public ConexaoBD(Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
        this.context = context;
    }

    /*
    * É chamado quando o banco de dados é criado pela primeira vez
    * e será responsável por criar as tabelas
    * */
    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(context.getString(R.string.criatabela)); //cria tabela
    }

    /*
    * É chamado quando se atualiza a aplicação,
    * mantendo o banco de dados.
    * */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
