
import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

public class Utilizador implements Serializable {
    private String userName;
    private String password;
    private String nome;
    private List<String> casasAdmin;
    private List<String> casasUsufruto;

    public Utilizador() {
        this.userName = "";
        this.password = "";
        this.nome = "";
        this.casasAdmin = new ArrayList<>();
        this.casasUsufruto = new ArrayList<>();
    }

    public Utilizador(String userName, String password, String nome) {
        this.userName = userName;
        this.password = password;
        this.nome = nome;
        this.casasAdmin = new ArrayList<>();
        this.casasUsufruto = new ArrayList<>();
    }

    public Utilizador(Utilizador u) {
        this.userName = u.getUserName();
        this.password = u.getPassword();
        this.nome = u.getNome();
        this.casasAdmin = u.getCasasAdmin();
        this.casasUsufruto = u.getCasasUsufruto();
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getCasasAdmin() {
        return new ArrayList<>(this.casasAdmin);
    }

    public void setCasasAdmin(List<String> casas) {
        this.casasAdmin = new ArrayList<>(casas);

    }

    public List<String> getCasasUsufruto() {
        return new ArrayList<>(this.casasUsufruto);
    }

    public void setCasaUsufruto(List<String> casas) {
        this.casasUsufruto = new ArrayList<>(casas);
    }

    public boolean verificaPassword(String password) {
        return this.password.equals(password);
    }

    public void addCasaAdmin(String idCasa) {
        if (!this.casasAdmin.contains(idCasa)) {
            this.casasAdmin.add(idCasa);
        }
    }

    public void removeCasaAdmin(String idCasa) {
        this.casasAdmin.remove(idCasa);
    }

    public void addCasaUsufruto(String idCasa) {
        if (!this.casasUsufruto.contains(idCasa)) {
            this.casasUsufruto.add(idCasa);
        }
    }

    public void removeCasaUsufruto(String idCasa) {
        this.casasUsufruto.remove(idCasa);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Utilizador: ")
                .append(this.nome)
                .append(" | Username: ")
                .append(this.userName)
                .append("\n -> Casas Administradas: ")
                .append(this.casasAdmin.size())
                .append("\n -> Casas em Usufruto: ")
                .append(this.casasUsufruto.size());
        return sb.toString();
    }

    @Override
    public Utilizador clone() {
        return new Utilizador(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;

        Utilizador outroUser = (Utilizador) o;
        return this.userName.equals(outroUser.getUserName()) &&
                this.password.equals(outroUser.getPassword()) &&
                this.nome.equals(outroUser.getNome()) &&
                this.casasAdmin.equals(outroUser.getCasasAdmin()) &&
                this.casasUsufruto.equals(outroUser.getCasasUsufruto());
    }

}