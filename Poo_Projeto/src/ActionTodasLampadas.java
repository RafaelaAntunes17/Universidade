public class ActionTodasLampadas implements Action {

    private boolean ligar;

    public ActionTodasLampadas(boolean ligar) {
        this.ligar = ligar;
    }

    @Override
    public void execute(Casa casa, double tempoAtual) {
        for (Dispositivo d : casa.getTodosDispositivos()) {
            if (!(d instanceof Lampada))
                continue;
            if (ligar && !d.isLigado())
                d.ligar(tempoAtual);
            else if (!ligar && d.isLigado())
                d.desligar(tempoAtual);
            else
                continue;
            String nomeDivisao = casa.divisaoDoDispositivo(d.getIdentificador());
            if (nomeDivisao != null)
                casa.atualizarDispositivo(nomeDivisao, d);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.ligar ? "Ligar" : "Desligar")
                .append(" todas as lâmpadas");
        return sb.toString();
    }
}