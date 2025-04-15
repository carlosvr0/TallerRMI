import java.rmi.*;
import java.rmi.registry.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClienteEmpleador extends JFrame implements ActionListener {

    private JTextField cajaIdEmpleador;
    private JTextField cajaNombreEmpresa;
    private JTextField cajaNombrePuesto;
    private JTextField cajaDescripcionPuesto;
    private JTextField cajaRequisitosPuesto;
    private JButton botonEnviar;
    private JButton botonVerInscripciones;
    private JLabel estado;
    private static InterfazBolsaEmpleos servidor;
    private static Registry registro;
    private static String direccionServidor = "127.0.0.1";
    private static String puertoServidor = "3232";

    public ClienteEmpleador() {

        super("Bienvenido a la Bolsa de Empleos");
        setTitle("Bolsa de Empleos - Cliente Empleador");
        getContentPane().setLayout(new BorderLayout());

        cajaIdEmpleador = new JTextField();
        cajaNombreEmpresa = new JTextField();
        cajaNombrePuesto = new JTextField();
        cajaDescripcionPuesto = new JTextField();
        cajaRequisitosPuesto = new JTextField();

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("ID Empleador:"));
        panel.add(cajaIdEmpleador);
        panel.add(new JLabel("Nombre Empresa:"));
        panel.add(cajaNombreEmpresa);
        panel.add(new JLabel("Nombre Puesto:"));
        panel.add(cajaNombrePuesto);
        panel.add(new JLabel("Descripción Puesto:"));
        panel.add(cajaDescripcionPuesto);
        panel.add(new JLabel("Requisitos Puesto:"));
        panel.add(cajaRequisitosPuesto);

        getContentPane().add(panel, BorderLayout.CENTER);

        estado = new JLabel("Estado...");
        getContentPane().add(estado, BorderLayout.NORTH);

        botonEnviar = new JButton("Enviar");
        botonVerInscripciones = new JButton("Ver inscripciones");

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(botonEnviar);
        panelBotones.add(botonVerInscripciones);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        botonEnviar.addActionListener(this);
        botonVerInscripciones.addActionListener(e -> new ConsultarInscripciones(servidor));

        setSize(600, 400);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (!cajaIdEmpleador.getText().equals("") &&
                !cajaNombreEmpresa.getText().equals("") &&
                !cajaNombrePuesto.getText().equals("") &&
                !cajaDescripcionPuesto.getText().equals("") &&
                !cajaRequisitosPuesto.getText().equals("")) {
            Empleo empleo = new Empleo(cajaIdEmpleador.getText(),
                    cajaNombreEmpresa.getText(),
                    cajaNombrePuesto.getText(),
                    cajaDescripcionPuesto.getText(),
                    cajaRequisitosPuesto.getText());
            enviarEmpleo(empleo);
        }
    }

    private static void conectarseAlServidor() {
        try {
            registro = LocateRegistry.getRegistry(direccionServidor,
                    Integer.valueOf(puertoServidor));
            servidor = (InterfazBolsaEmpleos) (registro
                    .lookup("servidorBolsa"));
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    private void enviarEmpleo(Empleo empleo) {
        estado.setText("Enviando oferta de trabajo...");
        try {
            String respuesta = servidor.registrarOfertasTrabajo(empleo);
            estado.setText(respuesta);
        } catch (RemoteException e) {
            estado.setText("Error al enviar la oferta de trabajo.");
            e.printStackTrace();
        }
    }

    static public void main(String args[]) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        conectarseAlServidor();
        ClienteEmpleador ventana = new ClienteEmpleador();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
