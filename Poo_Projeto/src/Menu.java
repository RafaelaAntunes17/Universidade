import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public interface Handler{
        public void executar();
    }

    public interface PreCondicao{
        public boolean validar();
    }

    public static Scanner scanner = new Scanner(System.in);

    private String titulo;
    private List<String> opcoes;
    private List<PreCondicao> disponibilidade;
    private List<Handler> handlers;

    public Menu(String titulo, String[] op){
        this.titulo = titulo;
        this.opcoes = Arrays.asList(op);
        this.disponibilidade = new ArrayList<>();
        this.handlers = new ArrayList<>();
        this.opcoes.forEach( x -> {
            this.disponibilidade.add(() -> true);
            this.handlers.add(() -> System.out.println("Aviso: Opção não implementada\n"));
        });
    }

    public void run(){
        int op;
        do{
            this.mostrar();

            op = this.lerOpcao();
            if(op > 0 && !this.disponibilidade.get(op - 1).validar()){
                System.out.print("Opção inválida\nEscolha outra opcão\n\n");
            }else if(op > 0){
                this.handlers.get(op - 1).executar();
            }

        }while(op != 0);
    }

    public void setPreCondicao(int i, PreCondicao p){
        this.disponibilidade.set(i - 1, p);
    }

    public void setHandler(int i, Handler h){
        this.handlers.set(i - 1, h);
    }

    private void mostrar(){
        System.out.println("---- " + this.titulo + " ----");

        for(int i = 0; i < this.opcoes.size(); i++){
            System.out.print(i + 1);
            System.out.print(" - ");
            System.out.println(this.disponibilidade.get(i).validar() ? this.opcoes.get(i) : "********");
        }
    }

    private int lerOpcao(){
        int op;
        System.out.print("Opção: ");
        op = scanner.nextInt();

        if(op < 0 || op > this.opcoes.size()){
            System.out.println("Opção inválida");
            return -1;
        }

        return op;
    }

}
