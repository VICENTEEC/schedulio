package es.mdef.schedulio.entidades;

import java.time.LocalTime;

public class HoraDisponibilidad {
    
    private LocalTime hora;
    private String disponibilidad;

    public HoraDisponibilidad(LocalTime hora, String disponibilidad) {
        this.hora = hora;
        this.disponibilidad = disponibilidad;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}