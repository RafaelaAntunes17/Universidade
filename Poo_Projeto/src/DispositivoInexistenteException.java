public class DispositivoInexistenteException extends Exception {
    public DispositivoInexistenteException(String idDispositivo) {
        super("Dispositivo '" + idDispositivo + "' não existe.");
    }
}
