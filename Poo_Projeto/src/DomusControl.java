import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.*;

public class DomusControl implements Serializable {

    private Map<String, Utilizador> utilizadores;
    private Map<String, Casa> casas;
    private int horaAtual;
    private int minutoAtual;
    private int diaAtual;

    private static final Map<String, Comparator<Dispositivo>> comparadores = new HashMap<>();
    static {
        comparadores.put("tempo",
                Comparator.comparingDouble(Dispositivo::getTempoTotalLigado).reversed());
        comparadores.put("ativações",
                Comparator.comparingInt(Dispositivo::getNumAtivacoes).reversed());
        comparadores.put("consumo",
                Comparator.comparingDouble(Dispositivo::getConsumoTotal).reversed());
    }

    public DomusControl() {
        this.utilizadores = new HashMap<>();
        this.casas = new HashMap<>();
        this.horaAtual = 0;
        this.minutoAtual = 0;
        this.diaAtual = 0;
    }

    public DomusControl(Map<String, Utilizador> users, Map<String, Casa> casas) {
        this.utilizadores = new HashMap<>();
        for (Utilizador u : users.values()) {
            this.utilizadores.put(u.getUserName(), u.clone());
        }
        this.casas = new HashMap<>();
        for (Casa c : casas.values()) {
            this.casas.put(c.getNomeCasa(), c.clone());
        }
        this.horaAtual = 0;
        this.minutoAtual = 0;
        this.diaAtual = 0;
    }

    public DomusControl(DomusControl modelo) {
        this.utilizadores = modelo.getUtilizadores();
        this.casas = modelo.getCasas();
        this.horaAtual = modelo.getHoraAtual();
        this.minutoAtual = modelo.getMinutoAtual();
        this.diaAtual = modelo.getDiaAtual();
    }

    public Map<String, Utilizador> getUtilizadores() {
        Map<String, Utilizador> copia = new HashMap<>();
        for (Utilizador u : this.utilizadores.values()) {
            copia.put(u.getUserName(), u.clone());
        }
        return copia;
    }

    public Map<String, Casa> getCasas() {
        Map<String, Casa> copia = new HashMap<>();
        for (Casa c : this.casas.values()) {
            copia.put(c.getNomeCasa(), c.clone());
        }
        return copia;
    }

    public int getHoraAtual() {
        return this.horaAtual;
    }

    public int getMinutoAtual() {
        return this.minutoAtual;
    }

    public int getDiaAtual() {
        return this.diaAtual;
    }

    public String getDiaSemanaFormatado() {
        String[] dias = { "Segunda-feira", "Terça-feira", "Quarta-feira",
                "Quinta-feira", "Sexta-feira", "Sábado", "Domingo" };
        return dias[this.diaAtual % 7];
    }

    public String getHoraFormatada() {
        return String.format("%02d:%02d (%s)", this.horaAtual, this.minutoAtual, getDiaSemanaFormatado());
    }

    public double getTempoEmMinutos() {
        return this.horaAtual * 60 + this.minutoAtual;
    }

    public Utilizador getUtilizador(String username) throws UtilizadorInexistenteException {
        if (!this.existeUtilizador(username)) {
            throw new UtilizadorInexistenteException(username);
        }
        return this.utilizadores.get(username).clone();
    }

    public Casa getCasa(String idCasa) throws CasaInexistenteException {
        if (!this.existeCasa(idCasa)) {
            throw new CasaInexistenteException(idCasa);
        }
        return this.casas.get(idCasa).clone();
    }

    public Dispositivo getDispositivo(String idCasa, String nomeDivisao, String idDisp)
            throws CasaInexistenteException, DispositivoInexistenteException {
        Casa c = this.casas.get(idCasa);
        if (c == null)
            throw new CasaInexistenteException(idCasa);
        Dispositivo d = c.getDispositivo(nomeDivisao, idDisp);
        if (d == null)
            throw new DispositivoInexistenteException(idDisp);
        return d;
    }

    public List<Casa> getCasasDoUtilizador(Utilizador u) {
        List<Casa> casasEncontradas = new ArrayList<>();
        List<String> nomes = new ArrayList<>(u.getCasasAdmin());
        nomes.addAll(u.getCasasUsufruto());
        for (String nome : nomes) {
            Casa c = this.casas.get(nome);
            if (c != null) {
                casasEncontradas.add(c);
            }
        }
        return casasEncontradas;
    }

    public String getAmbiente(String idCasa) {
        Casa c = this.casas.get(idCasa);
        return c != null ? c.getAmbienteFormatado() : "Casa não encontrada.";
    }

    public void setHoraAtual(int horaAtual) {
        if (horaAtual < 0 || horaAtual > 23)
            throw new IllegalArgumentException("Hora deve estar entre 0 e 23.");
        this.horaAtual = horaAtual;
    }

    public void setMinutoAtual(int minutoAtual) {
        if (minutoAtual < 0 || minutoAtual > 59)
            throw new IllegalArgumentException("Minuto deve estar entre 0 e 59.");
        this.minutoAtual = minutoAtual;
    }

    public void setDiaAtual(int diaAtual) {
        if (diaAtual < 0)
            throw new IllegalArgumentException("Dia não pode ser negativo.");
        this.diaAtual = diaAtual;
    }

    public void adicionarUtilizador(String id, String password, String nome) throws UtilizadorRepetidoException {
        if (this.utilizadores.containsKey(id)) {
            throw new UtilizadorRepetidoException(id);
        }
        Utilizador u = new Utilizador(id, password, nome);
        this.utilizadores.put(id, u.clone());
    }

    public boolean existeUtilizador(String username) {
        return this.utilizadores.containsKey(username);
    }

    public void atualizarUtilizador(Utilizador u) {
        this.utilizadores.put(u.getUserName(), u.clone());
    }

    public Utilizador autenticar(String username, String password) throws UtilizadorInexistenteException {
        Utilizador u = this.utilizadores.get(username);
        if (u == null || !u.verificaPassword(password))
            throw new UtilizadorInexistenteException(username);
        return u.clone();
    }

    public void adicionarCasa(Casa c) throws CasaRepetidaException {
        if (this.existeCasa(c.getNomeCasa())) {
            throw new CasaRepetidaException(c.getNomeCasa());
        }
        this.casas.put(c.getNomeCasa(), c.clone());
    }

    public void criarCasa(String nomeCasa, String morada, String usernameAdmin)
            throws CasaRepetidaException, UtilizadorInexistenteException {
        adicionarCasa(new Casa(nomeCasa, morada, new HashMap<>()));
        Utilizador u = this.utilizadores.get(usernameAdmin);
        if (u == null)
            throw new UtilizadorInexistenteException(usernameAdmin);
        u.addCasaAdmin(nomeCasa);
    }

    public void convidarUtilizadorParaCasa(String idCasa, String username)
            throws UtilizadorInexistenteException {
        Utilizador u = this.utilizadores.get(username);
        if (u == null)
            throw new UtilizadorInexistenteException(username);
        if (u.getCasasAdmin().contains(idCasa) || u.getCasasUsufruto().contains(idCasa))
            throw new IllegalStateException("O utilizador '" + username + "' já tem acesso a esta casa.");
        u.addCasaUsufruto(idCasa);
    }

    public boolean existeCasa(String idCasa) {
        return this.casas.containsKey(idCasa);
    }

    public void adicionarDivisaoACasa(String idCasa, Divisao d) throws CasaInexistenteException {
        Casa c = this.casas.get(idCasa);
        if (c == null)
            throw new CasaInexistenteException(idCasa);
        c.addDivisao(d);
    }

    public void adicionarDivisaoACasa(String idCasa, String nomeDivisao) throws CasaInexistenteException {
        adicionarDivisaoACasa(idCasa, new Divisao(nomeDivisao, new HashMap<>()));
    }

    public void removerDivisaoDeCasa(String idCasa, String nomeDivisao) throws CasaInexistenteException {
        Casa c = this.casas.get(idCasa);
        if (c == null)
            throw new CasaInexistenteException(idCasa);
        c.removeDivisao(nomeDivisao);
    }

    public void adicionarDispositivoADivisao(String idCasa, String nomeDivisao, Dispositivo d)throws CasaInexistenteException {
        Casa c = this.casas.get(idCasa);
        if (c == null)
            throw new CasaInexistenteException(idCasa);
        c.adicionarDispositivoADivisao(nomeDivisao, d);
    }

    public String adicionarDispositivo(String idCasa, String nomeDivisao, String tipo, String marca, String modelo,double consumo) throws CasaInexistenteException {
        Dispositivo d = Dispositivo.criar(tipo, marca, modelo, consumo);
        adicionarDispositivoADivisao(idCasa, nomeDivisao, d);
        return d.getIdentificador();
    }

    public void removerDispositivoDeDivisao(String idCasa, String nomeDivisao, String idDisp)
            throws CasaInexistenteException {
        Casa c = this.casas.get(idCasa);
        if (c == null)
            throw new CasaInexistenteException(idCasa);
        c.removerDispositivoDeDivisao(nomeDivisao, idDisp);
    }

    public void atualizarDispositivo(String idCasa, String nomeDivisao, Dispositivo d) throws CasaInexistenteException {
        Casa c = this.casas.get(idCasa);
        if (c == null)
            throw new CasaInexistenteException(idCasa);
        c.atualizarDispositivo(nomeDivisao, d);
        executarTodasAutomacoes();
    }

    public List<String> listarDivisoes(String idCasa) throws CasaInexistenteException {
        Casa c = this.casas.get(idCasa);
        if (c == null)
            throw new CasaInexistenteException(idCasa);
        return c.listarDivisoes();
    }

    public List<String> listarNomesDivisoes(String idCasa) {
        Casa c = this.casas.get(idCasa);
        if (c != null) {
            return c.listarNomesDivisoes();
        }
        return new ArrayList<>();
    }

    public List<String> listarDispositivos(String idCasa, String nomeDivisao) {
        Casa c = this.casas.get(idCasa);
        if (c != null) {
            return c.listarDispositivos(nomeDivisao);
        }
        return new ArrayList<>();
    }

    private List<Dispositivo> todosDispositivosDaCasa(String idCasa) {
        Casa c = this.casas.get(idCasa);
        return c != null ? c.getTodosDispositivos() : new ArrayList<>();
    }

    public Casa casaQueMaisConsome(Utilizador u) {
        Casa maisconsome = null;
        for (Casa c : getCasasDoUtilizador(u)) {
            if (maisconsome == null || c.consumoTotal() > maisconsome.consumoTotal()) {
                maisconsome = c;
            }
        }
        return maisconsome != null ? maisconsome.clone() : null;
    }

    public List<DispositivoInfo> topDispositivosPorCriterio(String idCasa, String criterio, int limite) {
        List<Dispositivo> todos = todosDispositivosDaCasa(idCasa);
        if (!comparadores.containsKey(criterio))
            return new ArrayList<>();

        todos.sort(comparadores.get(criterio));

        List<DispositivoInfo> resultado = new ArrayList<>();
        for (int i = 0; i < Math.min(limite, todos.size()); i++) {
            resultado.add((DispositivoInfo) todos.get(i).clone());
        }
        return resultado;
    }

    public List<ResultadoDivisao> topDivisoes(Utilizador u, int limit) {
        List<ResultadoDivisao> todas = new ArrayList<>();
        for (Casa c : getCasasDoUtilizador(u)) {
            todas.addAll(c.getInfoDivisoes());
        }
        todas.sort((a, b) -> Integer.compare(b.getNumDispositivos(), a.getNumDispositivos()));

        List<ResultadoDivisao> resultado = new ArrayList<>();
        int limite = Math.min(limit, todas.size());
        for (int i = 0; i < limite; i++) {
            resultado.add(todas.get(i));
        }
        return resultado;
    }

    public double consumoTotalCasa(String idCasa) {
        Casa c = this.casas.get(idCasa);
        return c != null ? c.consumoTotal() : -1;
    }

    public DispositivoInfo dispositivoMaisConsumiu(Utilizador u) {
        Dispositivo melhor = null;
        for (Casa c : getCasasDoUtilizador(u)) {
            Dispositivo d = c.dispositivoMaisConsumiu();
            if (d != null && (melhor == null || d.getConsumoTotal() > melhor.getConsumoTotal())) {
                melhor = d;
            }
        }
        return melhor != null ? (DispositivoInfo) melhor.clone() : null;
    }

    public List<Divisao> divisoesOrdenadasPorConsumo(String idCasa) {
        Casa c = this.casas.get(idCasa);
        return c != null ? c.getDivisoesOrdenadasPorConsumo() : new ArrayList<>();
    }

    public void avancarTempo(int minutos) {
        if (minutos < 0)
            throw new IllegalArgumentException("Não pode avançar tempo negativo.");

        int tempoInicial = this.horaAtual * 60 + this.minutoAtual;
        int tempoFinal = tempoInicial + minutos;

        for (Casa c : this.casas.values()) {
            c.atualizarTempoDispositivos(tempoInicial, tempoFinal);
        }

        if (tempoFinal < 24 * 60) {
            for (Casa c : this.casas.values()) {
                c.executarEscalonamentosNoIntervalo(tempoInicial, tempoFinal, tempoFinal);
            }
        } else {

            for (Casa c : this.casas.values()) {
                c.executarEscalonamentosNoIntervalo(tempoInicial, 24 * 60, 24 * 60);
            }

            int novoTempo = tempoFinal % (24 * 60);
            for (Casa c : this.casas.values()) {
                c.executarEscalonamentosNoIntervalo(0, novoTempo, novoTempo);
            }
        }

        int diasPassados = tempoFinal / (24 * 60);
        this.diaAtual = (this.diaAtual + diasPassados) % 7;
        this.horaAtual = (tempoFinal / 60) % 24;
        this.minutoAtual = tempoFinal % 60;
        executarTodasAutomacoes();
    }

    public void avancarParaHora(int hora, int minuto) {
        if (hora < 0 || hora > 23 || minuto < 0 || minuto > 59)
            throw new IllegalArgumentException("Hora inválida (0-23h, 0-59min).");
        int alvo = hora * 60 + minuto;
        int atual = this.horaAtual * 60 + this.minutoAtual;
        int diff = alvo - atual;
        if (diff <= 0)
            diff += 24 * 60;
        avancarTempo(diff);
    }

    public void setLuminosidade(String idCasa, double lux) {
        Casa c = this.casas.get(idCasa);
        if (c != null) {
            c.setLuminosidade(lux);
            executarTodasAutomacoes();
        }
    }

    public void setPrecipitacao(String idCasa, double mmh) {
        Casa c = this.casas.get(idCasa);
        if (c != null) {
            c.setPrecipitacao(mmh);
            executarTodasAutomacoes();
        }
    }

    public void adicionarAutomacao(String idCasa, Automacao a) {
        Casa c = this.casas.get(idCasa);
        if (c != null) {
            c.addAutomacao(a);
        }
    }

    public void criarAutomacao(String idCasa, String nome, int tipoCond,
            String idCondDisp, double limiar, int tipoAcao, String idDispAcao) {
        adicionarAutomacao(idCasa, Automacao.criar(nome, tipoCond, idCondDisp, limiar, tipoAcao, idDispAcao));
    }

    public void adicionarEscalonamento(String idCasa, Escalonamento e) {
        Casa c = this.casas.get(idCasa);
        if (c != null) {
            c.addEscalonamento(e);
        }
    }

    public void criarEscalonamento(String idCasa, String idDisp, int hora, int minuto, int tipoAcao) {
        adicionarEscalonamento(idCasa, new Escalonamento(hora, minuto, Action.criar(tipoAcao, idDisp)));
    }

    public List<Automacao> listarAutomacoes(String idCasa) {
        Casa c = this.casas.get(idCasa);
        if (c == null)
            return new ArrayList<>();
        return c.getAutomacoes();
    }

    public List<Escalonamento> listarEscalonamentos(String idCasa) {
        Casa c = this.casas.get(idCasa);
        if (c == null)
            return new ArrayList<>();
        return c.getEscalonamentos();
    }

    public void removerAutomacao(String idCasa, String nome) {
        Casa c = this.casas.get(idCasa);
        if (c != null)
            c.removerAutomacao(nome);
    }

    public void removerEscalonamento(String idCasa, int index) {
        Casa c = this.casas.get(idCasa);
        if (c != null)
            c.removerEscalonamento(index);
    }

    public void executarTodasAutomacoes() {
        double tempo = getTempoEmMinutos();
        for (Casa c : this.casas.values()) {
            c.executarAutomacoes(tempo);
        }
    }

    public void adicionarCenario(String idCasa, Cenario c) {
        Casa casa = this.casas.get(idCasa);
        if (casa != null) {
            casa.addCenario(c);
        }
    }

    public void criarCenarioComAcoes(String idCasa, String nome, String descricao,
            List<Integer> tiposAcao, List<String> idsDisp) {
        Cenario c = new Cenario(nome, descricao);
        for (int i = 0; i < tiposAcao.size(); i++) {
            c.addAcao(Action.criar(tiposAcao.get(i), idsDisp.get(i)));
        }
        adicionarCenario(idCasa, c);
    }

    public void removerCenario(String idCasa, String nomeCenario) {
        Casa casa = this.casas.get(idCasa);
        if (casa != null) {
            casa.removerCenario(nomeCenario);
        }
    }

    public List<Cenario> listarCenarios(String idCasa) {
        Casa casa = this.casas.get(idCasa);
        if (casa == null)
            return new ArrayList<>();
        return casa.getCenarios();
    }

    public boolean executarCenario(String idCasa, String nomeCenario) {
        Casa casa = this.casas.get(idCasa);
        if (casa == null)
            return false;
        return casa.executarCenario(nomeCenario, getTempoEmMinutos());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        DomusControl outro = (DomusControl) o;
        return this.horaAtual == outro.horaAtual &&
                this.minutoAtual == outro.minutoAtual &&
                this.diaAtual == outro.diaAtual &&
                this.utilizadores.equals(outro.getUtilizadores()) &&
                this.casas.equals(outro.getCasas());
    }

    @Override
    public DomusControl clone() {
        return new DomusControl(this);
    }

}