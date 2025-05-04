import Atividades.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.Collectors;
import java.util.HashMap;


public class TextoInterface {
    private Fitness model;

    private Scanner scanner;

    public TextoInterface(){
        this.model = new Fitness();
        this.scanner = new Scanner(System.in);
        this.scanner.useDelimiter("\n");
    }

    public void run(){
        this.menuPrincipal();
    }

    private void limparEcra(){
        System.out.print("\033[H\033[2J");
        scanner.nextLine();
        System.out.flush();

    }

    private void menuPrincipal(){
        Menu menu = new Menu("App Fitness", new String[]{"Login", "Registar", "Sair"});
        menu.setHandler(1, () -> this.login());
        menu.setHandler(2, () -> this.registar());
        menu.setHandler(3, () -> this.sair());

        menu.run();
    }

    private void menuInicial(){
        this.limparEcra();
        Menu menu = new Menu("Menu Inicial", new String[]{"Atividades", "Planos de Treino","Defenições", "Tabelas", "Sair"});
        menu.setHandler(1, () -> this.atividades());
        menu.setHandler(2, () -> this.planosDeTreino());
        menu.setHandler(3, () -> this.definicoes());
        menu.setHandler(4, () -> this.tabelas());
        menu.setHandler(5, () -> this.sair());

        menu.run();
    }

    private void login(){
        this.limparEcra();
        System.out.println("Insira o seu nome de utilizador: ");
        String username = scanner.nextLine();
        System.out.println("Insira a sua palavra-passe: ");
        String password = scanner.nextLine();

        if(!this.model.login(username, password)) {
            System.out.println("Informações de login inválidas");
            this.menuPrincipal();
        }

        this.menuInicial();
    }

    private void registar(){
        this.limparEcra();
        Acesso novoUtilizador = new Acesso();
        System.out.println("Insira o seu nome de utilizador: ");
        novoUtilizador.setUserName(scanner.nextLine());
        System.out.println("Insira a sua palavra-passe: ");
        novoUtilizador.setSenha(scanner.nextLine());
        System.out.println("Insira o seu nome: ");
        novoUtilizador.setNome(scanner.nextLine());
        System.out.println("Insira o seu email: ");
        novoUtilizador.setMail(scanner.nextLine());
        System.out.println("Insira a sua morada: ");
        novoUtilizador.setMorada(scanner.nextLine());
        System.out.println("Insira o seu peso: ");
        novoUtilizador.setPeso(scanner.nextDouble());
        System.out.println("Insira a sua frequência cardíaca: ");
        novoUtilizador.setFrequenciaCardiaca(scanner.nextInt());
        System.out.println("Insira o seu tipo de utilizador: (Amador, Profissional, Ocasional)");
        String tipoUtilizador = scanner.next();
        novoUtilizador.setTipodeUtilizador(tipoUtilizador);


        if(this.model.registar(novoUtilizador)){
            System.out.println("Utilizador registado com sucesso");
            this.menuInicial();
        }else{
            System.out.println("Erro ao registar utilizador");
            this.menuPrincipal();
        }

    }

    private void atividades(){
        this.limparEcra();
        Menu menuAtivdades = new Menu("Atividades", new String[]{"Adicionar Atividade", "Remover Atividade", "Listar Atividades", "Voltar ao Menu Inicial"});
        menuAtivdades.setHandler(1, () -> this.adicionarAtividade());
        menuAtivdades.setHandler(2, () -> this.removerAtividade());
        menuAtivdades.setHandler(3, () -> this.listarAtividades());
        menuAtivdades.setHandler(4, () -> this.menuInicial());

        menuAtivdades.run();

    }

    private void adicionarAtividade(){
        this.limparEcra();
        Menu menuAdicionarAtividades = new Menu("Adicionar Atividade", new String[]{"Resistencia", "Trilha", "Series", "Series com pesos", "Voltar ao Menu Atividades"});
        menuAdicionarAtividades.setHandler(1, () -> this.model.getAtividades().criarAtividade(this.resistencia()));
        menuAdicionarAtividades.setHandler(2, () -> this.model.getAtividades().criarAtividade(this.trilha()));
        menuAdicionarAtividades.setHandler(3, () -> this.model.getAtividades().criarAtividade(this.series()));
        menuAdicionarAtividades.setHandler(4, () -> this.model.getAtividades().criarAtividade(this.seriesComPesos()));
        menuAdicionarAtividades.setHandler(5, () -> this.atividades());

        menuAdicionarAtividades.run();
    }

    private Resistencia resistencia(){
        this.limparEcra();
        Resistencia resistencia = new Resistencia();
        System.out.println("Insira o nome da atividade: ");
        resistencia.setNomeAtividade(scanner.nextLine());
        System.out.println("Insira a duração da atividade: (minutos)");
        resistencia.setDuracao(scanner.nextInt());
        System.out.println("Insira a distância que pertende percorrer: (m)");
        resistencia.setDistancia(scanner.nextDouble());
        System.out.println("Insira a velociade média que pretende atingir: (km/h)");
        resistencia.setVelocidade(scanner.nextDouble());
        resistencia.setCalorias(resistencia.calorias(this.model.getUtilizador().getPeso(), this.model.getUtilizador().getFrequenciaCardiaca(), this.model.getUtilizador().getTipodeutilizador().getFator()));
        scanner.nextLine();
        return resistencia;
    }

    private Trilha trilha(){
        this.limparEcra();
        Trilha trilha = new Trilha();
        System.out.println("Insira o nome da atividade: ");
        trilha.setNomeAtividade(scanner.nextLine());
        System.out.println("Insira a duração da atividade: (minutos)");
        trilha.setDuracao(scanner.nextInt());
        System.out.println("Insira a distância que pertende percorrer: (m)");
        trilha.setDistancia(scanner.nextDouble());
        System.out.println("Insira a altura que pertende alcançar: ");
        trilha.setAltura(scanner.nextInt());

        trilha.setCalorias(trilha.calorias(this.model.getUtilizador().getPeso(), this.model.getUtilizador().getFrequenciaCardiaca(), this.model.getUtilizador().getTipodeutilizador().getFator()));
        scanner.nextLine();
        return trilha;
    }

    private Series series(){
        this.limparEcra();
        Series series = new Series();
        System.out.println("Insira o nome da atividade: ");
        series.setNomeAtividade(scanner.nextLine());
        System.out.println("Insira a duração da atividade: (minutos)");
        series.setDuracao(scanner.nextInt());
        System.out.println("Insira o número de séries que pretende fazer: ");
        series.setSeries(scanner.nextInt());
        System.out.println("Insira o número de repetições que pretende fazer: ");
        List<Integer> repeticoes = new ArrayList<>();
        for(int i = 0; i < series.getSeries(); i++){
            System.out.println("Insira o número de repetições da série " + (i+1) + ": ");
            repeticoes.add(scanner.nextInt());
        }
        series.setRepeticoes(repeticoes);

        System.out.println("Tempo que pertende descansar entre séries: (segundos)");
        series.setTempoDescanso(scanner.nextInt());

        series.setCalorias(series.calorias(this.model.getUtilizador().getPeso(), this.model.getUtilizador().getFrequenciaCardiaca(), this.model.getUtilizador().getTipodeutilizador().getFator()));
        scanner.nextLine();
        return series;
    }

    private SeriesPesos seriesComPesos(){
        this.limparEcra();
        SeriesPesos series = new SeriesPesos();
        System.out.println("Insira o nome da atividade: ");
        series.setNomeAtividade(scanner.nextLine());
        System.out.println("Insira a duração da atividade: (minutos)");
        series.setDuracao(scanner.nextInt());
        System.out.println("Insira o número de séries que pretende fazer: ");
        series.setSeries(scanner.nextInt());
        System.out.println("Insira o número de repetições que pretende fazer: ");
        List<Integer> repeticoes = new ArrayList<>();
        for(int i = 0; i < series.getSeries(); i++){
            System.out.println("Insira o número de repetições da série " + (i+1) + ": ");
            repeticoes.add(scanner.nextInt());
        }
        series.setRepeticoes(repeticoes);
        System.out.println("Insira o peso que pretende levantar: ");
        series.setPeso(scanner.nextDouble());
        System.out.println("Tempo que pertende descansar entre séries: (segundos)");
        series.setTempoDescanso(scanner.nextInt());

        series.setCalorias(series.calorias(this.model.getUtilizador().getPeso(), this.model.getUtilizador().getFrequenciaCardiaca(), this.model.getUtilizador().getTipodeutilizador().getFator()));
        scanner.nextLine();
        return series;
    }

    private void removerAtividade(){
        this.limparEcra();
        System.out.println("Insira o nome da atividade que pretende remover: ");
        String nomeAtividade = scanner.nextLine();
        scanner.nextLine();
        this.model.getAtividades().apagarAtividade(nomeAtividade);
    }

    private void listarAtividades(){
        this.limparEcra();
        this.model.getAtividades().listarAtividades();
    }

    private void listarAtividades(Atividades atividades){
        this.limparEcra();
        atividades.listarAtividades();
    }

    private void escolherAtividadesPorTipo(Class<?> classe){
        this.limparEcra();

        List<Atividade> atividadeList = this.model.getAtividades().listarAtividadesPorTipo(classe);

        List<String> list= atividadeList.stream()
                .map(Atividade::getNomeAtividade)
                .collect(Collectors.toCollection(ArrayList::new));
        list.add("Guardar plano");
        list.add("Voltar para o menu planos de treino");

        String[] opcoes = list.toArray(new String[list.size()]);

        Menu menuAtividadesTipo = new Menu("Criar plano - \"" + classe.getSimpleName() + "\"", opcoes);

        PlanoTreino plano = new PlanoTreino();
        System.out.println("Insira o nome do plano de treino:");
        plano.setNome(scanner.nextLine());
        System.out.println("Insira o dia da semana que pertende treinar:");
        plano.setDiaSemana(scanner.nextLine());

        List<Atividade> atividadesPlano = new ArrayList<>();
        int i;
        for(i = 1; i < atividadeList.size() + 1; i++){
            int index = i;
            menuAtividadesTipo.setHandler(i, () -> {
                atividadesPlano.add(atividadeList.get(index - 1));
                menuAtividadesTipo.setPreCondicao(index, () -> false);
            });
        }


        menuAtividadesTipo.setHandler(i, () -> {
            plano.setAtividades(atividadesPlano);
            this.model.adicionarPlanoTreino(plano);
            this.planosDeTreino();
        });
        menuAtividadesTipo.setPreCondicao(i, () -> !atividadesPlano.isEmpty());
        menuAtividadesTipo.setHandler(i + 1, () -> this.planosDeTreino());
      

        menuAtividadesTipo.run();
    }

    private void planosDeTreino(){
        this.limparEcra();
        Menu menuPlanosDeTreino = new Menu("Planos de Treino", new String[]{"Adicionar Plano de Treino", "Remover Plano de Treino", "Listar Planos de Treino e Atividades", "Marcar Plano de Treino como realizado", "Listar Planos de Treino Realizados", "Voltar ao Menu Inicial"});
        menuPlanosDeTreino.setHandler(1, () -> this.criarPlanoDeTreino());
        menuPlanosDeTreino.setHandler(2, () -> this.removerPlanoDeTreino());
        menuPlanosDeTreino.setHandler(3, () -> this.listarPlanosDeTreino());
        menuPlanosDeTreino.setHandler(4, () -> this.marcarPlanoRealizado());
        menuPlanosDeTreino.setHandler(5, () -> this.listarAtividadesRealizadas());
        menuPlanosDeTreino.setHandler(6, () -> this.menuInicial());

        for(int i = 2; i <= 5; i++)
            menuPlanosDeTreino.setPreCondicao(i, () -> this.model.getPlanosDeTreino().getPlanosDeTreino().containsKey(this.model.getUtilizador().getUserName()));

        menuPlanosDeTreino.run();
    }

    public void criarPlanoDeTreino(){
        this.limparEcra();
        Menu menuTipoAtividades = new Menu("Tipo de Atividades", new String[]{"Resistencia", "Trilha", "Series", "Series com pesos", "Voltar ao Menu Planos de Treino"});
        menuTipoAtividades.setHandler(1, () -> this.escolherAtividadesPorTipo(Resistencia.class));
        menuTipoAtividades.setHandler(2, () -> this.escolherAtividadesPorTipo(Trilha.class));
        menuTipoAtividades.setHandler(3, () -> this.escolherAtividadesPorTipo(Series.class));
        menuTipoAtividades.setHandler(4, () -> this.escolherAtividadesPorTipo(SeriesPesos.class));
        menuTipoAtividades.run();
    }

    public void removerPlanoDeTreino(){
        this.limparEcra();
        List<PlanoTreino> planos = this.model.getPlanosTreino();
        List<String> opcoes = planos.stream().map(planoTreino -> planoTreino.getNome()).collect(Collectors.toCollection(ArrayList::new));
        opcoes.add("Voltar para o menu planos de treino");

        Menu menuRemoverPlano = new Menu("Remover plano", opcoes.toArray(new String[opcoes.size()]));
        int i;
        for(i = 1; i < planos.size() + 1; i++){
            int index  = i;
            menuRemoverPlano.setHandler(i, () -> {
                this.model.removerPlanoTreino(planos.get(index - 1));
                menuRemoverPlano.setPreCondicao(index, () -> false);
            });
        }
        menuRemoverPlano.setHandler(i, () -> this.planosDeTreino());
        menuRemoverPlano.run();
    }

    public void listarPlanosDeTreino(){
        this.limparEcra();
        List<PlanoTreino> planos = this.model.getPlanosTreino();
        List<String> opcoes = planos.stream().map(planoTreino -> planoTreino.getNome()).collect(Collectors.toCollection(ArrayList::new));
        opcoes.add("Voltar para o menu planos de treino");

        Menu listarPlanosAtividades = new Menu("Listar Planos de Treino e Atividades", opcoes.toArray(new String[opcoes.size()]));
        int i;

        for(i = 1; i < planos.size() + 1; i++){
            int index  = i;
            listarPlanosAtividades.setHandler(index, () -> this.listarAtividades(new Atividades(planos.get(index - 1).getAtividades())));
        }

        listarPlanosAtividades.setHandler(i, () -> this.planosDeTreino());
        listarPlanosAtividades.run();
    }

    public void marcarPlanoRealizado(){
        this.limparEcra();
        List<PlanoTreino> planos = this.model.getPlanosTreino();
        List<String> opcoes = planos.stream().map(planoTreino -> planoTreino.getNome()).collect(Collectors.toCollection(ArrayList::new));
        opcoes.add("Voltar para o menu planos de treino");

        Menu menuMarcarPlanoRealizado = new Menu("Marcar Plano Realizado", opcoes.toArray(new String[opcoes.size()]));
        int i;

        for(i = 1; i < planos.size() + 1; i++){
            int index  = i;
            menuMarcarPlanoRealizado.setHandler(index, () -> {
                System.out.println("Insira a frequência cardíaca média durante a realização do plano de treino: ");

                this.model.adicionarPlanoRealizado(planos.get(index - 1), scanner.nextInt());
            });
        }
        scanner.nextLine();

        menuMarcarPlanoRealizado.setHandler(i, () -> this.planosDeTreino());
        menuMarcarPlanoRealizado.run();
    }

    public void listarAtividadesRealizadas(){
        this.limparEcra();
        this.model.listarAtividadesRealizadas();
    }
    public void definicoes(){
        this.limparEcra();
        Menu menuDefinicoes = new Menu("Definições", new String[]{"Ver Data", "Mudar Data", "Voltar ao Menu Inicial"});
        menuDefinicoes.setHandler(1, () -> this.verdata());
        menuDefinicoes.setHandler(2, () -> this.mudardata());
        menuDefinicoes.setHandler(3, () -> this.menuInicial());
        menuDefinicoes.run();
    }
    public void verdata(){
        this.limparEcra();
        System.out.println("Data: " + this.model.getData());
        Menu menuPlanosDeTreino = new Menu("Ver Data", new String[]{ "Voltar as defeinições"});
        menuPlanosDeTreino.setHandler(1, () -> this.definicoes());
        menuPlanosDeTreino.run();
    }
    public void mudardata(){
        this.limparEcra();
        System.out.println("Insira o dia: ");
        int dia = scanner.nextInt();
        System.out.println("Insira o mês: ");
        int mes = scanner.nextInt();
        System.out.println("Insira o ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        LocalDate dataAtual = LocalDate.now();
        LocalDate novaData = LocalDate.of(ano, mes, dia);
        while (!dataAtual.isEqual(novaData)) {
            HashMap<String, List<PlanoTreino>> planos = this.model.getPlanosDeTreino().getPlanosDeTreino();

            LocalDate finalDataAtual = dataAtual;
            planos.forEach((nome, listaPlanos) -> {
                for (PlanoTreino plano : listaPlanos) {
                    if (plano.getDiaSemana().equalsIgnoreCase(finalDataAtual.getDayOfWeek().toString())) {
                        // Executar as atividades planejadas para este dia
                        this.model.adicionarPlanoRealizado(nome, plano, finalDataAtual);
                    }
                }
            });

            
            // Avançar para o próximo dia
            dataAtual = dataAtual.plusDays(1);
        }
        this.model.setData(novaData);

        this.menuInicial();
    }

    public void tabelas(){
        this.limparEcra();
        Menu menuTabelas = new Menu("Tabelas", new String[]{"Tabela Calorias", "Tabela Utilizador Com Mais Atividades Realizadas", "Tabela Atividade Mais Realizada", "Alterar Intervalo de Tempo", "Voltar ao Menu Inicial"});
        menuTabelas.setHandler(1, () -> this.tabelaCalorias());
        menuTabelas.setHandler(2, () -> this.tabelaAtividadesRealizadas());
        menuTabelas.setHandler(3, () -> this.tabelaAtividadeMaisRealizada());
        menuTabelas.setHandler(4, () -> this.alterarIntervaloTempo());
        menuTabelas.setHandler(5, () -> {
            this.menuInicial();
            this.model.setDataI(LocalDate.of(2021, 01, 01));
            this.model.setData(LocalDate.now());
        });
        menuTabelas.run();
    }

    public void tabelaCalorias(){
        this.limparEcra();

        this.model.tabelaCalorias();
    }

    public void tabelaAtividadesRealizadas(){
        this.limparEcra();

        this.model.tabelaUtilizadorAtividadesRealizadas();
    }

    public void tabelaAtividadeMaisRealizada(){
        this.limparEcra();

        this.model.tabelaAtividadeMaisRealizada();
    }

    public void alterarIntervaloTempo(){
        this.limparEcra();

        Menu menuAlterarIntervalo = new Menu("Alterar Intervalo de Tempo", new String[]{"Alterar Data de Inicio", "Alterar Data de Fim", "Voltar ao Menu Tabelas"});
        menuAlterarIntervalo.setHandler(1, () ->  this.model.setDataI(this.alterarData()));
        menuAlterarIntervalo.setHandler(2, () ->  this.model.setData(this.alterarData()));
        menuAlterarIntervalo.setHandler(3, () -> this.tabelas());
        menuAlterarIntervalo.run();

    }

    public LocalDate alterarData() {
        this.limparEcra();
        System.out.println("Insira o dia: ");
        int dia = scanner.nextInt();
        System.out.println("Insira o mês: ");
        int mes = scanner.nextInt();
        System.out.println("Insira o ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        return LocalDate.of(ano, mes, dia);
    }
     
    private void sair(){
        this.limparEcra();
        System.out.println("Aguardar algumas informações...");
        this.model.getPlanosDeTreino().guardarPlanosDeTreino();
        this.model.getAtividades().guardarAtividades();
        this.model.getPlanosRealizados().guardarPlanosRealizados();

        System.exit(0);
    }
}
