public class ActionOff implements Action {
    private String idDispositivo;

    public ActionOff(String idDispositivo) {
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

        d.desligar(tempoAtual);
        casa.atualizarDispositivo(nomeDivisao, d);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Desligar ").append(this.idDispositivo);
        return sb.toString();
    }
}