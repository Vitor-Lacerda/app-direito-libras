package com.domain.vitor.estudos1;

/**
 * Objeto do thumbnail e com as informacoes para reproduzir o video
 */

public class VideoThumb {

    private String nomeVideo;
    private int idDrawableThumb;
    private String codigoYoutube;
    private String textoVideo;
    private String[] palavrasChave;

    public VideoThumb(String _nomeVideo, int _drawableThumb, String _codigo, String _texto, String[] _palavrasChave){
        nomeVideo = _nomeVideo;
        idDrawableThumb = _drawableThumb;
        codigoYoutube = _codigo;
        textoVideo = _texto;
        palavrasChave = _palavrasChave;

    }


    public String getNomeVideo() {
        return nomeVideo;
    }

    public void setNomeVideo(String nomeVideo) {
        this.nomeVideo = nomeVideo;
    }

    public int getIdDrawableThumb() {
        return idDrawableThumb;
    }

    public void setIdDrawableThumb(int idDrawableThumb) {
        this.idDrawableThumb = idDrawableThumb;
    }

    public String getCodigoYoutube() {
        return codigoYoutube;
    }

    public void setCodigoYoutube(String codigoYoutube) {
        this.codigoYoutube = codigoYoutube;
    }

    public String getTextoVideo() {
        return textoVideo;
    }

    public void setTextoVideo(String textoVideo) {
        this.textoVideo = textoVideo;
    }

    public String[] getPalavrasChave() {
        return palavrasChave;
    }

    public void setPalavrasChave(String[] palavrasChave) {
        this.palavrasChave = palavrasChave;
    }




}
