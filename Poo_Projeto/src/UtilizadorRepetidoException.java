public class UtilizadorRepetidoException extends Exception {
    public UtilizadorRepetidoException(String username) {
        super("Utilizador '" + username + "' já existe.");
    }
}