package es.mdef.schedulio.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation="servicio")
public class ServicioModel extends RepresentationModel<ServicioModel> {
   
	private String nombre;
    private String subservicio;
    private double precio;
    
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getSubservicio() {
		return subservicio;
	}
	
	public void setSubservicio(String subservicio) {
		this.subservicio = subservicio;
	}
	
	public double getPrecio() {
		return precio;
	}
	
	public void setPrecio(double precio) {
		this.precio = precio;
	}     
}
