import java.io.Serializable;

public class Empleo implements Serializable{
    private static final long serialVersionUID = 1L;

    private String idOferta;
    
    private String idEmpleador;
    private String nombreEmpresa;
    private String nombrePuesto;
    private String descripcionPuesto;
    private String requisitosPuesto;
    

    public Empleo() {
    }

    public Empleo(String idEmpleador, String nombreEmpresa, String nombrePuesto, String descripcionPuesto, String requisitosPuesto) {
        this.idEmpleador = idEmpleador;
        this.nombreEmpresa = nombreEmpresa;
        this.nombrePuesto = nombrePuesto;
        this.descripcionPuesto = descripcionPuesto;
        this.requisitosPuesto = requisitosPuesto;
    }

    public String getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(String idOferta) {
        this.idOferta = idOferta;
    }

    public String getIdEmpleador() {
        return idEmpleador;
    }

    public void setIdEmpleador(String idEmpleador) {
        this.idEmpleador = idEmpleador;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNombrePuesto() {
        return nombrePuesto;
    }

    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }

    public String getDescripcionPuesto() {
        return descripcionPuesto;
    }

    public void setDescripcionPuesto(String descripcionPuesto) {
        this.descripcionPuesto = descripcionPuesto;
    }

    public String getRequisitosPuesto() {
        return requisitosPuesto;
    }

    public void setRequisitosPuesto(String requisitosPuesto) {
        this.requisitosPuesto = requisitosPuesto;
    }

    @Override
    public String toString() {
        return  "idOferta: " + idOferta +
                "\n idEmpleador: " + idEmpleador +
                "\n nombreEmpresa: " + nombreEmpresa +
                "\n nombrePuesto: " + nombrePuesto +
                "\n descripcionPuesto: " + descripcionPuesto +
                "\n requisitosPuesto: " + requisitosPuesto;
    }
}
