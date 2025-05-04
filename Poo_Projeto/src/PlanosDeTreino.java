import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlanosDeTreino implements Serializable {
    private HashMap<String, List<PlanoTreino>> planosDeTreino;

    public PlanosDeTreino() {
        this.planosDeTreino = this.lerPlanosDeTreino();
    }

    public PlanosDeTreino(HashMap<String, List<PlanoTreino>> planosDeTreino) {
        this.planosDeTreino = planosDeTreino;
    }

    public PlanosDeTreino(PlanosDeTreino planosDeTreino) {
        this.planosDeTreino = planosDeTreino.getPlanosDeTreino();
    }

    public HashMap<String, List<PlanoTreino>> getPlanosDeTreino() {
        return planosDeTreino;
    }

    public void setPlanosDeTreino(HashMap<String, List<PlanoTreino>> planosDeTreino) {
        this.planosDeTreino = planosDeTreino;
    }

    public void addPlanosTreino(String nome, List<PlanoTreino> planos) {
        if(this.planosDeTreino.containsKey(nome)){
            this.planosDeTreino.get(nome).addAll(planos);
        }else{
            this.planosDeTreino.put(nome, planos);
        }
    }

    public void removePlanosTreino(String nome) {
        this.planosDeTreino.remove(nome);
    }

    public List<PlanoTreino> getPlanosTreino(String nome) {
        return this.planosDeTreino.get(nome);
    }

    public void addPlanoTreino(String nome, PlanoTreino plano) {
        if(this.planosDeTreino.containsKey(nome)){
            this.planosDeTreino.get(nome).add(plano);
        }else{
            List<PlanoTreino> planos = new ArrayList<>();
            planos.add(plano);
            this.planosDeTreino.put(nome, planos);
        }
    }

    public void removePlanoTreino(String nome, PlanoTreino plano) {
        this.planosDeTreino.get(nome).remove(plano);
    }

    public PlanosDeTreino clone() {
        return new PlanosDeTreino(this);
    }

    private HashMap<String, List<PlanoTreino>> lerPlanosDeTreino(){
        try{
            FileInputStream file = new FileInputStream("PlanosTreino.bin");
            if(file.available() != 0){
                ObjectInputStream obj = new ObjectInputStream(file);
                HashMap<String, List<PlanoTreino>> planosDeTreino = (HashMap<String, List<PlanoTreino>>) obj.readObject();
                obj.close();
                return planosDeTreino;
            }
            return new HashMap<>();
        }catch (Exception e){
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    public void guardarPlanosDeTreino(){
        try{
            FileOutputStream file = new FileOutputStream("PlanosTreino.bin");
            ObjectOutputStream obj = new ObjectOutputStream(file);
            obj.writeObject(this.planosDeTreino);
            obj.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}


