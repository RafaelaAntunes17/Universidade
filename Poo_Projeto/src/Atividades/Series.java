package Atividades;

import Atividades.Atividade;

import java.util.List;

public class Series extends Atividade {
    private List<Integer> repeticoes;
    private int series;
    private int tempoDescanso;

    public Series(){
        super();
        this.repeticoes = null;
        this.series = 0;
        this.tempoDescanso = 0;
    }

    public Series(String nomeAtividade, int calorias, int duracao, List<Integer> repeticoes, int series, int tempoDescanso, boolean realizada){
        super(nomeAtividade, calorias, duracao, realizada);
        this.repeticoes = repeticoes;
        this.series = series;
        this.tempoDescanso = tempoDescanso;
    }

    public Series(Series atividade){
        super(atividade);
        this.repeticoes = atividade.getRepeticoes();
        this.series = atividade.getSeries();
        this.tempoDescanso = atividade.getTempoDescanso();
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public List<Integer> getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(List<Integer> repeticoes) {
        this.repeticoes = repeticoes;
    }

    public int getTempoDescanso() {
        return tempoDescanso;
    }

    public void setTempoDescanso(int tempoDescanso) {
        this.tempoDescanso = tempoDescanso;
    }

    public String toString(){
        return "Nome da atividade: " + getNomeAtividade()
                + "\nCalorias: " + getCalorias() + " (estimado)"
                + "\nDuracao: " + getDuracao()
                + "\nRepeticoes: " + getRepeticoes()
                + "\nSeries: " + getSeries()
                + "\nTempo de descanso: " + getTempoDescanso();
    }

    public Series clone(){
        return new Series(this);
    }

    public double calorias(double peso, int frequenciaCardiaca, double fator){
        return ((this.repeticoes.stream().mapToInt(integer -> integer).sum() / (double) this.series) / 0.75 * (this.getDuracao() / 60.0)  * peso * fator) * (frequenciaCardiaca / 200.0);
    }
}
