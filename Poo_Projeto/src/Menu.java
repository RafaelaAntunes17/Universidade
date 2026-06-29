import java.util.*;

public class Menu {

    public interface Handler {
        public void execute();
    }

    public interface PreCondition {
        public boolean validate();
    }

    public static Scanner is = new Scanner(System.in);

    private List<String> opcoes;
    private List<PreCondition> disponivel;
    private List<Handler> handlers;
    private String titulo;

    public Menu(String[] opcoes) {
        this(null, opcoes);
    }

    public Menu(String titulo, String[] opcoes) {
        this.titulo = titulo;
        this.opcoes = Arrays.asList(opcoes);
        this.disponivel = new ArrayList<>();
        this.handlers = new ArrayList<>();
        this.opcoes.forEach(s -> {
            this.disponivel.add(() -> true);
            this.handlers.add(() -> System.out.println("\nATENÇÃO: Opção não disponivel!"));
        });
    }

    public void run() {
        int op;
        do {
            show();
            op = readOption();
            if (op > 0 && !this.disponivel.get(op - 1).validate()) {
                System.out.println("Opção indisponível! Tente novamente.");
            } else if (op > 0) {
                this.handlers.get(op - 1).execute();
            }
        } while (op != 0);
    }

    public void setPreCondition(int i, PreCondition b) {
        this.disponivel.set(i - 1, b);
    }

    public void setHandler(int i, Handler h) {
        this.handlers.set(i - 1, h);
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    private void show() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("==================================");
        if (this.titulo != null) {
            System.out.println("  " + this.titulo);
            System.out.println("==================================");
        }
        for (int i = 0; i < this.opcoes.size(); i++) {
            System.out.print((i + 1) + " - ");
            System.out.println(this.disponivel.get(i).validate() ? this.opcoes.get(i) : "---");
        }
        System.out.println("0 - Sair");
    }

    private int readOption() {
        int op;
        System.out.print("Opção: ");
        try {
            String line = is.nextLine();
            op = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            op = -1;
        }
        if (op < 0 || op > this.opcoes.size()) {
            System.out.println("Opção Inválida!!!");
            op = -1;
        }
        return op;
    }
}