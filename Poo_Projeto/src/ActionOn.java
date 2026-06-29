public class ActionOn implements Action {
    private String idDispositivo;

    public ActionOn(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getIdDispositivo() {
        return this.idDispositivo;
    }

    @Override
    public void execute(Casa casa, double tempoAtual) {
        String nomeDivisao = casa.divisaoDoDispositivo(idDispositivo);
        if (nomeDivisao == null)
            return;

        Dispositivo d = casa.getDispositivo(nomeDivisao, idDispositivo);
        if (d == null)
            return;

        d.ligar(tempoAtual);
        casa.atualizarDispositivo(nomeDivisao, d);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ligar ").append(this.idDispositivo);
        return sb.toString();
    }
}