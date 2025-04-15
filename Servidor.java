import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.List;

public class Servidor extends UnicastRemoteObject implements InterfazBolsaEmpleos{

    private static GUIServidor ventana;
	private int estePuerto;
	private String estaIP;
    private Registry registro;
    private static int contadorOfertas = 100; 

    public Servidor() throws RemoteException {
		try {
			// obtener la direccion de este host.
			estaIP = (InetAddress.getLocalHost()).toString();
		} catch (Exception e) {

			throw new RemoteException("No se puede obtener la direccion IP.");
		}
		estePuerto = 3232; // asignar el puerto que se registra
		ventana.anadirEntradas("Conexion establecida por...\nEsta direccion="
				+ estaIP + ", y puerto=" + estePuerto);
		try {

			// crear el registro y ligar el nombre y objeto.
			registro = LocateRegistry.createRegistry(estePuerto);
			registro.rebind("servidorBolsa", this);
		} catch (RemoteException e) {
			throw e;
		}
	}

    public synchronized String registrarOfertasTrabajo(Empleo empleo) throws RemoteException {
        try {
            System.out.println("Registrando oferta de trabajo: " + empleo.getNombrePuesto() + " en " + empleo.getNombreEmpresa());
            FileWriter escritor = new FileWriter("Empleos.txt", true);
            empleo.setIdOferta(String.valueOf(contadorOfertas));
            contadorOfertas++;
            escritor.write(empleo.toString() + System.lineSeparator() + System.lineSeparator());
            escritor.close();
            ventana.anadirEntradas("Oferta registrada: " + empleo.getNombrePuesto() + " en " + empleo.getNombreEmpresa());
            return ("Oferta registrada satisfactoriamente");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al guardar la información:");
            e.printStackTrace();
            return ("Error al registrar la oferta de trabajo");
        }
    }

    public synchronized List<Empleo> consultarOfertasRegistradas() throws RemoteException {
        List<Empleo> listaEmpleos = new ArrayList<>();
        try {
            FileReader lector = new FileReader("Empleos.txt");
            StringBuilder contenido = new StringBuilder();
            int caracter;
            while ((caracter = lector.read()) != -1) {
                contenido.append((char) caracter);
            }
            lector.close();
            String[] lineas = contenido.toString().split(System.lineSeparator() + System.lineSeparator());
            for (String linea : lineas) {
                String[] partes = linea.split("\n");
                if (partes.length >= 6) {
                    Empleo empleo = new Empleo();
                    empleo.setIdOferta(partes[0].split(": ")[1]);
                    empleo.setIdEmpleador(partes[1].split(": ")[1]);
                    empleo.setNombreEmpresa(partes[2].split(": ")[1]);
                    empleo.setNombrePuesto(partes[3].split(": ")[1]);
                    empleo.setDescripcionPuesto(partes[4].split(": ")[1]);
                    empleo.setRequisitosPuesto(partes[5].split(": ")[1]);
                    listaEmpleos.add(empleo);
                }
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo:");
            e.printStackTrace();
        }
        return listaEmpleos;
    }

    public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		ventana = new GUIServidor();
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			new Servidor();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

    public synchronized String inscribirseEnOferta(Inscripcion inscripcion) throws RemoteException {
        try {
            FileWriter escritor = new FileWriter("Inscripciones.txt", true);
            escritor.write(inscripcion.toString() + System.lineSeparator() + System.lineSeparator());
            escritor.close();
            ventana.anadirEntradas("Inscripción recibida: " + inscripcion.getNombreAspirante());
            return "Inscripción registrada satisfactoriamente";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al registrar la inscripción";
        }
    }
    
   @Override
   public synchronized List<Inscripcion> consultarInscripcionesPorOferta (String idOferta) throws RemoteException {
    List<Inscripcion> inscripciones = new ArrayList<>();
    try{
        FileReader lector = new FileReader("Inscripciones.txt");
        StringBuilder contenido = new StringBuilder();
        int caracter;
        while ((caracter = lector.read())!= -1) {
            contenido.append((char)caracter);
        }
        lector.close();

        String[] bloques = contenido.toString().split(System.lineSeparator() + System.lineSeparator());
        for(String bloque : bloques){
            String[] partes = bloque.split("\n");
            if(partes.length >=3){
                String nombre = partes[0].split(": ")[1];
                String cedula = partes[1].split(": ")[1];
                String id = partes[2].split(": ")[1];

                if(id.equals(idOferta)){
                    Inscripcion i= new Inscripcion(id, nombre, cedula);
                    inscripciones.add(i);
                }
            }
        }
    } catch (IOException e){
        e.printStackTrace();
    }
    return inscripciones;
   }
}
