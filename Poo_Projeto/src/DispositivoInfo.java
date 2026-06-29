public interface DispositivoInfo {
    public String getIdentificador();

    public String getMarca();

    public String getModelo();

    public double getConsumoHora();

    public double getConsumoTotal();

    public double getTempoTotalLigado();

    public int getNumAtivacoes();

    public boolean isLigado();
}