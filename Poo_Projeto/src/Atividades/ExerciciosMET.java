package Atividades;

public enum ExerciciosMET {
    CorridaModerada(9.5),
    CorridaRápida(11),
    CorridaIntensa(12.8),
    TrilhaPlana(2.5),
    TrilhaModerada(4),
    TrilhaÍngreme(6);


    private double met;

    ExerciciosMET(double met){
        this.met = met;
    }

    public static double getMET(double velocidade){
        if(velocidade < 10){
            return CorridaModerada.met;
        }else if(velocidade < 13.2){
            return CorridaRápida.met;
        }

        return CorridaIntensa.met;
    }

    public static double getMET(int altura) {
        if(altura < 20){
            return TrilhaPlana.met;
        }else if(altura < 100){
            return TrilhaModerada.met;
        }

        return TrilhaÍngreme.met;
    }
}
