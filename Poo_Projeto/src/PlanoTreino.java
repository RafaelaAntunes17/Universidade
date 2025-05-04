import Atividades.Atividade;
import Atividades.Atividades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlanoTreino implements Serializable {
    private String nome;
    private String diaSemana;
    private List<Atividade> atividades;

    public PlanoTreino() {
        this.nome = "";
        this.diaSemana = "";
        this.atividades = new ArrayList<>();
    }

    public PlanoTreino(String nome, String diaSemana, List<Atividade> atividades) {
        this.nome = nome;
        this.diaSemana = diaSemana;
        this.atividades = atividades;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    public double getCalorias(){
        return this.atividades.stream().mapToDouble(Atividade::getCalorias).sum();
    }
}
