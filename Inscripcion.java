import java.io.Serializable;

public class Inscripcion implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombreAspirante;
    private String cedula;
    private String idOferta;

    public Inscripcion( String idOferta, String nombreAspirante, String cedula) {
        this.idOferta = idOferta;
        this.nombreAspirante = nombreAspirante;
        this.cedula = cedula;
    }

    public String getNombreAspirante() {
        return nombreAspirante;
    }

    public String getCedula() {
        return cedula;
    }

    public String getIdOferta() {
        return idOferta;
    }
    
    @Override
    public String toString() {
        return "Aspirante: " + nombreAspirante + "\nCédula: " + cedula + "\nPuesto: " + idOferta;
    }
}
