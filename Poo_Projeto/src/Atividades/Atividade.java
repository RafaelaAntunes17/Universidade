package Atividades;


import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Atividade implements Serializable{
    private String nomeAtividade;
    private double calorias;
    private int duracao;
    private boolean realizada;

    public Atividade(){
        this.nomeAtividade = "";
        this.calorias = 0;
        this.duracao = 0;
        this.realizada = false;
    }

    public Atividade(String nomeAtividade, double calorias, int duracao, boolean realizada){
        this.nomeAtividade = nomeAtividade;
        this.calorias = calorias;
        this.duracao = duracao;
        this.realizada = false;
    }

    public Atividade(Atividade atividade){
        this.nomeAtividade = atividade.getNomeAtividade();
        this.calorias = atividade.getCalorias();
        this.duracao = atividade.getDuracao();
        this.realizada = atividade.isRealizada();
    }

    public String getNomeAtividade() {
        return nomeAtividade;
    }

    public void setNomeAtividade(String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }

    public double getCalorias() {
        return calorias;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public boolean isRealizada() {
        return realizada;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }


    public abstract Atividade clone();

    public abstract String toString();

    public abstract double calorias(double peso, int frequenciaCardiaca, double fator);
}

