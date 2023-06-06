package es.mdef.schedulio.entidades;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Horario {
    public static List<LocalTime> getHorario() {
        List<LocalTime> horario = new ArrayList<>();

        horario.add(LocalTime.of(9, 0));
        horario.add(LocalTime.of(9, 30));
        horario.add(LocalTime.of(10, 0));
        horario.add(LocalTime.of(10, 30));
        horario.add(LocalTime.of(11, 0));
        horario.add(LocalTime.of(11, 30));
        horario.add(LocalTime.of(12, 0));
        horario.add(LocalTime.of(12, 30));
        horario.add(LocalTime.of(13, 0));
        horario.add(LocalTime.of(13, 30));
        horario.add(LocalTime.of(17, 0));
        horario.add(LocalTime.of(17, 30));
        horario.add(LocalTime.of(18, 0));
        horario.add(LocalTime.of(18, 30));
        horario.add(LocalTime.of(19, 0));
        horario.add(LocalTime.of(19, 30));

        return horario;
    }
}

