public class ActionTodosDispAbertura implements Action {

    private boolean fechar;

    public ActionTodosDispAbertura(boolean fechar) {
        this.fechar = fechar;
    }

    @Override
    public void execute(Casa casa, double tempoAtual) {
        for (Dispositivo d : casa.getTodosDispositivos()) {
            if (!(d instanceof DispositivoAbertura))
                continue;
            DispositivoAbertura dispAb = (DispositivoAbertura) d;
            if (fechar) {
                if (d instanceof Portao) {
                    Portao p = (Portao) d;
                    if (!p.isTrancado()) {
                        dispAb.fechar();
                        p.trancar();
                    }
                } else {
                    dispAb.fechar();
                }
                dispAb.desligar(tempoAtual);
            } else {
                if (d instanceof Portao)
                    ((Portao) d).destrancar();
                dispAb.ligar(tempoAtual);
                dispAb.abrir();
            }
            String nomeDivisao = casa.divisaoDoDispositivo(d.getIdentificador());
            if (nomeDivisao != null)
                casa.atualizarDispositivo(nomeDivisao, d);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.fechar ? "Fechar todas as cortinas/portões" : "Abrir todas as cortinas/portões");
        return sb.toString();
    }
}