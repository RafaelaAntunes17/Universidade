public class CondicaoLuminosidade implements Condicao {

    private double limite;

    public CondicaoLuminosidade(double limite) {
        this.limite = limite;
    }

    public double getLimite() {
        return this.limite;
    }

    @Override
    public boolean verify(Casa casa) {
        return casa.getLuminosidade() < this.limite;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Luminosidade < ")
                .append(this.limite)
                .append(" lux");
        return sb.toString();
    }
}