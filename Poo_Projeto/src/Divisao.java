import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Divisao implements Serializable {
    private String nome;
    private Map<String, Dispositivo> dispositivos;

    public Divisao() {
        this.nome = "";
        this.dispositivos = new HashMap<>();
    }

    public Divisao(String nome, Map<String, Dispositivo> dispositivos) {
        this.nome = nome;
        this.dispositivos = new HashMap<>();
        for (Dispositivo d : dispositivos.values()) {
            this.dispositivos.put(d.getIdentificador(), d.clone());
        }
    }

    public Divisao(Divisao d) {
        this.nome = d.getNome();
        this.dispositivos = d.getDispositivos();
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public Map<String, Dispositivo> getDispositivos() {
        Map<String, Dispositivo> copia = new HashMap<>();
        for (Dispositivo d : this.dispositivos.values()) {
            copia.put(d.getIdentificador(), d.clone());
        }
        return copia;
    }

    public void setDispositivos(Map<String, Dispositivo> novosDispositivos) {
        this.dispositivos = new HashMap<>();

        for (Dispositivo d : novosDispositivos.values()) {
            this.dispositivos.put(d.getIdentificador(), d.clone());
        }
    }

    public void addDispositivo(Dispositivo d) {
        this.dispositivos.put(d.getIdentificador(), d.clone());
    }

    public void removeDispositivo(String idDispositivo) {
        this.dispositivos.remove(idDispositivo);
    }

    public boolean existeDispositivo(String idDispositivo) {
        return this.dispositivos.containsKey(idDispositivo);
    }

    public Dispositivo getDispositivo(String idDispositivo) {
        Dispositivo d = this.dispositivos.get(idDispositivo);
        if (d != null) {
            return d.clone();
        }
        return null;
    }

    public void atualizarDispositivo(Dispositivo d) {
        this.dispositivos.put(d.getIdentificador(), d.clone());
    }

    public void atualizarTempoDispositivos(double tempoInicial, double tempoFinal) {
        double inte = tempoFinal - tempoInicial;
        double novoTempoAtual = tempoFinal % (24 * 60);
        for (Dispositivo d : this.dispositivos.values()) {
            d.atualizarTempoLigado(inte, novoTempoAtual);
        }
    }

    public List<String> listarDispositivos() {
        List<String> lista = new ArrayList<>();
        for (Dispositivo d : this.dispositivos.values()) {
            lista.add(d.toString());
        }
        return lista;
    }

    public double consumoDivisao() {
        double total = 0;
        for (Dispositivo d : this.dispositivos.values()) {
            total += d.getConsumoTotal();
        }
        return total;
    }

    public int getNumDispositivos() {
        return this.dispositivos.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;

        Divisao outra = (Divisao) o;
        return this.nome.equals(outra.getNome()) &&
                this.dispositivos.equals(outra.getDispositivos());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Divisão: ")
                .append(this.nome)
                .append(" | Total de Dispositivos: ")
                .append(this.dispositivos.size());
        return sb.toString();
    }

    @Override
    public Divisao clone() {
        return new Divisao(this);
    }
}