public class UtilizadorInexistenteException extends Exception {
    public UtilizadorInexistenteException(String username) {
        super("Utilizador '" + username + "' não existe.");
    }
}
