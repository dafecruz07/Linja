import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;
/**
 * Clase encargada de manejar la interfaz gráfica de Linja.
 * @author Miguel Angel Askar Rodriguez - 1355842
 * @author Danny Fernando Cruz Arango   - 1449949
 */


@SuppressWarnings("serial")
public class PrincipalVista extends JFrame {

	private JPanel contentPane;
	private JPanel panel0_1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel5;
	private JPanel panel6;
	private JPanel panel7;
	private JPanel panel8_9;
	private JLabel lblActual;
	private JButton btnIniciarPartida;
	private JButton btnReglas;
	private JButton btnInfo;


	/**
	 * Diseño de la ventana
	 */
	//-------------------------------------------------------------------------
	public PrincipalVista(JButton[][] botones, PrincipalControlador PPalC) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PrincipalVista.class.getResource("/Img/Icon.png")));
		setResizable(false);
		setTitle("Juego Linja");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1350, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelTablero = new JPanel();
		panelTablero.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panelTablero.setBounds(154, 11, 1180, 500);
		contentPane.add(panelTablero);
		panelTablero.setLayout(null);
		
		panel0_1 = new JPanel();
		panel0_1.setBounds(0, 0, 166, 500);
		panelTablero.add(panel0_1);
		panel0_1.setLayout(new GridLayout(6, 2));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Bambu.png")));
		lblNewLabel.setBounds(166, 0, 50, 500);
		panelTablero.add(lblNewLabel);
		
		panel2 = new JPanel();
		panel2.setBounds(216, 0, 83, 500);
		panelTablero.add(panel2);
		panel2.setLayout(new GridLayout(6, 1));
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Bambu.png")));
		label.setBounds(299, 0, 50, 500);
		panelTablero.add(label);
		
		panel3 = new JPanel();
		panel3.setBounds(349, 0, 83, 500);
		panelTablero.add(panel3);
		panel3.setLayout(new GridLayout(6, 1));
		
		JLabel label1 = new JLabel("");
		label1.setIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Bambu.png")));
		label1.setBounds(432, 0, 50, 500);
		panelTablero.add(label1);
		
		panel4 = new JPanel();
		panel4.setBounds(482, 0, 83, 500);
		panelTablero.add(panel4);
		panel4.setLayout(new GridLayout(6, 1));
		
		JLabel label2 = new JLabel("");
		label2.setIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Bambu.png")));
		label2.setBounds(565, 0, 50, 500);
		panelTablero.add(label2);
		
		panel5 = new JPanel();
		panel5.setBounds(615, 0, 83, 500);
		panelTablero.add(panel5);
		panel5.setLayout(new GridLayout(6, 1));
		
		JLabel label3 = new JLabel("");
		label3.setIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Bambu.png")));
		label3.setBounds(698, 0, 50, 500);
		panelTablero.add(label3);
		
		panel6 = new JPanel();
		panel6.setBounds(748, 0, 83, 500);
		panelTablero.add(panel6);
		panel6.setLayout(new GridLayout(6, 1));
		
		JLabel label4 = new JLabel("");
		label4.setIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Bambu.png")));
		label4.setBounds(831, 0, 50, 500);
		panelTablero.add(label4);
		
		panel7 = new JPanel();
		panel7.setBounds(881, 0, 83, 500);
		panelTablero.add(panel7);
		panel7.setLayout(new GridLayout(6, 1));
		
		JLabel label5 = new JLabel("");
		label5.setIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Bambu.png")));
		label5.setBounds(964, 0, 50, 500);
		panelTablero.add(label5);
		
		panel8_9 = new JPanel();
		panel8_9.setBounds(1014, 0, 166, 500);
		panelTablero.add(panel8_9);
		panel8_9.setLayout(new GridLayout(6, 2));
		
		JPanel panelOpciones = new JPanel();
		panelOpciones.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panelOpciones.setBounds(10, 11, 134, 500);
		contentPane.add(panelOpciones);
		panelOpciones.setLayout(null);
		
		btnIniciarPartida = new JButton("Iniciar Partida");
		btnIniciarPartida.setBounds(10, 134, 114, 40);
		panelOpciones.add(btnIniciarPartida);
		
		btnReglas = new JButton("Reglas");
		btnReglas.setBounds(10, 185, 114, 40);
		panelOpciones.add(btnReglas);
		
		btnInfo = new JButton("Info");
		btnInfo.setBounds(10, 236, 114, 40);
		panelOpciones.add(btnInfo);
		
		JLabel lblTurno = new JLabel("TURNO");
		lblTurno.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		lblTurno.setHorizontalAlignment(SwingConstants.CENTER);
		lblTurno.setBounds(10, 356, 114, 34);
		panelOpciones.add(lblTurno);
		
		lblActual = new JLabel();
		lblActual.setFont(new Font("Tekton Pro", Font.PLAIN, 18));
		lblActual.setHorizontalAlignment(SwingConstants.CENTER);
		lblActual.setBounds(10, 401, 114, 34);
		panelOpciones.add(lblActual);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(PrincipalVista.class.getResource("/Img/Logo.png")));
		label_1.setBounds(10, 11, 114, 101);
		panelOpciones.add(label_1);
		btnIniciarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		llenarBotonesTablero(botones);
		
		PrincipalControlador Ppal = PPalC;
		btnIniciarPartida.addActionListener(Ppal);
		btnInfo.addActionListener(Ppal);
		btnReglas.addActionListener(Ppal);
		
	}
	//-------------------------------------------------------------------------	
	/**
	 * Metodo encargado de agregar los botones al panel del usuario
	 * @param btnzUser: Arreglo bidimensional que contiene los botones que van a ser agregados
	 * (JButton[][] btnzUser)
	 */
	public void llenarBotonesTablero(JButton[][] botones){
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 10; j++) {
				if(j<2)  panel0_1.add(botones[i][j]);
				if(j==2) panel2.add(botones[i][j]);
				if(j==3) panel3.add(botones[i][j]);
				if(j==4) panel4.add(botones[i][j]);
				if(j==5) panel5.add(botones[i][j]);
				if(j==6) panel6.add(botones[i][j]);
				if(j==7) panel7.add(botones[i][j]);
				if(j>7)  panel8_9.add(botones[i][j]);
			}
		}
	}
	
	public void actualizarTurno(String nombre) {
		lblActual.setText(nombre);
	}

	/**
	 * @return the btnIniciarPartida
	 */
	public JButton getBtnIniciarPartida() {
		return btnIniciarPartida;
	}

	/**
	 * @param btnIniciarPartida the btnIniciarPartida to set
	 */
	public void setBtnIniciarPartida(JButton btnIniciarPartida) {
		this.btnIniciarPartida = btnIniciarPartida;
	}

	/**
	 * @return the btnReglas
	 */
	public JButton getBtnReglas() {
		return btnReglas;
	}

	/**
	 * @param btnReglas the btnReglas to set
	 */
	public void setBtnReglas(JButton btnReglas) {
		this.btnReglas = btnReglas;
	}

	/**
	 * @return the btnInfo
	 */
	public JButton getBtnInfo() {
		return btnInfo;
	}

	/**
	 * @param btnInfo the btnInfo to set
	 */
	public void setBtnInfo(JButton btnInfo) {
		this.btnInfo = btnInfo;
	}
}
