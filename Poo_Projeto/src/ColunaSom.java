public class ColunaSom extends Dispositivo {
    private int volume;
    private Estado estado;

    public enum Estado {
        LIGADO(20),
        MUDO(0),
        DESLIGADO(0);

        private final int volume;

        Estado(int volume) {
            this.volume = volume;
        }

        public int getVolume() {
            return this.volume;
        }
    }

    public ColunaSom() {
        super();
        this.estado = Estado.DESLIGADO;
        this.volume = estado.getVolume();
    }

    public ColunaSom(String marca, String modelo, double consumo) {
        super(marca, modelo, consumo);
        this.estado = Estado.DESLIGADO;
        this.volume = estado.getVolume();
    }

    public ColunaSom(String marca, String modelo, double consumo, Estado estado) {
        super(marca, modelo, consumo);
        this.estado = estado;
        this.volume = estado.getVolume();
    }

    public ColunaSom(ColunaSom c) {
        super(c);
        this.volume = c.getVolume();
        this.estado = c.getEstado();
    }

    public int getVolume() {
        return this.volume;
    }

    public void setVolume(int volume) {
        if (volume < 0 || volume > 100)
            throw new IllegalArgumentException("Volume deve estar entre 0 e 100");
        this.volume = volume;
        if (volume == 0 && this.estado == Estado.LIGADO) {
            this.estado = Estado.MUDO;
        } else if (volume > 0 && this.estado == Estado.MUDO) {
            this.estado = Estado.LIGADO;
        }
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
        this.volume = estado.getVolume();
    }

    public void aumentarVolume(int valor) {
        if (!this.isLigado())
            throw new IllegalStateException("Coluna está desligada");
        this.setVolume(Math.min(this.volume + valor, 100));
    }

    public void diminuirVolume(int valor) {
        if (!this.isLigado())
            throw new IllegalStateException("Coluna está desligada");
        this.setVolume(Math.max(this.volume - valor, 0));
    }

    public void mutar() {
        if (!this.isLigado())
            throw new IllegalStateException("Coluna está desligada");
        this.estado = Estado.MUDO;
        this.volume = 0;
    }

    @Override
    public boolean isLigado() {
        return this.estado != Estado.DESLIGADO;
    }

    @Override
    public void ligar(double tempoAtual) {
        if (isLigado())
            return;
        this.setEstado(Estado.LIGADO);
        registarAtivacao(tempoAtual);
    }

    @Override
    public void desligar(double tempoAtual) {
        if (isLigado())
            registarDesativacao(tempoAtual);
        this.setEstado(Estado.DESLIGADO);
    }

    @Override
    public ColunaSom clone() {
        return new ColunaSom(this);
    }

    public boolean equals(Object o) {
        if (!super.equals(o))
            return false;
        ColunaSom c = (ColunaSom) o;
        return this.volume == c.getVolume() &&
                this.estado == c.getEstado();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString())
                .append(" | Coluna de Som")
                .append(" | Estado: ")
                .append(this.estado);
        return sb.toString();
    }

    @Override
    public String toStringDetalhado() {
        StringBuilder sb = new StringBuilder();
        sb.append("Identificador: ")
                .append(getIdentificador())
                .append(" | Tipo: Coluna de Som")
                .append("\n -> Marca: ")
                .append(getMarca())
                .append(" | Modelo: ")
                .append(getModelo())
                .append("\n -> Estado: ")
                .append(this.estado);

        if (this.estado != Estado.DESLIGADO) {
            sb.append(" | Volume: ")
                    .append(this.volume)
                    .append("%");
        }

        sb.append("\n -> Consumo/hora: ")
                .append(getConsumoHora())
                .append(" Wh | Ativações: ")
                .append(getNumAtivacoes())
                .append("\n -> Tempo ligado: ")
                .append(String.format("%.1f", getTempoTotalLigado()))
                .append(" min");

        return sb.toString();
    }
}