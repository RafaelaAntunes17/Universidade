
public class ActionTodasColunas implements Action {

    private boolean ligar;

    public ActionTodasColunas(boolean ligar) {
        this.ligar = ligar;
    }

    @Override
    public void execute(Casa casa, double tempoAtual) {
        for (Dispositivo d : casa.getTodosDispositivos()) {
            if (!(d instanceof ColunaSom)) continue;
            if (ligar && !d.isLigado()) d.ligar(tempoAtual);
            else if (!ligar && d.isLigado()) d.desligar(tempoAtual);
            else continue;
            String nomeDivisao = casa.divisaoDoDispositivo(d.getIdentificador());
            if (nomeDivisao != null) casa.atualizarDispositivo(nomeDivisao, d);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.ligar ? "Ligar" : "Desligar")
                .append(" todas as colunas de som");
        return sb.toString();
    }
}