
public class Estore extends DispositivoAbertura {

    public Estore() {
        super();
    }

    public Estore(String marca, String modelo, double consumo) {
        super(marca, modelo, consumo);
    }

    public Estore(String marca, String modelo, double consumo, int abertura) {
        super(marca, modelo, consumo, abertura);
    }

    public Estore(Estore c) {
        super(c);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString())
                .append(" | Estore");
        return sb.toString();
    }

    @Override
    public String toStringDetalhado() {
        String posicao = getAbertura() > 0 ? "ABERTO (" + getAbertura() + "%)" : "FECHADO";

        StringBuilder sb = new StringBuilder();
        sb.append("Identificador: ")
                .append(getIdentificador())
                .append(" | Tipo: Estore")
                .append("\n -> Marca: ")
                .append(getMarca())
                .append(" | Modelo: ")
                .append(getModelo())
                .append("\n -> Estado: ")
                .append(isLigado() ? "LIGADO" : "DESLIGADO")
                .append(" | Posição: ")
                .append(posicao)
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

    @Override
    public Estore clone() {
        return new Estore(this);
    }
}