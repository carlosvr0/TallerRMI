import java.rmi.*;
import java.util.List;

public interface InterfazBolsaEmpleos extends Remote{
    
    String registrarOfertasTrabajo(Empleo empleo) throws RemoteException;
    List<Empleo> consultarOfertasRegistradas() throws RemoteException;
    String inscribirseEnOferta(Inscripcion inscripcion) throws RemoteException;
    List<Inscripcion> consultarInscripcionesPorOferta(String idOferta) throws RemoteException;

}
