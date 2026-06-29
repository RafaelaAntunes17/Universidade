import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Casa implements Serializable {
    private String nomeCasa;
    private String morada;
    private Map<String, Divisao> divisoes;
    private List<Automacao> automacoes;
    private List<Escalonamento> escalonamentos;
    private List<Cenario> cenarios;
    private double luminosidade;
    private double precipitacao;

    public Casa() {
        this.nomeCasa = "";
        this.morada = "";
        this.divisoes = new HashMap<>();
        this.automacoes = new ArrayList<>();
        this.escalonamentos = new ArrayList<>();
        this.cenarios = new ArrayList<>();
        this.luminosidade = 500.0;
        this.precipitacao = 0.0;
    }

    public Casa(String nomeCasa, String morada, Map<String, Divisao> divisoes) {
        this.nomeCasa = nomeCasa;
        this.morada = morada;
        this.divisoes = new HashMap<>();
        for (Divisao d : divisoes.values()) {
            this.divisoes.put(d.getNome(), d.clone());
        }
        this.automacoes = new ArrayList<>();
        this.escalonamentos = new ArrayList<>();
        this.cenarios = new ArrayList<>();
        this.luminosidade = 500.0;
        this.precipitacao = 0.0;
    }

    public Casa(Casa c) {
        this.nomeCasa = c.getNomeCasa();
        this.morada = c.getMorada();
        this.divisoes = new HashMap<>();
        for (Divisao d : c.divisoes.values()) {
            this.divisoes.put(d.getNome(), d.clone());
        }
        this.automacoes = new ArrayList<>();
        for (Automacao a : c.automacoes) {
            this.automacoes.add(a.clone());
        }
        this.escalonamentos = new ArrayList<>();
        for (Escalonamento e : c.escalonamentos) {
            this.escalonamentos.add(e.clone());
        }
        this.cenarios = new ArrayList<>();
        for (Cenario cen : c.cenarios) {
            this.cenarios.add(cen.clone());
        }
        this.luminosidade = c.luminosidade;
        this.precipitacao = c.precipitacao;
    }

    public String getNomeCasa() {
        return this.nomeCasa;
    }

    public void setNomeCasa(String novoNome) {
        this.nomeCasa = novoNome;
    }

    public String getMorada() {
        return this.morada;
    }

    public void setMorada(String novaMorada) {
        this.morada = novaMorada;
    }

    public Map<String, Divisao> getDivisoes() {
        Map<String, Divisao> copia = new HashMap<>();
        for (Divisao a : this.divisoes.values()) {
            copia.put(a.getNome(), a.clone());
        }
        return copia;
    }

    public void setDivisoes(Map<String, Divisao> novaDivisoes) {
        this.divisoes = new HashMap<>();
        for (Divisao d : novaDivisoes.values()) {
            this.divisoes.put(d.getNome(), d.clone());
        }
    }

    public double getLuminosidade() {
        return this.luminosidade;
    }

    public void setLuminosidade(double luminosidade) {
        this.luminosidade = luminosidade;
    }

    public double getPrecipitacao() {
        return this.precipitacao;
    }

    public void setPrecipitacao(double precipitacao) {
        this.precipitacao = precipitacao;
    }

    public String getAmbienteFormatado() {
        return String.format("Luminosidade: %.0f lux | Precipitação: %.1f mm/h%s",
                this.luminosidade, this.precipitacao,
                this.precipitacao > 0 ? " (chuva)" : "");
    }

    public void addDivisao(Divisao d) {
        this.divisoes.put(d.getNome(), d.clone());
    }

    public void removeDivisao(String nomeDivisao) {
        this.divisoes.remove(nomeDivisao);
    }

    public boolean existeDivisao(String nomeDivisao) {
        return this.divisoes.containsKey(nomeDivisao);
    }

    public void adicionarDispositivoADivisao(String nomeDivisao, Dispositivo d) {
        Divisao div = this.divisoes.get(nomeDivisao);
        if (div != null) {
            div.addDispositivo(d);
        }
    }

    public void removerDispositivoDeDivisao(String nomeDivisao, String idDisp) {
        Divisao div = this.divisoes.get(nomeDivisao);
        if (div != null) {
            div.removeDispositivo(idDisp);
        }
    }

    public Dispositivo getDispositivo(String nomeDivisao, String idDisp) {
        Divisao div = this.divisoes.get(nomeDivisao);
        if (div != null) {
            return div.getDispositivo(idDisp);
        }
        return null;
    }

    public void atualizarDispositivo(String nomeDivisao, Dispositivo d) {
        Divisao div = this.divisoes.get(nomeDivisao);
        if (div != null) {
            div.atualizarDispositivo(d);
        }
    }

    public List<String> listarDivisoes() {
        List<String> lista = new ArrayList<>();
        for (Divisao d : this.divisoes.values()) {
            lista.add(d.toString());
        }
        return lista;
    }

    public List<String> listarNomesDivisoes() {
        List<String> lista = new ArrayList<>();
        for (Divisao d : this.divisoes.values()) {
            lista.add(d.getNome());
        }
        return lista;
    }

    public List<String> listarDispositivos(String nomeDivisao) {
        Divisao div = this.divisoes.get(nomeDivisao);
        if (div != null) {
            return div.listarDispositivos();
        }
        return new ArrayList<>();
    }

    public double consumoTotal() {
        double soma = 0;
        for (Divisao d : this.divisoes.values()) {
            soma += d.consumoDivisao();
        }
        return soma;
    }

    public int totalDispositivos() {
        int total = 0;
        for (Divisao d : this.divisoes.values()) {
            total += d.getNumDispositivos();
        }
        return total;
    }

    public List<Dispositivo> getTodosDispositivos() {
        List<Dispositivo> todos = new ArrayList<>();

        for (Divisao div : this.divisoes.values()) {
            todos.addAll(div.getDispositivos().values());
        }

        return todos;
    }

    public void atualizarTempoDispositivos(double tempoInicial, double tempoFinal) {
        for (Divisao div : this.divisoes.values()) {
            div.atualizarTempoDispositivos(tempoInicial, tempoFinal);
        }
    }

    public void addAutomacao(Automacao a) {
        this.automacoes.add(a.clone());
    }

    public void removerAutomacao(String nome) {
        this.automacoes.removeIf(a -> a.getNome().equals(nome));
    }

    public List<Automacao> getAutomacoes() {
        List<Automacao> copia = new ArrayList<>();
        for (Automacao a : this.automacoes) {
            copia.add(a.clone());
        }
        return copia;
    }

    public void addEscalonamento(Escalonamento e) {
        this.escalonamentos.add(e.clone());
    }

    public List<Escalonamento> getEscalonamentos() {
        List<Escalonamento> copia = new ArrayList<>();
        for (Escalonamento e : this.escalonamentos) {
            copia.add(e.clone());
        }
        return copia;
    }

    public void removerEscalonamento(int index) {
        if (index >= 0 && index < this.escalonamentos.size()) {
            this.escalonamentos.remove(index);
        }
    }

    public void executarAutomacoes(double tempoAtual) {
        for (Automacao a : this.automacoes) {
            a.execute(this, tempoAtual);
        }
    }

    public void executarEscalonamentosNoIntervalo(int tempoInicial, int tempoFinal, double tempoAtual) {
        for (Escalonamento e : this.escalonamentos) {
            e.executarSeNoIntervalo(tempoInicial, tempoFinal, this, tempoAtual);
        }
    }

    public void addCenario(Cenario c) {
        this.cenarios.add(c.clone());
    }

    public void removerCenario(String nome) {
        this.cenarios.removeIf(c -> c.getNome().equals(nome));
    }

    public List<Cenario> getCenarios() {
        List<Cenario> copia = new ArrayList<>();
        for (Cenario c : this.cenarios) {
            copia.add(c.clone());
        }
        return copia;
    }

    public boolean executarCenario(String nomeCenario, double tempoAtual) {
        for (Cenario c : this.cenarios) {
            if (c.getNome().equals(nomeCenario)) {
                c.executar(this, tempoAtual);
                return true;
            }
        }
        return false;
    }

    public Dispositivo dispositivoMaisConsumiu() {
        Dispositivo melhor = null;
        for (Dispositivo d : getTodosDispositivos()) {
            if (melhor == null || d.getConsumoTotal() > melhor.getConsumoTotal()) {
                melhor = d;
            }
        }
        return melhor;
    }

    public List<Divisao> getDivisoesOrdenadasPorConsumo() {
        return this.divisoes.values().stream()
                .sorted(Comparator.comparingDouble(Divisao::consumoDivisao).reversed())
                .map(Divisao::clone)
                .collect(Collectors.toList());
    }

    public List<ResultadoDivisao> getInfoDivisoes() {
        List<ResultadoDivisao> lista = new ArrayList<>();
        for (Divisao div : this.divisoes.values()) {
            lista.add(new ResultadoDivisao(this.nomeCasa, div.getNome(), div.getNumDispositivos()));
        }
        return lista;
    }

    public String divisaoDoDispositivo(String idDispositivo) {
        for (Divisao div : this.divisoes.values()) {
            if (div.existeDispositivo(idDispositivo)) {
                return div.getNome();
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || this.getClass() != o.getClass())
            return false;

        Casa outra = (Casa) o;

        return this.luminosidade == outra.getLuminosidade() &&
                this.precipitacao == outra.getPrecipitacao() &&
                this.nomeCasa.equals(outra.nomeCasa) &&
                this.morada.equals(outra.morada) &&
                this.divisoes.equals(outra.divisoes) &&
                this.automacoes.equals(outra.automacoes) &&
                this.escalonamentos.equals(outra.escalonamentos) &&
                this.cenarios.equals(outra.cenarios);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Casa: ")
                .append(this.nomeCasa)
                .append(" | Morada: ")
                .append(this.morada)
                .append(" | Divisões: ")
                .append(this.divisoes.size());
        return sb.toString();
    }

    @Override
    public Casa clone() {
        return new Casa(this);
    }
}