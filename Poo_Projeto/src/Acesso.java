
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Acesso implements Serializable{
    private String senha;
    private String UserName;
    private String Nome;
    private String Morada;
    private String Mail;
    private double peso;
    private int frequenciaCardiaca;
    private TiposUtilizador tipodeutilizador;

    public Acesso(){
        this.senha = "";
        this.UserName = "";
        this.Nome = "";
        this.Morada = "";
        this.Mail = "";
        this.peso = 0;
        this.frequenciaCardiaca = 0;
        this.tipodeutilizador = TiposUtilizador.Outro;
    }

    public Acesso(String senha,String UserName, String Nome, String Morada, String Mail, double peso, int frequenciaCardiaca, String tipodeutilizador) {
        this.senha = senha;
        this.UserName = UserName;
        this.Nome = Nome;
        this.Morada = Morada;
        this.Mail = Mail;
        this.peso = peso;
        this.frequenciaCardiaca = frequenciaCardiaca;
        this.tipodeutilizador = TiposUtilizador.valueOf(tipodeutilizador);
        
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getMorada() {
        return Morada;
    }

    public void setMorada(String morada) {
        Morada = morada;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public double getPeso() {return peso; }

    public void setPeso(double peso) {this.peso = peso; }

    public int getFrequenciaCardiaca() {
        return frequenciaCardiaca;
    }

    public void setFrequenciaCardiaca(int frequenciaCardiaca) {
        this.frequenciaCardiaca = frequenciaCardiaca;
    }

    public TiposUtilizador getTipodeutilizador() {
        return tipodeutilizador;
    }

    public void setTipodeUtilizador(String tipodeutilizador) {
        this.tipodeutilizador = TiposUtilizador.valueOf(tipodeutilizador);
    }

    public Acesso clone(){
        return new Acesso(this.senha, this.UserName, this.Nome, this.Morada, this.Mail, this.peso, this.frequenciaCardiaca, this.tipodeutilizador.name());
    }

    private List<Acesso> lerUtilizadores(){
        try{
            FileInputStream file = new FileInputStream("Utilizadores.bin");
            if(file.available() != 0){
                ObjectInputStream objInput = new ObjectInputStream(file);
                Object obj = objInput.readObject();
                if(obj instanceof List){
                    objInput.close();
                    return (List<Acesso>) obj;
                }
                return new ArrayList<>();
            }

            return new ArrayList<>();
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public HashMap<String, Acesso> carregarUtilizadores(){
        List<Acesso> utilizadores = this.lerUtilizadores();
        HashMap<String, Acesso> utilizadoresMap = new HashMap<>();
        for(Acesso utilizador : utilizadores){
            utilizadoresMap.put(utilizador.getUserName(), utilizador);
        }
        return utilizadoresMap;
    }


    public void guardarUtilizadores(){
        List<Acesso> utilizadores = this.lerUtilizadores();
        utilizadores.add(this);
        try{
            FileOutputStream file = new FileOutputStream("Utilizadores.bin");
            ObjectOutputStream obj = new ObjectOutputStream(file);
            obj.writeObject(utilizadores);
            obj.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}