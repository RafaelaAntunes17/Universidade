import java.io.Serializable;

public interface Action extends Serializable {
    void execute(Casa casa, double tempoAtual);

    public static Action criar(int tipoAcao, String idDisp) {
        switch (tipoAcao) {
            case 1: return new ActionOn(idDisp);
            case 2: return new ActionOff(idDisp);
            case 3: return new ActionTodasLampadas(true);
            case 4: return new ActionTodasLampadas(false);
            case 5: return new ActionTodasColunas(true);
            case 6: return new ActionTodasColunas(false);
            case 7: return new ActionTodosDispAbertura(false);
            case 8: return new ActionTodosDispAbertura(true);
            default: throw new IllegalArgumentException("Tipo de ação inválido: " + tipoAcao);
        }
    }
}