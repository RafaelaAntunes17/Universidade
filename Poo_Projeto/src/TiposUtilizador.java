public enum TiposUtilizador {
    Amador(1.1),
    Profissional(1.4),
    Ocasional(1),
    Outro(0);

    private double fator;

    TiposUtilizador(double fator){
        this.fator = fator;
    }

    public double getFator(){
        return this.fator;
    }
}
