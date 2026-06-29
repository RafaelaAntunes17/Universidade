
public class CondicaoOn implements Condicao {
    private String idDispositivo;

    public CondicaoOn(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getIdDispositivo() {
        return this.idDispositivo;
    }

    public boolean verify(Casa casa) {
        String nomeDivisao = casa.divisaoDoDispositivo(idDispositivo);
        if (nomeDivisao == null)
            return false;
        Dispositivo d = casa.getDispositivo(nomeDivisao, idDispositivo);
        return d != null && d.isLigado();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Dispositivo ").append(this.idDispositivo).append(" ligado");
        return sb.toString();
    }
}