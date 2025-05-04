package Atividades;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Atividades implements Serializable{
    private List<Atividade> atividades;

    public Atividades(){
        this.atividades = this.lerAtividades();
    }

    public Atividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    private List<Atividade> lerAtividades(){
        try{
            FileInputStream file = new FileInputStream("Atividades/Atividades.bin");
            if(file.available() == 0){
                return new ArrayList<>();
            }

            ObjectInputStream ojbInput = new ObjectInputStream(file);
            Object obj = ojbInput.readObject();

            if(obj instanceof List){
                ojbInput.close();
                return (List<Atividade>) obj;
            }

            return new ArrayList<>();
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public void guardarAtividades(){
        try{
            FileOutputStream file = new FileOutputStream("Atividades/Atividades.bin");
            ObjectOutputStream objOutput = new ObjectOutputStream(file);
            objOutput.writeObject(this.atividades);
            objOutput.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void criarAtividade(Atividade atividade){
        this.atividades.add(atividade);
    }

    public void apagarAtividade(String nomeAtividade){
        this.atividades.removeIf(atividade -> atividade.getNomeAtividade().equals(nomeAtividade));
    }

    public void listarAtividades(){
        System.out.println("\n-------------------\n");
        for(Atividade atividade : this.atividades){
            System.out.println(atividade.toString());
            System.out.println("\n-------------------\n");
        }
    }

    public List<Atividade> listarAtividadesPorTipo(Class<?> classe){
        List<Atividade> atividadeList = new ArrayList<>();
        System.out.println("\n-------------------\n");
        for(Atividade atividade : this.atividades){
            if(classe.isInstance(atividade)){
                System.out.println(atividade.toString());
                atividadeList.add(atividade);
                System.out.println("\n-------------------\n");
            }
        }

        return atividadeList;
    }
}
