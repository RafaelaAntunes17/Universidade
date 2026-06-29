import java.io.Serializable;

public class Automacao implements Serializable {
    private String nome;
    private Condicao condicao;
    private Action acao;

    public Automacao(String nome, Condicao condicao, Action acao) {
        this.nome = nome;
        this.condicao = condicao;
        this.acao = acao;
    }

    public Automacao(Automacao a) {
        this.nome = a.nome;
        this.condicao = a.condicao;
        this.acao = a.acao;
    }

    public String getNome() {
        return this.nome;
    }

    public Condicao getCondicao() {
        return this.condicao;
    }

    public Action getAcao() {
        return this.acao;
    }

    public void execute(Casa casa, double tempoAtual) {
        if (condicao.verify(casa)) {
            acao.execute(casa, tempoAtual);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Automação: ")
                .append(this.nome)
                .append(" | Se: ")
                .append(this.condicao)
                .append(" - ")
                .append(this.acao);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        Automacao a = (Automacao) o;
        return this.nome.equals(a.nome);
    }
    public Automacao clone() {
        return new Automacao(this);
    }
    public static Automacao criar(String nome, int tipoCond, String idCondDisp, double limiar,
            int tipoAcao, String idDispAcao) {
        Condicao cond;
        switch (tipoCond) {
            case 1: cond = new CondicaoOn(idCondDisp); break;
            case 2: cond = new CondicaoOff(idCondDisp); break;
            case 3: cond = new CondicaoLuminosidade(limiar); break;
            case 4: cond = new CondicaoChuva(limiar); break;
            default: throw new IllegalArgumentException("Tipo de condição inválido.");
        }
        return new Automacao(nome, cond, Action.criar(tipoAcao, idDispAcao));
    }

    
}