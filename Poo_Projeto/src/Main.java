public class Main {
    private static final String FICHEIRO_DEFAULT = "domuscontrol.dat";

    public static void main(String[] args) {
        DomusControl modelo;
        try {
            modelo = Estado.lerFicheiroObjecto(FICHEIRO_DEFAULT);
            System.out.println("Estado carregado com sucesso de '" + FICHEIRO_DEFAULT + "'.");

        } catch (Exception e) {
            System.out.println("Aviso: Ficheiro não encontrado ou corrompido.");
            System.out.println("A iniciar aplicação com base de dados vazia...");
            modelo = new DomusControl();
        }
        try {
            TextUI ui = new TextUI(modelo);
            modelo = ui.run();
        } catch (Exception e) {
            System.err.println("Erro crítico durante a execução: " + e.getMessage());
            return;
        }
        try {
            System.out.println("A guardar dados...");
            Estado.guardarEmObjeto(modelo, FICHEIRO_DEFAULT);
            System.out.println("Dados salvos com sucesso! A encerrar...");
        } catch (Exception e) {
            System.err.println("Erro ao gravar estado: " + e.getMessage());
        }
    }
}