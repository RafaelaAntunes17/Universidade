public class CasaRepetidaException extends Exception {
    public CasaRepetidaException(String nome) {
        super("Casa com nome '" + nome + "' já existe.");
    }
}