import java.rmi.*;
import java.rmi.registry.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClienteEmpleador extends JFrame implements ActionListener{

    private JTextField cajaIdEmpleador;
    private JTextField cajaNombreEmpresa;
    private JTextField cajaNombrePuesto;
    private JTextField cajaDescripcionPuesto;
    private JTextField cajaRequisitosPuesto;
	private JButton botonEnviar;
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
		cajaIdEmpleador.addActionListener(this);
        cajaNombreEmpresa.addActionListener(this);
        cajaNombrePuesto.addActionListener(this);
        cajaDescripcionPuesto.addActionListener(this);
        cajaRequisitosPuesto.addActionListener(this);
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("ID Empleador:"));
        panel.add(cajaIdEmpleador);
        panel.add(new JLabel("Nombre Empresa:"));
        panel.add(cajaNombreEmpresa);
        panel.add(new JLabel("Nombre Puesto:"));
        panel.add(cajaNombrePuesto);
        panel.add(new JLabel("Descripcion Puesto:"));
        panel.add(cajaDescripcionPuesto);
        panel.add(new JLabel("Requisitos Puesto:"));
        panel.add(cajaRequisitosPuesto);
        getContentPane().add(panel, BorderLayout.CENTER);
		botonEnviar = new JButton("Enviar");
		botonEnviar.addActionListener(this);
		estado = new JLabel("Estado...");

		getContentPane().add(panel);
		getContentPane().add(botonEnviar, BorderLayout.EAST);
		getContentPane().add(estado, BorderLayout.SOUTH);

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
			// obtener el registro
			registro = LocateRegistry.getRegistry(direccionServidor,
					Integer.valueOf(puertoServidor));
			// creando el objeto remoto
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
