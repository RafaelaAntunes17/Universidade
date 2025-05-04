import Atividades.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Fitness {
    private HashMap<String, Acesso> utilizadores;
    private Acesso utilizador;
    private PlanosDeTreino planosDeTreino;
    private Atividades atividades;
    private LocalDate dataI;
    private LocalDate data;
    private PlanosRealizados planosRealizados;

    public Fitness(){
        this.utilizadores = new Acesso().carregarUtilizadores();
        this.utilizador = new Acesso();
        this.planosDeTreino = new PlanosDeTreino();
        this.atividades = new Atividades();
        this.dataI = LocalDate.of(2021,1,1);
        this.data = LocalDate.now();
        this.planosRealizados = new PlanosRealizados();
    }

    public Fitness(HashMap<String, Acesso> utilizadores, Acesso utilizador, PlanosDeTreino planosDeTreino, Atividades atividades,LocalDate dataI, LocalDate data, PlanosRealizados planosRealizados){
        this.utilizadores = utilizadores;
        this.utilizador = utilizador;
        this.planosDeTreino = planosDeTreino;
        this.atividades = atividades;
        this.dataI = dataI;
        this.data = data;
        this.planosRealizados = planosRealizados;
    }

    public Fitness(Fitness fitness){
        this.utilizadores = fitness.getUtilizadores();
        this.utilizador = fitness.getUtilizador();
        this.planosDeTreino = fitness.getPlanosDeTreino();
        this.atividades = fitness.getAtividades();
        this.dataI = fitness.getDataI();
        this.data = fitness.getData();
        this.planosRealizados = fitness.getPlanosRealizados();
    }

    public HashMap<String, Acesso> getUtilizadores() {
        return utilizadores;
    }

    public void setUtilizador(HashMap<String, Acesso> utilizadores) {
        this.utilizadores = utilizadores;
    }

    public Acesso getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(Acesso utilizador) {
        this.utilizador = utilizador;
    }

    public PlanosDeTreino getPlanosDeTreino() {
        return planosDeTreino;
    }

    public void setPlanosDeTreino(PlanosDeTreino planosDeTreino) {
        this.planosDeTreino = planosDeTreino;
    }

    public Atividades getAtividades() {
        return atividades;
    }

    public void setAtividades(Atividades atividades) {
        this.atividades = atividades;
    }

    public LocalDate getDataI() {
        return dataI;
    }

    public void setDataI(LocalDate dataI) {
        this.dataI = dataI;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public PlanosRealizados getPlanosRealizados() {
        return planosRealizados;
    }

    public void setPlanosRealizados(PlanosRealizados planosRealizados) {
        this.planosRealizados = planosRealizados;
    }

    public Fitness clone(){
        return new Fitness(this);
    }

    public void adicionarPlanoTreino(PlanoTreino plano){
        this.planosDeTreino.addPlanoTreino(this.utilizador.getUserName(), plano);
    }

    public void removerPlanoTreino(PlanoTreino plano){
        this.planosDeTreino.removePlanoTreino(this.utilizador.getUserName(), plano);
    }

    public List<PlanoTreino> getPlanosTreino(){
        return this.planosDeTreino.getPlanosTreino(this.utilizador.getUserName());
    }

    public List<PlanoRealizado> getPlanosRealizados(String nome){
        return this.planosRealizados.getPlanosRealizados(nome);
    }

    public void adicionarPlanoRealizado(PlanoTreino plano, int frequenciaCardiaca){
        PlanoRealizado planoRealizado = new PlanoRealizado(plano.getNome(), plano.getAtividades()
                .stream()
                .mapToDouble(atividade -> atividade.calorias(this.utilizador.getPeso() ,this.utilizador.getFrequenciaCardiaca(), this.utilizador.getTipodeutilizador().getFator()))
                .sum(), this.data, frequenciaCardiaca);
        this.planosRealizados.addPlanoRealizado(this.utilizador.getUserName(), planoRealizado);
    }

    public void adicionarPlanoRealizado(String nome, PlanoTreino plano, LocalDate data){
        PlanoRealizado planoRealizado = new PlanoRealizado(plano.getNome(), plano.getAtividades()
                .stream()
                .mapToDouble(atividade -> atividade.calorias(this.utilizadores.get(nome).getPeso() ,this.utilizadores.get(nome).getFrequenciaCardiaca(), this.utilizadores.get(nome).getTipodeutilizador().getFator()))
                .sum(), data, this.utilizadores.get(nome).getFrequenciaCardiaca());
        this.planosRealizados.addPlanoRealizado(nome, planoRealizado);
    }

    public void listarAtividadesRealizadas(){
        this.planosRealizados.toStringPlanosRealizados(this.utilizador.getUserName());
    }

    public boolean login(String nome, String password){
        if(this.utilizadores.containsKey(nome)){
            if(this.utilizadores.get(nome).getSenha().equals(password)){
                this.utilizador = this.utilizadores.get(nome).clone();
                return true;
            }
        }
        return false;
    }

    public boolean registar(Acesso utilizador){
        if(this.utilizadores.containsKey(utilizador.getUserName())){
            return false;
        }
        this.utilizadores.put(utilizador.getUserName(), utilizador.clone());
        utilizador.guardarUtilizadores();
        return true;
    }

    public void tabelaCalorias(){
        TreeMap<String, Double> tabela = new TreeMap<>(Comparator.reverseOrder());

        this.getPlanosRealizados().getPlanosRealizados().forEach((userName, planos) ->{
            tabela.put(userName, planos.stream()
                    .filter(planoRealizado -> planoRealizado.getData().compareTo(this.dataI) > 0 && planoRealizado.getData().compareTo(this.data) <= 0)
                    .mapToDouble(planoRealizado -> planoRealizado.getCalorias()).sum());
        });


        tabela.forEach((userName, calorias) -> System.out.println(userName + " -> " + calorias));
        System.out.println("\n\n");
    }

    public void tabelaUtilizadorAtividadesRealizadas(){
        TreeMap<String, Integer> tabela = new TreeMap<>(Comparator.reverseOrder());

        this.getPlanosRealizados().getPlanosRealizados().forEach((userName, planos) ->{
            tabela.put(userName, planos.stream()
                    .filter(planoRealizado -> planoRealizado.getData().compareTo(this.dataI) > 0 && planoRealizado.getData().compareTo(this.data) <= 0)
                    .mapToInt(planoRealizado -> this.getPlanosDeTreino().getPlanosTreino(userName).stream()
                            .filter(planoTreino -> planoTreino.getNome().equals(planoRealizado.getNomePlano()))
                            .mapToInt(planoTreino -> planoTreino.getAtividades().size())
                            .sum())
                    .sum());
        });

        tabela.forEach((userName, atividades) -> System.out.println(userName + " -> " + atividades));
        System.out.println("\n\n");
    }

    public void tabelaAtividadeMaisRealizada(){
        TreeMap<String, Integer> tabela = new TreeMap<>(Comparator.reverseOrder());

        this.getPlanosRealizados().getPlanosRealizados().forEach((userName, planos) ->{
            for (PlanoRealizado plano : planos) {
                if(plano.getData().compareTo(this.dataI) > 0 && plano.getData().compareTo(this.data) <= 0){
                    this.getPlanosDeTreino().getPlanosTreino(userName).stream().filter(planoTreino -> planoTreino.getNome().equals(plano.getNomePlano()))
                            .forEach(planoTreino -> planoTreino.getAtividades().forEach(atividade -> {
                                if(tabela.containsKey(atividade.getNomeAtividade())){
                                    tabela.put(atividade.getNomeAtividade(), tabela.get(atividade.getNomeAtividade()) + 1);
                                }else{
                                    tabela.put(atividade.getNomeAtividade(), 1);
                                }
                            }));

                }
            }
        });

        tabela.forEach((atividade, vezes) -> System.out.println(atividade + " -> " + vezes));
        System.out.println("\n\n");
    }

}
