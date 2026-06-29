
public class Lampada extends Dispositivo {
    private int intensidade;
    private int cor;
    private Estado estado;

    public enum Estado {
        NORMAL(50),
        ECO(20),
        MAX(100),
        DESLIGADO(0),
        CUSTOM(0);

        private final int intensidade;

        Estado(int intensidade) {
            this.intensidade = intensidade;
        }

        public int getIntensidade() {
            return this.intensidade;
        }
    }

    public Lampada() {
        super();
        this.estado = Estado.NORMAL;
        this.intensidade = estado.getIntensidade();
        this.cor = 3200;
    }

    public Lampada(String marca, String modelo, double consumo, Estado estado, int cor) {
        super(marca, modelo, consumo);
        this.estado = estado;
        this.intensidade = estado.getIntensidade();
        this.cor = cor;
    }

    public Lampada(String marca, String modelo, double consumo) {
        super(marca, modelo, consumo);
        this.estado = Estado.DESLIGADO;
        this.intensidade = estado.getIntensidade();
        this.cor = 3200;
    }

    public Lampada(Lampada l) {
        super(l);
        this.intensidade = l.getIntensidade();
        this.cor = l.getCor();
        this.estado = l.getEstado();

    }

    public int getIntensidade() {
        return this.intensidade;
    }

    public void setIntensidade(int intensidade) {
        if (intensidade < 0 || intensidade > 100)
            throw new IllegalArgumentException("A intensidade deve estar entre 0% e 100%");
        this.intensidade = intensidade;
        this.estado = Estado.CUSTOM;
    }

    public int getCor() {
        return this.cor;
    }

    public void setCor(int cor) {
        if (cor < 2700 || cor > 4000)
            throw new IllegalArgumentException("Cor deve estar entre 2700K e 4000K");
        this.cor = cor;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado novoEstado) {
        this.estado = novoEstado;
        if (novoEstado != Estado.CUSTOM) {
            this.intensidade = novoEstado.getIntensidade();
        }
    }

    public double getConsumoAtual() {
        if (this.estado == Estado.DESLIGADO)
            return 0.0;
        return getConsumoHora() * (this.intensidade / (double) Estado.MAX.getIntensidade());
    }

    @Override
    public Lampada clone() {
        return new Lampada(this);
    }

    public boolean equals(Object o) {
        if (!super.equals(o))
            return false;
        Lampada l = (Lampada) o;
        return this.intensidade == l.getIntensidade() &&
                this.cor == l.getCor() &&
                this.estado == l.getEstado();
    }

    public boolean isLigado() {
        return this.estado != Estado.DESLIGADO;
    }

    @Override
    public void ligar(double tempoAtual) {
        if (isLigado())
            return;
        this.estado = Estado.NORMAL;
        this.intensidade = Estado.NORMAL.getIntensidade();
        registarAtivacao(tempoAtual);
    }

    @Override
    public void desligar(double tempoAtual) {
        if (isLigado()) {
            registarDesativacao(tempoAtual);
            this.setEstado(Estado.DESLIGADO);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString())
                .append(" | Lâmpada")
                .append(" | Estado: ")
                .append(this.estado);
        return sb.toString();
    }

    @Override
    public String toStringDetalhado() {
        StringBuilder sb = new StringBuilder();
        sb.append("Identificador: ")
                .append(getIdentificador())
                .append(" | Tipo: Lâmpada")
                .append("\n -> Marca: ")
                .append(getMarca())
                .append(" | Modelo: ")
                .append(getModelo())
                .append("\n -> Estado: ")
                .append(this.estado);

        if (this.estado != Estado.DESLIGADO) {
            sb.append("\n -> Intensidade: ")
                    .append(this.intensidade)
                    .append("% | Cor: ")
                    .append(this.cor)
                    .append("K | Consumo atual: ")
                    .append(String.format("%.1f", this.getConsumoAtual()))
                    .append(" Wh");
        }

        sb.append("\n -> Consumo/hora: ")
                .append(this.getConsumoHora())
                .append(" Wh | Tempo total ligado: ")
                .append(String.format("%.1f", this.getTempoTotalLigado()))
                .append(" min");

        return sb.toString();
    }
}