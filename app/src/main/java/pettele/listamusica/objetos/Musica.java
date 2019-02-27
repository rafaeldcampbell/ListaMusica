package pettele.listamusica.objetos;

public class Musica {

    private String nome_musica;
    private String nome_album;
    private String nome_artista;

    public Musica(String nome_musica, String nome_album, String nome_artista) {
        this.nome_musica = nome_musica;
        this.nome_album = nome_album;
        this.nome_artista = nome_artista;
    }

    public String getNome_musica() {
        return nome_musica;
    }

    public void setNome_musica(String nome_musica) {
        this.nome_musica = nome_musica;
    }

    public String getNome_album() {
        return nome_album;
    }

    public void setNome_album(String nome_album) {
        this.nome_album = nome_album;
    }

    public String getNome_artista() {
        return nome_artista;
    }

    public void setNome_artista(String nome_artista) {
        this.nome_artista = nome_artista;
    }

}
