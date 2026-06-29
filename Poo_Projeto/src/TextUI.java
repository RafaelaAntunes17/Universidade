import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextUI {
    private DomusControl model;
    private Utilizador utilizadorAtivo;
    private final Scanner scanner = Menu.is;

    public TextUI() {
        this.model = new DomusControl();
        this.utilizadorAtivo = null;
    }

    public TextUI(DomusControl model) {
        this.model = model;
        this.utilizadorAtivo = null;
    }

    public DomusControl getModel() {
        return this.model;
    }

    public DomusControl run() {
        Menu menuSessao = new Menu("DOMUS CONTROL", new String[] {
                "Entrar na conta",
                "Criar conta",
                "Carregar estado de ficheiro"
        });

        menuSessao.setHandler(1, () -> entrarNaConta());
        menuSessao.setHandler(2, () -> novoUtilizador());
        menuSessao.setHandler(3, () -> carregarEstado());

        menuSessao.run();

        System.out.println("A sair do Domus Control. Até breve!");
        return this.model;
    }

    private int lerInteiro(String pergunta) {
        while (true) {
            System.out.print(pergunta);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Número inválido.");
            }
        }
    }

    private double lerDouble(String pergunta) {
        while (true) {
            System.out.print(pergunta);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Número inválido.");
            }
        }
    }

    private String lerStringSemEspacos(String pergunta) {
        while (true) {
            System.out.print(pergunta);
            String input = scanner.nextLine();
            if (input.contains(" ")) {
                System.out.println("Erro: Não pode conter espaços.");
            } else if (input.isEmpty()) {
                System.out.println("Erro: Não pode ser vazio.");
            } else {
                return input;
            }
        }
    }

    private void pausa() {
        System.out.print("\nPrima enter para continuar...");
        scanner.nextLine();
    }

    private void mostrarLista(List<String> items) {
        if (items.isEmpty()) {
            System.out.println(" Não existe informação");
        } else {
            for (int i = 0; i < items.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + items.get(i));
            }
        }
    }

    private void entrarNaConta() {
        fazerLogin();
        if (utilizadorAtivo != null) {
            menuPrincipal();
        }
    }

    private void fazerLogin() {
        String id = lerStringSemEspacos("Nome de utilizador: ");
        String password = lerStringSemEspacos("Password: ");
        try {
            this.utilizadorAtivo = model.autenticar(id, password);
        } catch (UtilizadorInexistenteException e) {
            System.out.println("As credenciais estão incorretas.");
            pausa();
        }
    }

    private void novoUtilizador() {
        String id = lerStringSemEspacos("Nome de utilizador: ");
        System.out.println("Nome completo: ");
        String nome = scanner.nextLine();
        String password = lerStringSemEspacos("Password: ");
        try {
            model.adicionarUtilizador(id, password, nome);
            System.out.println("Conta criada com sucesso!");
        } catch (UtilizadorRepetidoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        pausa();
    }

    private void menuPrincipal() {
        Menu menu = new Menu(" Olá, " + utilizadorAtivo.getNome() + " | " + model.getHoraFormatada(),
                new String[] {
                        "Meus Dados",
                        "Ver Casas | Divisões | Dispositivos ",
                        "Criar nova Casa",
                        "Simular passagem do tempo",
                        "Simular ambiente",
                        "Gravar estado"
                });

        menu.setHandler(1, () -> menuEstatisticas());
        menu.setHandler(2, () -> verCasas());
        menu.setHandler(3, () -> criarCasa());
        menu.setHandler(4, () -> {
            menuSimulacaoTempo();
            menu.setTitulo(" Olá, " + utilizadorAtivo.getNome() + " | " + model.getHoraFormatada());
        });
        menu.setHandler(5, () -> simularAmbiente());
        menu.setHandler(6, () -> gravarEstado());

        menu.run();
        this.utilizadorAtivo = null;
    }

    private void menuSimulacaoTempo() {
        Menu menu = new Menu("Simulação de Tempo | Hora atual: " + model.getHoraFormatada(),
                new String[] {
                        "Avançar X minutos",
                        "Avançar X horas",
                        "Avançar até uma hora específica (HH:MM)",
                });

        menu.setHandler(1, () -> {
            avancarMinutos();
            menu.setTitulo("Simulação de Tempo | Hora atual: " + model.getHoraFormatada());
        });
        menu.setHandler(2, () -> {
            avancarHoras();
            menu.setTitulo("Simulação de Tempo | Hora atual: " + model.getHoraFormatada());
        });
        menu.setHandler(3, () -> {
            avancarParaHora();
            menu.setTitulo("Simulação de Tempo | Hora atual: " + model.getHoraFormatada());
        });

        menu.run();
    }

    private void simularAmbiente() {
        List<String> casasAdmin = utilizadorAtivo.getCasasAdmin();
        List<String> casasUsufruto = utilizadorAtivo.getCasasUsufruto();
        if (casasAdmin.isEmpty() && casasUsufruto.isEmpty()) {
            System.out.println("Não tem casas.");
            pausa();
            return;
        }
        System.out.println("Casas admin: ");
        mostrarLista(casasAdmin);
        System.out.println("Casas usufruto: ");
        mostrarLista(casasUsufruto);
        System.out.println("Nome da casa: ");
        String idCasa = scanner.nextLine();
        if (!model.existeCasa(idCasa)) {
            System.out.println("Casa não encontrada.");
            pausa();
            return;
        }

        System.out.println("\nAmbiente atual: " + model.getAmbiente(idCasa));

        Menu menu = new Menu("Simular Ambiente - Casa: " + idCasa, new String[] {
                "Alterar luminosidade",
                "Alterar Precipitação (chuva)"
        });
        menu.setHandler(1, () -> {
            double lux = lerDouble("Luminosidade em lux (ex: 500=dia, 100=escuro): ");
            model.setLuminosidade(idCasa, lux);
            System.out.println("Luminosidade definida para " + lux + " lux.");
            pausa();
        });
        menu.setHandler(2, () -> {
            double mmh = lerDouble("Precipitação em mm/h (0=sem chuva, >5=chuva): ");
            model.setPrecipitacao(idCasa, mmh);
            System.out.println("Precipitação definida para " + mmh + " mm/h.");
            pausa();
        });
        menu.run();
    }

    private void avancarMinutos() {
        try {
            int min = lerInteiro("Quantos minutos avançar? ");
            model.avancarTempo(min);
            System.out.println("Hora atual: " + model.getHoraFormatada());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        pausa();
    }

    private void avancarHoras() {
        try {
            int h = lerInteiro("Quantas horas avançar? ");
            model.avancarTempo(h * 60);
            System.out.println("Hora atual: " + model.getHoraFormatada());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        pausa();
    }

    private void avancarParaHora() {
        try {
            int h = lerInteiro("Hora (0-23): ");
            int m = lerInteiro("Minuto (0-59): ");
            model.avancarParaHora(h, m);
            System.out.println("Hora atual: " + model.getHoraFormatada());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        pausa();
    }

    private void criarCasa() {
        String nome = lerStringSemEspacos("Nome da casa: ");
        System.out.println("Morada: ");
        String morada = scanner.nextLine();

        try {
            model.criarCasa(nome, morada, utilizadorAtivo.getUserName());
            this.utilizadorAtivo = model.getUtilizador(utilizadorAtivo.getUserName());
            System.out.println("Casa '" + nome + "' criada com sucesso!");
        } catch (CasaRepetidaException | UtilizadorInexistenteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        pausa();
    }

    private void verCasas() {
        List<String> admin = utilizadorAtivo.getCasasAdmin();
        List<String> usufruto = utilizadorAtivo.getCasasUsufruto();

        if (admin.isEmpty() && usufruto.isEmpty()) {
            System.out.println("Ainda não tem casas. Cria uma nova para começar.");
            pausa();
            return;
        }

        System.out.println("Casas admin: ");
        mostrarLista(admin);
        System.out.println("Casas usufruto: ");
        mostrarLista(usufruto);

        System.out.println("\nNome da casa (ou vazio para voltar): ");

        String idCasa = scanner.nextLine();
        if (idCasa.isEmpty())
            return;

        if (!model.existeCasa(idCasa)) {
            System.out.println("Erro: Casa não existe.");
            pausa();
            return;
        }

        if (admin.contains(idCasa))
            menuAdminCasa(idCasa);
        else if (usufruto.contains(idCasa))
            menuUserCasa(idCasa);
        else {
            System.out.println("Erro: Não tem permissão para aceder a esta casa.");
            pausa();
        }
    }

    private void menuAdminCasa(String idCasa) {
        Menu menu = new Menu("Admin - Casa: " + idCasa, new String[] {
                "Ver Divisões | Dispositivos",
                "Adicionar Divisão",
                "Remover Divisão",
                "Convidar Utilizador (usufrutuário)",
                "Ver Automações e Escalonamentos",
                "Cenários",

        });

        menu.setHandler(1, () -> verDivisoes(idCasa, true));
        menu.setHandler(2, () -> adicionarDivisao(idCasa));
        menu.setHandler(3, () -> removerDivisao(idCasa));
        menu.setHandler(4, () -> convidarUtilizador(idCasa));
        menu.setHandler(5, () -> listarAutomacoesEscalonamentos(idCasa));
        menu.setHandler(6, () -> menuCenarios(idCasa));


        menu.run();
    }

    private void menuUserCasa(String idCasa) {
        Menu menu = new Menu("Usufrutuário - Casa: " + idCasa, new String[] {
                "Ver Divisões | Dispositivos",
                "Ver Automações e Escalonamentos",
                "Cenários"
        });
        menu.setHandler(1, () -> verDivisoes(idCasa, false));
        menu.setHandler(2, () -> listarAutomacoesEscalonamentos(idCasa));
        menu.setHandler(3, () -> menuCenarios(idCasa));
        menu.run();
    }

    private void verDivisoes(String idCasa, boolean isAdmin) {
        try {
            List<String> divs = model.listarDivisoes(idCasa);
            if (divs.isEmpty()) {
                System.out.println("Esta casa não tem divisões.");
                pausa();
                return;
            }
            System.out.println("Divisões da casa '" + idCasa + "':");
            mostrarLista(divs);
            System.out.println("\nNome da divisão (ou vazio para voltar): ");
            String nomeDivisao = scanner.nextLine();
            if (nomeDivisao.isEmpty())
                return;

            if (!model.listarNomesDivisoes(idCasa).contains(nomeDivisao)) {
                System.out.println("Erro: A divisão não foi encontrada.");
                pausa();
                return;
            }

            if (isAdmin)
                menuAdminDivisao(idCasa, nomeDivisao);
            else
                menuUserDivisao(idCasa, nomeDivisao);

        } catch (CasaInexistenteException e) {
            System.out.println("Erro: " + e.getMessage());
            pausa();
        }
    }

    private void adicionarDivisao(String idCasa) {
        System.out.println("Nome da nova divisão: ");
        String nome = scanner.nextLine();
        try {
            model.adicionarDivisaoACasa(idCasa, nome);
            System.out.println("Divisão '" + nome + "' adicionada.");
        } catch (CasaInexistenteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        pausa();
    }

    private void removerDivisao(String idCasa) {
        List<String> nomes = model.listarNomesDivisoes(idCasa);
        if (nomes.isEmpty()) {
            System.out.println("Esta casa não tem divisões.");
            pausa();
            return;
        }
        mostrarLista(nomes);
        int escolha = lerInteiro("Número da divisão a remover (0 para cancelar): ");
        if (escolha > 0 && escolha <= nomes.size()) {
            String nome = nomes.get(escolha - 1);
            try {
                model.removerDivisaoDeCasa(idCasa, nome);
                System.out.println("Divisão '" + nome + "' removida.");
            } catch (CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else if (escolha != 0) {
            System.out.println("Opção inválida.");
        }
        pausa();
    }

    private void menuAdminDivisao(String idCasa, String nomeDivisao) {
        Menu menu = new Menu("Casa: " + idCasa + " > " + nomeDivisao, new String[] {
                "Listar Dispositivos",
                "Adicionar Dispositivo",
                "Remover Dispositivo",
                "Interagir com Dispositivo"
        });
        menu.setHandler(1, () -> listarDispositivos(idCasa, nomeDivisao));
        menu.setHandler(2, () -> adicionarDispositivo(idCasa, nomeDivisao));
        menu.setHandler(3, () -> removerDispositivo(idCasa, nomeDivisao));
        menu.setHandler(4, () -> escolherEInteragir(idCasa, nomeDivisao));
        menu.run();
    }

    private void menuUserDivisao(String idCasa, String nomeDivisao) {
        Menu menu = new Menu("Casa: " + idCasa + " > " + nomeDivisao, new String[] {
                "Listar Dispositivos",
                "Interagir com Dispositivo"
        });
        menu.setHandler(1, () -> listarDispositivos(idCasa, nomeDivisao));
        menu.setHandler(2, () -> escolherEInteragir(idCasa, nomeDivisao));
        menu.run();
    }

    private void listarDispositivos(String idCasa, String nomeDivisao) {
        System.out.println("Dispositivos da divisão '" + nomeDivisao + "':");
        mostrarLista(model.listarDispositivos(idCasa, nomeDivisao));
        pausa();
    }

    private void adicionarDispositivo(String idCasa, String nomeDivisao) {
        Menu menu = new Menu("Adicionar Dispositivo", new String[] {
                "Relé", "Lâmpada", "Coluna de Som", "Estore", "Portão"
        });

        menu.setHandler(1, () -> criarEAdicionarDispositivo(idCasa, nomeDivisao, "rele"));
        menu.setHandler(2, () -> criarEAdicionarDispositivo(idCasa, nomeDivisao, "lampada"));
        menu.setHandler(3, () -> criarEAdicionarDispositivo(idCasa, nomeDivisao, "coluna"));
        menu.setHandler(4, () -> criarEAdicionarDispositivo(idCasa, nomeDivisao, "estore"));
        menu.setHandler(5, () -> criarEAdicionarDispositivo(idCasa, nomeDivisao, "portao"));

        menu.run();
    }

    private void criarEAdicionarDispositivo(String idCasa, String nomeDivisao, String tipo) {
        System.out.println("Marca: ");
        String marca = scanner.nextLine();
        System.out.println("Modelo: ");
        String modelo = scanner.nextLine();
        double consumo = lerDouble("Consumo por hora (Wh): ");

        try {
            String id = model.adicionarDispositivo(idCasa, nomeDivisao, tipo, marca, modelo, consumo);
            System.out.println("Dispositivo '" + id + "' adicionado.");
        } catch (CasaInexistenteException | IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        pausa();
    }

    private void removerDispositivo(String idCasa, String nomeDivisao) {
        List<String> disps = model.listarDispositivos(idCasa, nomeDivisao);
        if (disps.isEmpty()) {
            System.out.println("Esta divisão não tem dispositivos.");
            pausa();
            return;
        }
        mostrarLista(disps);
        System.out.println("ID do dispositivo a remover: ");
        String idDisp = scanner.nextLine();
        try {
            model.removerDispositivoDeDivisao(idCasa, nomeDivisao, idDisp);
            System.out.println("Dispositivo removido.");
        } catch (CasaInexistenteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        pausa();
    }

    private void escolherEInteragir(String idCasa, String nomeDivisao) {
        List<String> disps = model.listarDispositivos(idCasa, nomeDivisao);
        if (disps.isEmpty()) {
            System.out.println("Esta divisão não tem dispositivos.");
            pausa();
            return;
        }
        mostrarLista(disps);
        System.out.println("ID do dispositivo: ");
        String idDisp = scanner.nextLine();
        try {
            Dispositivo d = model.getDispositivo(idCasa, nomeDivisao, idDisp);
            interagirComDispositivo(d, idCasa, nomeDivisao);

        } catch (CasaInexistenteException | DispositivoInexistenteException e) {
            System.out.println("Erro: " + e.getMessage());
            pausa();
        }
    }

    private void interagirComDispositivo(Dispositivo d, String idCasa, String nomeDivisao) {
        if (d instanceof Lampada) {
            menuLampada((Lampada) d, idCasa, nomeDivisao);
        } else if (d instanceof ColunaSom) {
            menuColunaSom((ColunaSom) d, idCasa, nomeDivisao);
        } else if (d instanceof Estore) {
            menuEstore((Estore) d, idCasa, nomeDivisao);
        } else if (d instanceof Portao) {
            menuPortao((Portao) d, idCasa, nomeDivisao);
        } else if (d instanceof Rele) {
            menuRele((Rele) d, idCasa, nomeDivisao);
        } else {
            System.out.println("Tipo de dispositivo desconhecido.");
            pausa();
        }
    }

    private void menuRele(Rele r, String idCasa, String nomeDivisao) {
        Menu menu = new Menu(r.toStringDetalhado(), new String[] {
                "Ligar", "Desligar"
        });

        menu.setPreCondition(1, () -> !r.isLigado());
        menu.setPreCondition(2, () -> r.isLigado());

        menu.setHandler(1, () -> {
            try {
                r.ligar(model.getTempoEmMinutos());
                model.atualizarDispositivo(idCasa, nomeDivisao, r);
                System.out.println("\n" + r.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            menu.setTitulo(r.toStringDetalhado());
            pausa();
        });
        menu.setHandler(2, () -> {
            try {
                r.desligar(model.getTempoEmMinutos());
                model.atualizarDispositivo(idCasa, nomeDivisao, r);
                System.out.println("\n" + r.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            menu.setTitulo(r.toStringDetalhado());
            pausa();
        });

        menu.run();
    }

    private void menuLampada(Lampada l, String idCasa, String nomeDivisao) {
        Menu menu = new Menu(l.toStringDetalhado(), new String[] {
                "Ligar", "Desligar",
                "Alterar intensidade", "Alterar cor (K)",
                "Modo Eco", "Modo Máxima intensidade"
        });

        menu.setPreCondition(1, () -> !l.isLigado());
        menu.setPreCondition(2, () -> l.isLigado());
        menu.setPreCondition(3, () -> l.isLigado());
        menu.setPreCondition(4, () -> l.isLigado());
        menu.setPreCondition(5, () -> l.isLigado());
        menu.setPreCondition(6, () -> l.isLigado());

        menu.setHandler(1, () -> {
            try {
                l.ligar(model.getTempoEmMinutos());
                model.atualizarDispositivo(idCasa, nomeDivisao, l);
                System.out.println("\n" + l.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            menu.setTitulo(l.toStringDetalhado());
            pausa();
        });
        menu.setHandler(2, () -> {
            try {
                l.desligar(model.getTempoEmMinutos());
                model.atualizarDispositivo(idCasa, nomeDivisao, l);
                System.out.println("\n" + l.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            menu.setTitulo(l.toStringDetalhado());
            pausa();
        });
        menu.setHandler(3, () -> {
            int intensidade = lerInteiro("Nova intensidade (0-100): ");
            try {
                l.setIntensidade(intensidade);
                model.atualizarDispositivo(idCasa, nomeDivisao, l);
                System.out.println("\n" + l.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            menu.setTitulo(l.toStringDetalhado());
            pausa();
        });
        menu.setHandler(4, () -> {
            int cor = lerInteiro("Cor (2700-4000 K): ");
            try {
                l.setCor(cor);
                model.atualizarDispositivo(idCasa, nomeDivisao, l);
                System.out.println("\n" + l.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            menu.setTitulo(l.toStringDetalhado());
            pausa();
        });
        menu.setHandler(5, () -> {
            try {
                l.setEstado(Lampada.Estado.ECO);
                model.atualizarDispositivo(idCasa, nomeDivisao, l);
                System.out.println("\n" + l.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            menu.setTitulo(l.toStringDetalhado());
            pausa();
        });
        menu.setHandler(6, () -> {
            try {
                l.setEstado(Lampada.Estado.MAX);
                model.atualizarDispositivo(idCasa, nomeDivisao, l);
                System.out.println("\n" + l.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            menu.setTitulo(l.toStringDetalhado());
            pausa();
        });

        menu.run();
    }

    private void menuColunaSom(ColunaSom c, String idCasa, String nomeDivisao) {
        Menu menu = new Menu(c.toStringDetalhado(), new String[] {
                "Ligar", "Desligar",
                "Aumentar volume", "Diminuir volume"
        });

        menu.setPreCondition(1, () -> !c.isLigado());
        menu.setPreCondition(2, () -> c.isLigado());
        menu.setPreCondition(3, () -> c.isLigado());
        menu.setPreCondition(4, () -> c.isLigado());

        menu.setHandler(1, () -> {
            try {
                c.ligar(model.getTempoEmMinutos());
                model.atualizarDispositivo(idCasa, nomeDivisao, c);
                System.out.println("\n" + c.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            menu.setTitulo(c.toStringDetalhado());
            pausa();
        });
        menu.setHandler(2, () -> {
            try {
                c.desligar(model.getTempoEmMinutos());
                model.atualizarDispositivo(idCasa, nomeDivisao, c);
                System.out.println("\n" + c.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            menu.setTitulo(c.toStringDetalhado());
            pausa();
        });
        menu.setHandler(3, () -> {
            int valor = lerInteiro("Aumentar volume (1-50): ");
            try {
                c.aumentarVolume(valor);
                model.atualizarDispositivo(idCasa, nomeDivisao, c);
                System.out.println("\n" + c.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            menu.setTitulo(c.toStringDetalhado());
            pausa();
        });
        menu.setHandler(4, () -> {
            int valor = lerInteiro("Diminuir volume (1-50): ");
            try {
                c.diminuirVolume(valor);
                model.atualizarDispositivo(idCasa, nomeDivisao, c);
                System.out.println("\n" + c.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            menu.setTitulo(c.toStringDetalhado());
            pausa();
        });

        menu.run();
    }

    private void menuEstore(Estore e, String idCasa, String nomeDivisao) {
        Menu menu = new Menu(e.toStringDetalhado(), new String[] {
                "Abrir (100%)", "Fechar (0%)", "Abrir parcialmente"
        });

        menu.setPreCondition(1, () -> e.getAbertura() < 100);
        menu.setPreCondition(2, () -> e.getAbertura() > 0);

        menu.setHandler(1, () -> {
            try {
                e.ligar(model.getTempoEmMinutos());
                e.abrir();
                model.atualizarDispositivo(idCasa, nomeDivisao, e);
                System.out.println("\n" + e.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
            menu.setTitulo(e.toStringDetalhado());
            pausa();
        });
        menu.setHandler(2, () -> {
            try {
                e.fechar();
                e.desligar(model.getTempoEmMinutos());
                model.atualizarDispositivo(idCasa, nomeDivisao, e);
                System.out.println("\n" + e.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
            menu.setTitulo(e.toStringDetalhado());
            pausa();
        });
        menu.setHandler(3, () -> {
            int percentagem = lerInteiro("Grau de abertura (1-99): ");
            try {
                e.ligar(model.getTempoEmMinutos());
                e.setAbertura(percentagem);
                model.atualizarDispositivo(idCasa, nomeDivisao, e);
                System.out.println("\n" + e.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
            menu.setTitulo(e.toStringDetalhado());
            pausa();
        });

        menu.run();
    }

    private void menuPortao(Portao p, String idCasa, String nomeDivisao) {
        Menu menu = new Menu(p.toStringDetalhado(), new String[] {
                "Abrir (100%)", "Fechar (0%)", "Abrir parcialmente",
                "Trancar", "Destrancar"
        });

        menu.setPreCondition(1, () -> !p.isTrancado());
        menu.setPreCondition(2, () -> p.getAbertura() > 0);
        menu.setPreCondition(3, () -> !p.isTrancado());
        menu.setPreCondition(4, () -> !p.isTrancado());
        menu.setPreCondition(5, () -> p.isTrancado());

        menu.setHandler(1, () -> {
            try {
                p.ligar(model.getTempoEmMinutos());
                p.abrir();
                model.atualizarDispositivo(idCasa, nomeDivisao, p);
                System.out.println("\n" + p.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            menu.setTitulo(p.toStringDetalhado());
            pausa();
        });
        menu.setHandler(2, () -> {
            try {
                p.fechar();
                p.desligar(model.getTempoEmMinutos());
                model.atualizarDispositivo(idCasa, nomeDivisao, p);
                System.out.println("\n" + p.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            menu.setTitulo(p.toStringDetalhado());
            pausa();
        });
        menu.setHandler(3, () -> {
            int pct = lerInteiro("Grau de abertura (1-99): ");
            try {
                p.ligar(model.getTempoEmMinutos());
                p.setAbertura(pct);
                model.atualizarDispositivo(idCasa, nomeDivisao, p);
                System.out.println("\n" + p.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            menu.setTitulo(p.toStringDetalhado());
            pausa();
        });
        menu.setHandler(4, () -> {
            try {
                p.trancar();
                model.atualizarDispositivo(idCasa, nomeDivisao, p);
                System.out.println("\n" + p.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            menu.setTitulo(p.toStringDetalhado());
            pausa();
        });
        menu.setHandler(5, () -> {
            try {
                p.destrancar();
                model.atualizarDispositivo(idCasa, nomeDivisao, p);
                System.out.println("\n" + p.toStringDetalhado());
            } catch (IllegalArgumentException | IllegalStateException | CasaInexistenteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            menu.setTitulo(p.toStringDetalhado());
            pausa();
        });

        menu.run();
    }

    private void gravarEstado() {
        try {
            Estado.guardarEmObjeto(model, "domuscontrol.dat");
            System.out.println("Estado gravado com sucesso em 'domuscontrol.dat'.");
        } catch (java.io.IOException e) {
            System.out.println("Erro ao gravar: " + e.getMessage());
        }
        pausa();
    }

    private void carregarEstado() {
        try {
            this.model = Estado.lerFicheiroObjecto("domuscontrol.dat");
            System.out.println("Estado carregado com sucesso de 'domuscontrol.dat'.");
        } catch (java.io.IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
        }
        pausa();
    }

    private void convidarUtilizador(String idCasa) {
        System.out.println("Utilizador que queres convidar(Username): ");
        String username = scanner.nextLine();
        try {
            model.convidarUtilizadorParaCasa(idCasa, username);
            System.out.println("Utilizador '" + username + "' adicionado como usufrutuário.");
        } catch (UtilizadorInexistenteException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        pausa();
    }

    private void menuEstatisticas() {
        Menu menu = new Menu("Estatísticas: " + utilizadorAtivo.getNome(), new String[] {
                "Casa que mais consome",
                "Top 3 dispositivos mais utilizados",
                "Top 3 divisões com mais dispositivos",
                "Consumo total de uma casa (kWh)",
                "Dispositivo que mais consumiu",
                "Comparar consumo entre divisões"
        });
        menu.setHandler(1, () -> casaConsomeMais());
        menu.setHandler(2, () -> topDispositivosCasa());
        menu.setHandler(3, () -> topDivisoesDispositivos());
        menu.setHandler(4, () -> consumoTotalCasa());
        menu.setHandler(5, () -> dispositivoMaisConsumiu());
        menu.setHandler(6, () -> comparacaoConsumoDivisoes());
        menu.run();
    }

    private void casaConsomeMais() {
        System.out.println("\n--- Casa que mais consome ---");
        Casa c = model.casaQueMaisConsome(utilizadorAtivo);
        if (c == null) {
            System.out.println("Sem casas registadas.");
        } else {
            System.out.println("Casa: " + c.getNomeCasa() +
                    " | Consumo total: " + String.format("%.1f", c.consumoTotal()) + " Wh");
        }
        pausa();
    }

    private void topDispositivosCasa() {
        String idCasa = escolherCasa();
        if (idCasa == null)
            return;
        System.out.println("\n--- Top 3 por tempo ligado ---");
        mostrarTopDispositivos(model.topDispositivosPorCriterio(idCasa, "tempo", 3), "tempo");
        System.out.println("--- Top 3 por ativações ---");
        mostrarTopDispositivos(model.topDispositivosPorCriterio(idCasa, "ativações", 3), "ativações");
        pausa();
    }

    private void mostrarTopDispositivos(List<DispositivoInfo> lista, String criterio) {
        if (lista.isEmpty()) {
            System.out.println("Não há dispositivos disponiveis.");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            DispositivoInfo d = lista.get(i);
            String valor;
            switch (criterio) {
                case "tempo":
                    valor = String.format("%.1f", d.getTempoTotalLigado()) + " min";
                    break;
                case "ativações":
                    valor = d.getNumAtivacoes() + " ativações";
                    break;
                case "consumo":
                    valor = String.format("%.1f", d.getConsumoTotal()) + " Wh";
                    break;
                default:
                    valor = "";
            }
            System.out.println((i + 1) + ". " + d.getIdentificador() + " | " + valor);
        }
    }

    private void topDivisoesDispositivos() {
        System.out.println("\n--- Top 3 divisões com mais dispositivos ---");
        List<ResultadoDivisao> lista = model.topDivisoes(utilizadorAtivo, 3);
        if (lista.isEmpty()) {
            System.out.println(" Não há divisões disponiveis");
        } else {
            for (int i = 0; i < lista.size(); i++) {
                System.out.println((i + 1) + ". " + lista.get(i).toString());
            }
        }
        pausa();
    }

    private void consumoTotalCasa() {
        String idCasa = escolherCasa();
        if (idCasa == null)
            return;
        double consumo = model.consumoTotalCasa(idCasa);
        if (consumo < 0) {
            System.out.println("Casa não encontrada.");
        } else {
            System.out.println("Casa: " + idCasa +
                    " | Consumo total: " + String.format("%.2f", consumo / 1000.0) + " kWh");
        }
        pausa();
    }

    private void dispositivoMaisConsumiu() {
        System.out.println("\n--- Dispositivo que mais consumiu ---");
        DispositivoInfo d = model.dispositivoMaisConsumiu(utilizadorAtivo);
        if (d == null) {
            System.out.println("Sem dispositivos.");
        } else {
            System.out.println(d.getIdentificador() +
                    " | Consumo: " + String.format("%.1f", d.getConsumoTotal()) + " Wh");
        }
        pausa();
    }

    private void comparacaoConsumoDivisoes() {
        String idCasa = escolherCasa();
        if (idCasa == null)
            return;
        System.out.println("\n--- Consumo por divisão de '" + idCasa + "' ---");
        List<Divisao> divisoes = model.divisoesOrdenadasPorConsumo(idCasa);
        if (divisoes.isEmpty()) {
            System.out.println("Sem divisões");
        } else {
            for (Divisao div : divisoes) {
                System.out.println("  " + div.getNome() +
                        " | " + String.format("%.1f", div.consumoDivisao()) + " Wh" +
                        " | " + div.getNumDispositivos() + " dispositivos");
            }
        }
        pausa();
    }

    private String escolherCasa() {
        List<String> admin = utilizadorAtivo.getCasasAdmin();
        List<String> usufruto = utilizadorAtivo.getCasasUsufruto();
        if (admin.isEmpty() && usufruto.isEmpty()) {
            System.out.println("Não existem casas");
            pausa();
            return null;
        }
        System.out.println("Casas admin: ");
        mostrarLista(admin);
        System.out.println("Casas usufruto: ");
        mostrarLista(usufruto);
        System.out.println("\nNome da casa: ");
        String nome = scanner.nextLine();
        return nome.isEmpty() ? null : nome;
    }

    private void criarAutomacaoParaDispositivo(String idCasa, String idDispAcao) {
        System.out.println("\n=== Criar Automação ===");
        System.out.println("Dispositivo de AÇÃO: " + idDispAcao);

        System.out.println("\nTipo de condição:");
        System.out.println("  1 - Outro dispositivo está LIGADO");
        System.out.println("  2 - Outro dispositivo está DESLIGADO");
        System.out.println("  3 - Luminosidade baixa (abaixo de X lux)");
        System.out.println("  4 - Chuva (Precipitação acima de X mm/h)");
        int tipoCond = lerInteiro("Tipo de condição: ");

        String idCondDisp = "";
        double limiar = 0.0;

        switch (tipoCond) {
            case 1: {
                mostrarTodosDispositivosDaCasa(idCasa);
                System.out.println("ID do dispositivo CONDIÇÃO: ");
                idCondDisp = scanner.nextLine();
                if (idCondDisp.isEmpty())
                    return;
                break;
            }
            case 2: {
                mostrarTodosDispositivosDaCasa(idCasa);
                System.out.println("ID do dispositivo CONDIÇÃO: ");
                idCondDisp = scanner.nextLine();
                if (idCondDisp.isEmpty())
                    return;
                break;
            }
            case 3:
                limiar = lerDouble("Disparar quando luminosidade abaixo de (lux): ");
                break;
            case 4:
                limiar = lerDouble("Disparar quando precipitação acima de (mm/h): ");
                break;
            default:
                System.out.println("Tipo inválido.");
                pausa();
                return;
        }

        System.out.println("Ação: 1 = Ligar " + idDispAcao + "  |  2 = Desligar " + idDispAcao);
        int tipoAcao = lerInteiro("Tipo de ação: ");
        if (tipoAcao != 1 && tipoAcao != 2) {
            System.out.println("Tipo inválido.");
            pausa();
            return;
        }

        System.out.println("Nome da automação: ");
        String nome = scanner.nextLine();
        model.criarAutomacao(idCasa, nome, tipoCond, idCondDisp, limiar, tipoAcao, idDispAcao);
        System.out.println("Automação '" + nome + "' criada.");
        pausa();
    }

    private void criarEscalonamentoParaDispositivo(String idCasa, String idDisp) {
        System.out.println("\n=== Criar Escalonamento ===");
        System.out.println("Dispositivo: " + idDisp);
        try {
            int hora = lerInteiro("Hora (0-23): ");
            int minuto = lerInteiro("Minuto (0-59): ");

            System.out.println("Ação: 1 = Ligar " + idDisp + "  |  2 = Desligar " + idDisp);
            int tipoAcao = lerInteiro("Tipo de ação: ");
            if (tipoAcao != 1 && tipoAcao != 2) {
                System.out.println("Tipo inválido.");
                pausa();
                return;
            }

            model.criarEscalonamento(idCasa, idDisp, hora, minuto, tipoAcao);
            System.out.println("Escalonamento criado: " + idDisp +
                    " às " + String.format("%02d:%02d", hora, minuto) + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        pausa();
    }

    private void mostrarTodosDispositivosDaCasa(String idCasa) {
        List<String> divisoes = model.listarNomesDivisoes(idCasa);
        if (divisoes.isEmpty()) {
            System.out.println(" Não existem divisões");
            return;
        }
        for (String nomeDivisao : divisoes) {
            List<String> disps = model.listarDispositivos(idCasa, nomeDivisao);
            if (!disps.isEmpty()) {
                System.out.println("  [" + nomeDivisao + "]");
                for (String d : disps) {
                    System.out.println("    " + d);
                }
            }
        }
    }

    private void listarAutomacoesEscalonamentos(String idCasa) {
        Menu menu = new Menu("Automações e Escalonamentos - Casa: " + idCasa, new String[] {
                "Listar automações",
                "Criar automação",
                "Remover automação",
                "Listar escalonamentos",
                "Criar escalonamento",
                "Remover escalonamento"
        });
        menu.setHandler(1, () -> listarAutomacoes(idCasa));
        menu.setHandler(2, () -> criarAutomacaoParaCasa(idCasa));
        menu.setHandler(3, () -> removerAutomacao(idCasa));
        menu.setHandler(4, () -> listarEscalonamentos(idCasa));
        menu.setHandler(5, () -> criarEscalonamentoParaCasa(idCasa));
        menu.setHandler(6, () -> removerEscalonamento(idCasa));
        menu.run();
    }

    private void listarAutomacoes(String idCasa) {
        System.out.println("\n=== Automações da casa '" + idCasa + "' ===");
        List<Automacao> autos = model.listarAutomacoes(idCasa);
        if (autos.isEmpty()) {
            System.out.println("Nenhuma automação");
        } else {
            for (int i = 0; i < autos.size(); i++)
                System.out.println("  " + (i + 1) + ". " + autos.get(i));
        }
        pausa();
    }

    private void listarEscalonamentos(String idCasa) {
        System.out.println("\n=== Escalonamentos da casa '" + idCasa + "' ===");
        List<Escalonamento> escalo = model.listarEscalonamentos(idCasa);
        if (escalo.isEmpty()) {
            System.out.println("Nenhum escalonamento existente.");
        } else {
            for (int i = 0; i < escalo.size(); i++)
                System.out.println("  " + (i + 1) + ". " + escalo.get(i));
        }
        pausa();
    }

    private void removerAutomacao(String idCasa) {
        List<Automacao> autos = model.listarAutomacoes(idCasa);
        if (autos.isEmpty()) {
            System.out.println("Nenhuma automação.");
            pausa();
            return;
        }
        for (int i = 0; i < autos.size(); i++)
            System.out.println((i + 1) + ". " + autos.get(i));
        int idx = lerInteiro("Número da automação a remover (0 para cancelar): ");
        if (idx > 0 && idx <= autos.size()) {
            model.removerAutomacao(idCasa, autos.get(idx - 1).getNome());
            System.out.println("Automação removida.");
        } else if (idx != 0) {
            System.out.println("Opção inválida.");
        }
        pausa();
    }

    private void removerEscalonamento(String idCasa) {
        List<Escalonamento> escalo = model.listarEscalonamentos(idCasa);
        if (escalo.isEmpty()) {
            System.out.println("Nenhum escalonamento para remover.");
            pausa();
            return;
        }
        for (int i = 0; i < escalo.size(); i++)
            System.out.println((i + 1) + ". " + escalo.get(i));
        int idx = lerInteiro("Número do escalonamento a remover (0 para cancelar): ");
        if (idx > 0 && idx <= escalo.size()) {
            model.removerEscalonamento(idCasa, idx - 1);
            System.out.println("Escalonamento removido.");
        } else if (idx != 0) {
            System.out.println("Opção inválida.");
        }
        pausa();
    }

    private void criarAutomacaoParaCasa(String idCasa) {
        System.out.println("\n=== Criar Automação ===");

        System.out.println("\nDispositivos disponíveis na casa:");
        mostrarTodosDispositivosDaCasa(idCasa);
        System.out.println("ID do dispositivo para a automação (ou vazio para cancelar): ");
        String idDisp = scanner.nextLine();
        if (idDisp.isEmpty())
            return;

        criarAutomacaoParaDispositivo(idCasa, idDisp);
    }

    private void criarEscalonamentoParaCasa(String idCasa) {
        System.out.println("\n=== Criar Escalonamento ===");

        System.out.println("\nDispositivos disponíveis na casa:");
        mostrarTodosDispositivosDaCasa(idCasa);
        System.out.println("ID do dispositivo (ou vazio para cancelar): ");
        String idDisp = scanner.nextLine();
        if (idDisp.isEmpty())
            return;

        criarEscalonamentoParaDispositivo(idCasa, idDisp);
    }

    private void menuCenarios(String idCasa) {
        Menu menu = new Menu("Cenários - Casa: " + idCasa, new String[] {
                "Listar cenários",
                "Executar cenário",
                "Remover cenário",
                "Criar Cenário"
        });
        menu.setHandler(1, () -> listarCenarios(idCasa));
        menu.setHandler(2, () -> executarCenario(idCasa));
        menu.setHandler(3, () -> removerCenario(idCasa));
        menu.setHandler(4, () -> criarCenario(idCasa));
        menu.run();
    }

    private void listarCenarios(String idCasa) {
        System.out.println("\n=== Cenários da casa '" + idCasa + "' ===");
        List<Cenario> lista = model.listarCenarios(idCasa);
        if (lista.isEmpty()) {
            System.out.println("Não existe cenários");
        } else {
            for (int i = 0; i < lista.size(); i++) {
                System.out.println((i + 1) + ". " + lista.get(i));
            }
        }
        pausa();
    }

    private void executarCenario(String idCasa) {
        List<Cenario> lista = model.listarCenarios(idCasa);
        if (lista.isEmpty()) {
            System.out.println("Nenhum cenário criado. Use 'Criar Cenário' para adicionar um.");
            pausa();
            return;
        }
        System.out.println("\nCenários disponíveis:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + lista.get(i).getNome());
        }
        int escolha = lerInteiro("Número do cenário a executar (0 para cancelar): ");
        if (escolha > 0 && escolha <= lista.size()) {
            String nome = lista.get(escolha - 1).getNome();
            boolean ok = model.executarCenario(idCasa, nome);
            System.out.println(ok ? " Cenário '" + nome + "' executado com sucesso!" : "Erro ao executar o cenário.");
        } else if (escolha != 0) {
            System.out.println("Opção inválida.");
        }
        pausa();
    }

    private void removerCenario(String idCasa) {
        List<Cenario> lista = model.listarCenarios(idCasa);
        if (lista.isEmpty()) {
            System.out.println("Nenhum cenário para remover.");
            pausa();
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + lista.get(i).getNome());
        }
        int escolha = lerInteiro("Número do cenário a remover (0 para cancelar): ");
        if (escolha > 0 && escolha <= lista.size()) {
            String nome = lista.get(escolha - 1).getNome();
            model.removerCenario(idCasa, nome);
            System.out.println("Cenário '" + nome + "' removido.");
        } else if (escolha != 0) {
            System.out.println("Opção inválida.");
        }
        pausa();
    }

    private void criarCenario(String idCasa) {
        System.out.println("\n=== Criar Cenário ===");
        System.out.println("Nome do cenário: ");
        String nome = scanner.nextLine();
        System.out.println("Descrição: ");
        String descricao = scanner.nextLine();
        List<Integer> tiposAcao = new ArrayList<>();
        List<String> idsDisp = new ArrayList<>();

        boolean continuar = true;
        while (continuar) {
            System.out.println("\nAdicionar ação:");
            System.out.println("  1. Ligar dispositivo específico");
            System.out.println("  2. Desligar dispositivo específico");
            System.out.println("  3. Ligar todas as lâmpadas");
            System.out.println("  4. Desligar todas as lâmpadas");
            System.out.println("  5. Ligar todas as colunas de som");
            System.out.println("  6. Desligar todas as colunas de som");
            System.out.println("  7. Abrir todas as cortinas/portões");
            System.out.println("  8. Fechar todas as cortinas/portões");
            System.out.println("  0. Terminar e guardar cenário");
            int op = lerInteiro("Opção: ");
            switch (op) {
                case 1: {
                    System.out.println("\nDispositivos disponíveis na casa:");
                    mostrarTodosDispositivosDaCasa(idCasa);
                    System.out.print("ID do dispositivo: ");
                    String id = scanner.nextLine();
                    tiposAcao.add(1); idsDisp.add(id);
                    break;
                }
                case 2: {
                    System.out.print("ID do dispositivo: ");
                    String id = scanner.nextLine();
                    tiposAcao.add(2); idsDisp.add(id);
                    break;
                }
                case 3: tiposAcao.add(3); idsDisp.add(""); break;
                case 4: tiposAcao.add(4); idsDisp.add(""); break;
                case 5: tiposAcao.add(5); idsDisp.add(""); break;
                case 6: tiposAcao.add(6); idsDisp.add(""); break;
                case 7: tiposAcao.add(7); idsDisp.add(""); break;
                case 8: tiposAcao.add(8); idsDisp.add(""); break;
                case 0:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        if (tiposAcao.isEmpty()) {
            System.out.println("Cenário sem ações — não guardado.");
        } else {
            model.criarCenarioComAcoes(idCasa, nome, descricao, tiposAcao, idsDisp);
            System.out.println("Cenário '" + nome + "' criado com " + tiposAcao.size() + " ações.");
        }
        pausa();
    }
}
