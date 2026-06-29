public class ResultadoDivisao {
    private String nomeCasa;
    private String nomeDivisao;
    private int numDispositivos;

    public ResultadoDivisao(String nomeCasa, String nomeDivisao, int numDispositivos) {
        this.nomeCasa = nomeCasa;
        this.nomeDivisao = nomeDivisao;
        this.numDispositivos = numDispositivos;
    }

    public String getNomeCasa() {
        return this.nomeCasa;
    }

    public String getNomeDivisao() {
        return this.nomeDivisao;
    }

    public int getNumDispositivos() {
        return this.numDispositivos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Divisão: ")
                .append(this.nomeDivisao)
                .append(" | Casa: ")
                .append(this.nomeCasa)
                .append("\n -> Dispositivos: ")
                .append(this.numDispositivos);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        ResultadoDivisao r = (ResultadoDivisao) o;
        return this.numDispositivos == r.numDispositivos &&
                this.nomeCasa.equals(r.nomeCasa) &&
                this.nomeDivisao.equals(r.nomeDivisao);
    }

}