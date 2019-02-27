package pettele.listamusica.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pettele.listamusica.R;

public class ConexaoBD extends SQLiteOpenHelper {

    private static final String NOME_BD = "bd.db";
    private static final int VERSAO_BD = 1;
    private Context context;

    public ConexaoBD(Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(context.getString(R.string.criatabela)); //cria tabela
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
