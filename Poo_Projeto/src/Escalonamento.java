import java.io.Serializable;

public class Escalonamento implements Serializable {
    private int hora;
    private int minuto;
    private Action acao;

    public Escalonamento(int hora, int minuto, Action acao) {
        if (hora < 0 || hora > 23 || minuto < 0 || minuto > 59)
            throw new IllegalArgumentException("Hora inválida (0-23h, 0-59min).");
        this.hora = hora;
        this.minuto = minuto;
        this.acao = acao;
    }

    public Escalonamento(Escalonamento e) {
        this.hora = e.hora;
        this.minuto = e.minuto;
        this.acao = e.acao;
    }

    public int getHora() {
        return this.hora;
    }

    public int getMinuto() {
        return this.minuto;
    }

    public Action getAcao() {
        return this.acao;
    }

    public int getTempoEmMinutos() {
        return this.hora * 60 + this.minuto;
    }

    public void executarSeNoIntervalo(int tempoInicial, int tempoFinal, Casa casa, double tempoAtual) {
        int meu = getTempoEmMinutos();
        if (meu > tempoInicial && meu <= tempoFinal) {
            this.acao.execute(casa, tempoAtual);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Escalonamento às ")
                .append(String.format("%02d:%02d", this.hora, this.minuto))
                .append(" | Ação: ")
                .append(this.acao);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        Escalonamento e = (Escalonamento) o;
        return this.hora == e.hora && this.minuto == e.minuto
                && this.acao.equals(e.acao);
    }

    public Escalonamento clone() {
        return new Escalonamento(this);
    }
}