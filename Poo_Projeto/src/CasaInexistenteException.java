public class CasaInexistenteException extends Exception {
    public CasaInexistenteException(String idCasa) {
        super("Casa '" + idCasa + "' não existe.");
    }
}
