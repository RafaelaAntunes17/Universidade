package Atividades;

public class Resistencia extends Atividade {
    private double distancia;
    private double velocidade;

    public Resistencia(){
        super();
        this.distancia = 0;
        this.velocidade = 0;
    }

    public Resistencia(String nomeAtividade, int calorias, int duracao, double distancia, double velocidade, boolean realizada){
        super(nomeAtividade, calorias, duracao, realizada);
        this.distancia = distancia;
        this.velocidade = velocidade;
    }

    public Resistencia(Resistencia atividade){
        super(atividade);
        this.distancia = atividade.getDistancia();
        this.velocidade = atividade.getVelocidade();
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }

    @Override
    public String toString(){
        return "Nome da atividade: " + getNomeAtividade()
                + "\nCalorias: " + getCalorias() + " (estimado)"
                + "\nDuracao: " + getDuracao()
                + "\nDistancia: " + getDistancia()
                + "\nVelocidade: " + getVelocidade();
    }

    public Resistencia clone(){
        return new Resistencia(this);
    }

    public double calorias(double peso, int frequenciaCardiaca, double fator){
        return (ExerciciosMET.getMET(this.velocidade) * (this.getDuracao() / 60.0)  * peso * fator) * (frequenciaCardiaca / 200.0);
    }
}
