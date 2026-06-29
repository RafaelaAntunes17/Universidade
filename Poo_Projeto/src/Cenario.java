import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cenario implements Serializable {

    private String nome;
    private String descricao;
    private List<Action> acoes;

    public Cenario(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.acoes = new ArrayList<>();
    }

    public Cenario(Cenario c) {
        this.nome = c.nome;
        this.descricao = c.descricao;
        this.acoes = new ArrayList<>(c.acoes);
    }

    public String getNome() {
        return this.nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public List<Action> getAcoes() {
        return new ArrayList<>(this.acoes);
    }

    public void addAcao(Action a) {
        this.acoes.add(a);
    }

    public void executar(Casa casa, double tempoAtual) {
        for (Action a : this.acoes) {
            a.execute(casa, tempoAtual);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cenário: ")
                .append(this.nome)
                .append(" | ")
                .append(this.descricao)
                .append(" (")
                .append(this.acoes.size())
                .append(" ação(ões))");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        Cenario c = (Cenario) o;
        return this.nome.equals(c.nome);
    }

    public Cenario clone() {
        return new Cenario(this);
    }
}