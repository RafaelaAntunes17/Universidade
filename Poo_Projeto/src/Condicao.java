import java.io.Serializable;

public interface Condicao extends Serializable {
    boolean verify(Casa casa);
}