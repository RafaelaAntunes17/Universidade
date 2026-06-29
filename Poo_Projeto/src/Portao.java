public class Portao extends DispositivoAbertura {
    private boolean trancado;

    public Portao() {
        super();
        this.trancado = true;
    }

    public Portao(String marca, String modelo, double consumo) {
        super(marca, modelo, consumo);
        this.trancado = true;
    }

    public Portao(String marca, String modelo, double consumo, int abertura, boolean trancado) {
        super(marca, modelo, consumo, abertura);
        this.trancado = trancado;
    }

    public Portao(Portao p) {
        super(p);
        this.trancado = p.isTrancado();
    }

    public boolean isTrancado() {
        return this.trancado;
    }

    public void trancar() {
        if (this.trancado)
            return;
        this.fechar();
        this.trancado = true;
    }

    public void destrancar() {
        this.trancado = false;
    }

    @Override
    public void setAbertura(int abertura) {
        if (this.trancado)
            throw new IllegalStateException("Portão está trancado. Destranque primeiro.");
        super.setAbertura(abertura);
    }

    @Override
    public void abrir() {
        if (this.trancado)
            throw new IllegalStateException("Portão está trancado. Destranque primeiro.");
        super.abrir();
    }

    @Override
    public Portao clone() {
        return new Portao(this);
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o))
            return false;
        Portao p = (Portao) o;
        return this.trancado == p.isTrancado();
    }

    @Override
    public String toString() {
        String posicao = getAbertura() > 0 ? "Aberto (" + getAbertura() + "%)" : "Fechado";

        StringBuilder sb = new StringBuilder();
        sb.append(getIdentificador())
                .append(" | Portão | ")
                .append(getMarca())
                .append(" ")
                .append(getModelo())
                .append(" | ")
                .append(isLigado() ? "Ligado" : "Desligado")
                .append(" | ")
                .append(posicao)
                .append(" | Trancado: ")
                .append(this.trancado ? "Sim" : "Não");

        return sb.toString();
    }

    @Override
    public String toStringDetalhado() {
        String posicao = getAbertura() > 0 ? "ABERTO (" + getAbertura() + "%)" : "FECHADO";

        StringBuilder sb = new StringBuilder();
        sb.append("Identificador: ")
                .append(getIdentificador())
                .append(" | Tipo: Portão")
                .append("\n -> Marca: ")
                .append(getMarca())
                .append(" | Modelo: ")
                .append(getModelo())
                .append("\n -> Estado: ")
                .append(isLigado() ? "LIGADO" : "DESLIGADO")
                .append("\n -> Posição: ")
                .append(posicao)
                .append(" | Trancado: ")
                .append(this.trancado ? "Sim" : "Não")
                .append("\n -> Consumo/hora: ")
                .append(getConsumoHora())
                .append(" Wh")
                .append(" | Ativações: ")
                .append(getNumAtivacoes())
                .append("\n -> Tempo ligado: ")
                .append(String.format("%.1f", getTempoTotalLigado()))
                .append(" min");

        return sb.toString();
    }
}