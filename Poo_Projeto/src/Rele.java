public class Rele extends Dispositivo {
    private Estado estado;

    public enum Estado {
        LIGADO, DESLIGADO
    }

    public Rele() {
        super();
        this.estado = Estado.DESLIGADO;
    }

    public Rele(String marca, String modelo, double consumo) {
        super(marca, modelo, consumo);
        this.estado = Estado.DESLIGADO;
    }

    public Rele(String marca, String modelo, double consumo, Estado estado) {
        super(marca, modelo, consumo);
        this.estado = estado;
    }

    public Rele(Rele r) {
        super(r);
        this.estado = r.getEstado();
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public boolean isLigado() {
        return this.estado == Estado.LIGADO;
    }

    @Override
    public void ligar(double tempoAtual) {
        if (isLigado())
            return;
        this.estado = Estado.LIGADO;
        registarAtivacao(tempoAtual);
    }

    public void desligar(double tempoAtual) {
        if (isLigado())
            registarDesativacao(tempoAtual);
        this.estado = Estado.DESLIGADO;
    }

    public Rele clone() {
        return new Rele(this);
    }

    public boolean equals(Object o) {
        if (!super.equals(o))
            return false;
        Rele r = (Rele) o;
        return this.estado == r.getEstado();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString())
                .append(" | Relé")
                .append(" | Estado: ")
                .append(this.estado);
        return sb.toString();
    }

    @Override
    public String toStringDetalhado() {
        StringBuilder sb = new StringBuilder();
        sb.append("Identificador: ")
                .append(getIdentificador())
                .append(" | Tipo: Relé")
                .append("\n -> Marca: ")
                .append(getMarca())
                .append(" | Modelo: ")
                .append(getModelo())
                .append("\n -> Estado: ")
                .append(this.estado)
                .append("\n -> Consumo/hora: ")
                .append(getConsumoHora())
                .append(" Wh")
                .append(" | Ativações: ")
                .append(getNumAtivacoes())
                .append("\n -> Tempo ligado: ")
                .append(String.format("%.1f", getTempoTotalLigado()))
                .append(" min")
                .append(" | Consumo total: ")
                .append(String.format("%.2f", getConsumoTotal()))
                .append(" Wh");
        return sb.toString();
    }

}