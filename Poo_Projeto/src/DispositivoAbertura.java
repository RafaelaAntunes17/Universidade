
public abstract class DispositivoAbertura extends Dispositivo {
    private int abertura;
    private boolean ligado;

    public DispositivoAbertura() {
        super();
        this.abertura = 0;
        this.ligado = false;
    }

    public DispositivoAbertura(String marca, String modelo, double consumo) {
        super(marca, modelo, consumo);
        this.abertura = 0;
        this.ligado = false;
    }

    public DispositivoAbertura(String marca, String modelo, double consumo, int abertura) {
        super(marca, modelo, consumo);
        if (abertura < 0 || abertura > 100)
            throw new IllegalArgumentException("Abertura deve estar entre 0 e 100");
        this.abertura = abertura;
        this.ligado = false;
    }

    public DispositivoAbertura(DispositivoAbertura d) {
        super(d);
        this.abertura = d.getAbertura();
        this.ligado = d.isLigado();
    }

    

    public int getAbertura() {
        return this.abertura;
    }

    public void setAbertura(int abertura) {
        if (abertura < 0 || abertura > 100)
            throw new IllegalArgumentException("Abertura deve estar entre 0 e 100");
        this.abertura = abertura;
    }

    public void abrir() {
        setAbertura(100);
    }

    public void fechar() {
        setAbertura(0);
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o))
            return false;
        DispositivoAbertura d = (DispositivoAbertura) o;
        return this.abertura == d.getAbertura() &&
                this.ligado == d.isLigado();
    }
    @Override
    public boolean isLigado() {
        return this.ligado;
    }

    @Override
    public void ligar(double tempoAtual) {
        if (this.ligado)
            return;
        this.ligado = true;
        registarAtivacao(tempoAtual);
    }

    @Override
    public void desligar(double tempoAtual) {
        if (this.ligado) {
            registarDesativacao(tempoAtual);
        }
        this.ligado = false;
    }

    @Override
    public String toString() {
        String posicao = this.abertura > 0 ? "Aberto (" + this.abertura + "%)" : "Fechado";

        StringBuilder sb = new StringBuilder();
        sb.append(super.toString())
                .append(" | ")
                .append(this.ligado ? "Ligado" : "Desligado")
                .append(" | ")
                .append(posicao);

        return sb.toString();
    }

    @Override
    public String toStringDetalhado() {
        String posicao = this.abertura > 0 ? "ABERTO (" + this.abertura + "%)" : "FECHADO";

        StringBuilder sb = new StringBuilder();
        sb.append("Identificador: ")
                .append(getIdentificador())
                .append("\n -> Marca: ")
                .append(getMarca())
                .append(" | Modelo: ")
                .append(getModelo())
                .append("\n -> Estado: ")
                .append(this.ligado ? "LIGADO" : "DESLIGADO")
                .append(" | Posição: ")
                .append(posicao)
                .append("\n -> Consumo/hora: ")
                .append(getConsumoHora())
                .append(" Wh | Ativações: ")
                .append(getNumAtivacoes())
                .append("\n -> Tempo ligado: ")
                .append(String.format("%.1f", getTempoTotalLigado()))
                .append(" min");

        return sb.toString();
    }
}