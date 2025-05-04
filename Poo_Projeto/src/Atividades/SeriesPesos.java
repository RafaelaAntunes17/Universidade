package Atividades;

import java.util.List;

public class SeriesPesos extends Series {
    private double peso;

    public SeriesPesos(){
        super();
        this.peso = 0;
    }

    public SeriesPesos(String nomeAtividade, int calorias, int duracao, List<Integer> repeticoes, int series, int tempoDescanso, double peso,boolean realizada){
        super(nomeAtividade, calorias, duracao, repeticoes, series, tempoDescanso, realizada);
        this.peso = peso;
    }

    public SeriesPesos(SeriesPesos atividade){
        super(atividade);
        this.peso = atividade.getPeso();
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String toString(){
        return "Nome da atividade: " + getNomeAtividade()
                + "\nCalorias: " + getCalorias() + " (estimado)"
                + "\nDuracao: " + getDuracao()
                + "\nPeso: " + getPeso()
                + "\nRepetições: " + super.getRepeticoes()
                + "\nSéries: " + super.getSeries()
                + "\nTempo de descanso: " + super.getTempoDescanso();
    }

    public SeriesPesos clone(){
        return new SeriesPesos(this);
    }

    public double calorias(double peso, int frequenciaCardiaca, double fator){
        return ( this.peso * super.getSeries() * (super.getDuracao() / 60.0)  * peso * fator) * (frequenciaCardiaca / 200.0);
    }
}

