import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.Serializable;

public class PlanosRealizados implements Serializable {
    private HashMap<String, List<PlanoRealizado>> planosRealizados;

    public PlanosRealizados() {
        this.planosRealizados = this.lerPlanosRealizados();
    }

    public PlanosRealizados(HashMap<String, List<PlanoRealizado>> planosRealizados) {
        this.planosRealizados = planosRealizados;
    }

    public PlanosRealizados(PlanosRealizados planosRealizados) {
        this.planosRealizados = planosRealizados.getPlanosRealizados();
    }

    public HashMap<String, List<PlanoRealizado>> getPlanosRealizados() {
        return planosRealizados;
    }

    public void setPlanosRealizados(HashMap<String, List<PlanoRealizado>> planosRealizados) {
        this.planosRealizados = planosRealizados;
    }

    public PlanosRealizados clone() {
        return new PlanosRealizados(this);
    }

    public void addPlanosRealizados(String nome, List<PlanoRealizado> planos) {
        if(this.planosRealizados.containsKey(nome)){
            this.planosRealizados.get(nome).addAll(planos);
        } else {
            this.planosRealizados.put(nome, planos);
        }
    }

    public void removePlanosRealizados(String nome) {
        this.planosRealizados.remove(nome);
    }

    public List<PlanoRealizado> getPlanosRealizados(String nome) {
        return this.planosRealizados.get(nome);
    }

    public void addPlanoRealizado(String nome, PlanoRealizado plano) {
        if(this.planosRealizados.containsKey(nome)){
            this.planosRealizados.get(nome).add(plano);
        } else {
            List<PlanoRealizado> planos = new ArrayList<>();
            planos.add(plano);
            this.planosRealizados.put(nome, planos);
        }
    }

    public void removePlanoRealizado(String nome, PlanoRealizado plano) {
        this.planosRealizados.get(nome).remove(plano);
    }

    private HashMap<String, List<PlanoRealizado>> lerPlanosRealizados() {
        try{
            FileInputStream file = new FileInputStream("PlanosRealizados.bin");

            if(file.available() != 0){
                ObjectInputStream objInput = new ObjectInputStream(file);
                Object obj = objInput.readObject();

                if(obj instanceof HashMap){
                    objInput.close();
                    return (HashMap<String, List<PlanoRealizado>>) obj;
                }
            }
            return new HashMap<>();
        }catch (Exception e){
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    public void guardarPlanosRealizados(){
        try{
            FileOutputStream file = new FileOutputStream("PlanosRealizados.bin");
            ObjectOutputStream objOutput = new ObjectOutputStream(file);
            objOutput.writeObject(this.planosRealizados);
            objOutput.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void toStringPlanosRealizados(String userName){
        System.out.println("\n-------------------\n");
        for(String nome : this.planosRealizados.keySet()){
            if(nome.equals(userName)){
                for(PlanoRealizado plano : this.planosRealizados.get(nome)){
                    System.out.println(plano.toString());
                    System.out.println("\n-------------------\n");
                }

                System.out.println("\n\n");
                return;
            }
        }
    }
}


class PlanoRealizado implements Serializable{
    private String nomePlano;
    private double calorias;
    private LocalDate data;
    private int freqCardiacaMedia;

    public PlanoRealizado(){
        this.nomePlano = "";
        this.calorias = 0;
        this.data = LocalDate.now();
        this.freqCardiacaMedia = 0;
    }

    public PlanoRealizado(String nomePlano, double calorias, LocalDate data, int freqCardiacaMedia){
        this.nomePlano = nomePlano;
        this.calorias = calorias;
        this.data = data;
        this.freqCardiacaMedia = freqCardiacaMedia;
    }

    public PlanoRealizado(PlanoRealizado planoRealizado){
        this.nomePlano = planoRealizado.getNomePlano();
        this.calorias = planoRealizado.getCalorias();
        this.data = planoRealizado.getData();
        this.freqCardiacaMedia = planoRealizado.getFreqCardiacaMedia();
    }

    public double getCalorias() {
        return calorias;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public String getNomePlano() {
        return nomePlano;
    }

    public void setNomePlano(String nomePlano) {
        this.nomePlano = nomePlano;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getFreqCardiacaMedia() {
        return freqCardiacaMedia;
    }

    public void setFreqCardiacaMedia(int freqCardiacaMedia) {
        this.freqCardiacaMedia = freqCardiacaMedia;
    }

    public PlanoRealizado clone(){
        return new PlanoRealizado(this);
    }

    public String toString(){
        return "Nome do Plano: " + this.nomePlano + "\nCalorias: " + this.calorias + "\nData: " + this.data;
    }

}
