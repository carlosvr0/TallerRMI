import java.rmi.*;
import java.rmi.registry.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ClienteAspirantes extends JFrame {

    private JTextArea cajaEmpleos;
    private JLabel estado;

    private JTextField campoNombre;
    private JTextField campoCedula;
    private JTextField campoIdOferta;
    
    private JButton botonInscribirse;

    private static InterfazBolsaEmpleos servidor;
    private static Registry registro;
    private static String direccionServidor = "127.0.0.1"; // Cambiar por IP del servidor si es necesario
    private static String puertoServidor = "3232";

    public ClienteAspirantes() {
        super("Bienvenido a la Bolsa de Empleos");
        setTitle("Bolsa de Empleos - Cliente Aspirante");
        getContentPane().setLayout(new BorderLayout());

        cajaEmpleos = new JTextArea(10, 40);
        cajaEmpleos.setEditable(false);
        getContentPane().add(new JScrollPane(cajaEmpleos), BorderLayout.NORTH);

        JPanel panelInscripcion = new JPanel(new GridLayout(4, 2));
        panelInscripcion.add(new JLabel("ID de la Oferta:"));
        campoIdOferta = new JTextField();
        panelInscripcion.add(campoIdOferta);
        
        panelInscripcion.add(new JLabel("Nombre Aspirante:"));
        campoNombre = new JTextField();
        panelInscripcion.add(campoNombre);

        panelInscripcion.add(new JLabel("Cédula:"));
        campoCedula = new JTextField();
        panelInscripcion.add(campoCedula);


        botonInscribirse = new JButton("Inscribirse");
        panelInscripcion.add(botonInscribirse);

        getContentPane().add(panelInscripcion, BorderLayout.CENTER);

        estado = new JLabel("Estado...");
        getContentPane().add(estado, BorderLayout.SOUTH);

        botonInscribirse.addActionListener(e -> {
            Inscripcion inscripcion = new Inscripcion(
                campoIdOferta.getText(),
                campoNombre.getText(),
                campoCedula.getText()   
            );
            try {
                String respuesta = servidor.inscribirseEnOferta(inscripcion);
                estado.setText(respuesta);
            } catch (RemoteException ex) {
                estado.setText("Error al inscribirse");
                ex.printStackTrace();
            }
        });

        setSize(600, 400);
        setVisible(true);
        iniciarHiloDeActualizacion();
    }

    private void iniciarHiloDeActualizacion() {
        Thread hiloActualizador = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    List<Empleo> empleos = servidor.consultarOfertasRegistradas();
                    SwingUtilities.invokeLater(() -> {
                        cajaEmpleos.setText("");
                        for (Empleo empleo : empleos) {
                            cajaEmpleos.append(
                                "ID: " + empleo.getIdOferta() + "\n" +
                                "Empresa: " + empleo.getNombreEmpresa() + "\n" +
                                "Puesto: " + empleo.getNombrePuesto() + "\n" +
                                "Descripción: " + empleo.getDescripcionPuesto() + "\n" +
                                "Requisitos: " + empleo.getRequisitosPuesto() + "\n" +
                                "-----------------------------\n"
                            );
                        }
                    });
                } catch (RemoteException e) {
                    SwingUtilities.invokeLater(() -> estado.setText("Error al obtener empleos."));
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        hiloActualizador.setDaemon(true);
        hiloActualizador.start();
    }

    private static void conectarseAlServidor() {
        try {
            registro = LocateRegistry.getRegistry(direccionServidor, Integer.valueOf(puertoServidor));
            servidor = (InterfazBolsaEmpleos) registro.lookup("servidorBolsa");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        conectarseAlServidor();
        ClienteAspirantes ventana = new ClienteAspirantes();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
