import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Estado {

    public static void guardarEmObjeto(DomusControl modelo, String nomeFicheiro) throws IOException {
        FileOutputStream fos = new FileOutputStream(nomeFicheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(modelo);
        oos.writeInt(Dispositivo.getContador());
        oos.flush();
        oos.close();
    }

    public static DomusControl lerFicheiroObjecto(String nomeFicheiro)
            throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(nomeFicheiro);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            DomusControl modelo = (DomusControl) ois.readObject();
            try {
                int contador = ois.readInt();
                Dispositivo.setContador(contador);
            } catch (Exception e) {

            }
            return modelo;
        }
    }
}