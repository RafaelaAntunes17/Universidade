public class CondicaoChuva implements Condicao {

    private double limite;

    public CondicaoChuva(double limite) {
        this.limite = limite;
    }

    public double getLimite() {
        return this.limite;
    }

    @Override
    public boolean verify(Casa casa) {
        return casa.getPrecipitacao() > this.limite;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Precipitacao > ")
                .append(this.limite)
                .append(" mm/h");
        return sb.toString();
    }
}