package Atividades;

import java.io.Serializable;

public class Trilha extends Atividade{
    private double distancia;
    private int altura;

    public Trilha(){
        super();
        this.distancia = 0;
        this.altura = 0;
    }

    public Trilha(double distancia, int altura, String nomeAtividade, int calorias, int duracao, boolean realizada){
        super(nomeAtividade, calorias, duracao, realizada);
        this.distancia = distancia;
        this.altura = altura;
    }

    public Trilha(Trilha atividade){
        super(atividade);
        this.distancia = atividade.getDistancia();
        this.altura = atividade.getAltura();
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public String toString(){
        return "Nome da atividade: " + this.getNomeAtividade()
                + "\nCalorias: " + this.getCalorias() + " (estimado)"
                + "\nDuracao: " + this.getDuracao()
                + "\nDistancia: " + this.getDistancia()
                + "\nAltura: " + this.getAltura();
    }

    public Trilha clone(){
        return new Trilha(this);
    }

    public double calorias(double peso, int frequenciaCardiaca, double fator){
        return (ExerciciosMET.getMET(this.altura) * (this.getDuracao() / 60.0)  * peso * fator) * (frequenciaCardiaca / 200.0);
    }
}
