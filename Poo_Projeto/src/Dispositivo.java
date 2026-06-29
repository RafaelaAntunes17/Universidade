import java.io.Serializable;

public abstract class Dispositivo implements Serializable, DispositivoInfo {
    private static int contador = 1;
    private String identificador;
    private String marca;
    private String modelo;
    private double consumoHora;
    private int numAtivacoes;
    private double tempoTotalLigado;
    private double momentoLigado;

    public Dispositivo() {
        this.identificador = "DISP" + (contador++);
        this.marca = "";
        this.modelo = "";
        this.consumoHora = 0.0;
        this.numAtivacoes = 0;
        this.tempoTotalLigado = 0.0;
        this.momentoLigado = 0.0;

    }

    public Dispositivo(String marca, String modelo, double consumoHora) {
        this.identificador = "DISP" + (contador++);
        this.marca = marca;
        this.modelo = modelo;
        this.consumoHora = consumoHora;
        this.numAtivacoes = 0;
        this.tempoTotalLigado = 0.0;
        this.momentoLigado = 0.0;
    }

    public Dispositivo(String identificador, String marca, String modelo, double consumoHora, int numAtivacoes,
            double tempoTotalLigado, double momentoLigado) {
        this.identificador = identificador;
        this.marca = marca;
        this.modelo = modelo;
        this.consumoHora = consumoHora;
        this.numAtivacoes = numAtivacoes;
        this.tempoTotalLigado = tempoTotalLigado;
        this.momentoLigado = momentoLigado;
    }

    public Dispositivo(Dispositivo d) {
        this.identificador = d.getIdentificador();
        this.marca = d.getMarca();
        this.modelo = d.getModelo();
        this.consumoHora = d.getConsumoHora();
        this.numAtivacoes = d.getNumAtivacoes();
        this.tempoTotalLigado = d.getTempoTotalLigado();
        this.momentoLigado = d.getMomentoLigado();
    }

    public static int getContador() {
        return contador;
    }

    public static void setContador(int valor) {
        contador = valor;
    }

    public String getIdentificador() {
        return this.identificador;
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return this.modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getConsumoHora() {
        return this.consumoHora;
    }

    public void setConsumoHora(double consumoHora) {
        this.consumoHora = consumoHora;
    }

    public int getNumAtivacoes() {
        return this.numAtivacoes;
    }

    public double getTempoTotalLigado() {
        return this.tempoTotalLigado;
    }

    public double getMomentoLigado() {
        return this.momentoLigado;
    }

    protected void registarAtivacao(double tempoAtual) {
        this.numAtivacoes++;
        this.momentoLigado = tempoAtual;
    }

    protected void registarDesativacao(double tempoAtual) {
        this.tempoTotalLigado += tempoAtual - this.momentoLigado;
    }

    public void atualizarTempoLigado(double elapsed, double novoTempoAtual) {
        if (isLigado()) {
            this.tempoTotalLigado += elapsed;
            this.momentoLigado = novoTempoAtual;
        }
    }

    public static Dispositivo criar(String tipo, String marca, String modelo, double consumo) {
        switch (tipo) {
            case "rele":
                return new Rele(marca, modelo, consumo);
            case "lampada":
                return new Lampada(marca, modelo, consumo);
            case "coluna":
                return new ColunaSom(marca, modelo, consumo);
            case "estore":
                return new Estore(marca, modelo, consumo);
            case "portao":
                return new Portao(marca, modelo, consumo);
            default:
                throw new IllegalArgumentException("Tipo inválido: " + tipo);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;

        Dispositivo d = (Dispositivo) o;

        return this.numAtivacoes == d.getNumAtivacoes() &&
                this.consumoHora == d.getConsumoHora() &&
                this.tempoTotalLigado == d.getTempoTotalLigado() &&
                this.momentoLigado == d.getMomentoLigado() &&
                this.identificador.equals(d.getIdentificador()) &&
                this.marca.equals(d.getMarca()) &&
                this.modelo.equals(d.getModelo());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ")
                .append(this.identificador);
        return sb.toString();
    }

    public String toStringDetalhado() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ")
                .append(this.identificador)
                .append("\n -> Marca: ")
                .append(this.marca)
                .append(" | Modelo: ")
                .append(this.modelo)
                .append("\n -> Estado: ")
                .append(isLigado() ? "LIGADO" : "DESLIGADO")
                .append("\n -> Consumo/hora: ")
                .append(this.consumoHora)
                .append(" Wh | Ativações: ")
                .append(this.numAtivacoes)
                .append("\n -> Tempo ligado: ")
                .append(String.format("%.1f", this.tempoTotalLigado))
                .append(" min | Consumo total: ")
                .append(String.format("%.2f", getConsumoTotal()))
                .append(" Wh");

        return sb.toString();
    }

    public double getConsumoTotal() {
        return this.consumoHora * this.tempoTotalLigado / 60.0;
    }

    @Override
    public abstract Dispositivo clone();

    public abstract boolean isLigado();

    public abstract void ligar(double tempoAtual);

    public abstract void desligar(double tempoAtual);

}