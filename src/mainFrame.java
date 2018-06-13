import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

public class mainFrame extends JFrame {

	private JPanel contentPane;
	private JLabel lblMatrix;
	private JLabel label;
	private JSpinner spinner_m11;
	private JSpinner spinner_m12;
	private JSpinner spinner_m21;
	private JSpinner spinner_m22;
	private JLabel label_1;
	private JLabel lblStatus;
	private JLabel lblFeldgre;
	private JLabel lblBreite;
	private JSpinner spinner_length;
	private JLabel lblPx;
	private JLabel lblAktionen;
	private JButton btnQuadrat;
	private JButton btnKreis;
	private JButton btnRahmen;
	private JButton btnEigenesBild;
	private JLabel lblNewLabel;
	private JLabel lblTransformation;
	private JButton btnTransformieren;
	private JButton btnTranslation;
	private JLabel lblOder;
	private JLabel lblWiederholungNach;
	private JLabel lblAnzahlDerDurchgnge;
	private JSpinner spinner_iter;
	private JButton btnAuto;
	private JLabel lblExport;
	private JButton btnKleinesFeld;
	private JButton btnGroesFeld;
	private JCheckBox chk_stop;

	private Matrix akt_matrix;
	private Pixelfeld pxl;
	private boolean mat_valid;
	private pnlkleinFrame pnl_klein;
	private pnlgrossFrame pnl_gross;
	private int iterations_wo_match;
	private boolean matched;
	private Color[][] start;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame frame = new mainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainFrame() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		setResizable(false);
		setTitle("JPoincare - Hauptfenster");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 393, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblMatrix = new JLabel("Matrix");
		lblMatrix.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMatrix.setBounds(10, 11, 46, 14);
		contentPane.add(lblMatrix);

		label = new JLabel("(");
		label.setFont(new Font("Tahoma", Font.PLAIN, 50));
		label.setBounds(81, 11, 46, 75);
		contentPane.add(label);

		spinner_m11 = new JSpinner();
		spinner_m11.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
		spinner_m11.setBounds(105, 28, 52, 20);
		contentPane.add(spinner_m11);

		spinner_m12 = new JSpinner();
		spinner_m12.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
		spinner_m12.setBounds(167, 28, 52, 20);
		contentPane.add(spinner_m12);

		spinner_m21 = new JSpinner();
		spinner_m21.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
		spinner_m21.setBounds(105, 54, 52, 20);
		contentPane.add(spinner_m21);

		spinner_m22 = new JSpinner();
		spinner_m22.setModel(new SpinnerNumberModel(new Integer(2), new Integer(0), null, new Integer(1)));
		spinner_m22.setBounds(167, 54, 52, 20);
		contentPane.add(spinner_m22);

		label_1 = new JLabel(")");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 50));
		label_1.setBounds(229, 11, 46, 75);
		contentPane.add(label_1);

		lblStatus = new JLabel("Status");
		lblStatus.setBounds(285, 11, 82, 36);
		contentPane.add(lblStatus);

		lblFeldgre = new JLabel("Feldgröße");
		lblFeldgre.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFeldgre.setBounds(10, 97, 66, 14);
		contentPane.add(lblFeldgre);

		lblBreite = new JLabel("Seitenlänge:");
		lblBreite.setBounds(61, 147, 66, 14);
		contentPane.add(lblBreite);

		spinner_length = new JSpinner();
		spinner_length.setModel(new SpinnerNumberModel(new Integer(150), new Integer(135), null, new Integer(1)));
		spinner_length.setBounds(137, 144, 58, 20);
		spinner_length.addChangeListener(size_change);
		contentPane.add(spinner_length);

		lblPx = new JLabel("px");
		lblPx.setBounds(205, 147, 46, 14);
		contentPane.add(lblPx);

		lblAktionen = new JLabel("Zeichnen");
		lblAktionen.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAktionen.setBounds(10, 211, 66, 14);
		contentPane.add(lblAktionen);

		btnQuadrat = new JButton("Quadrat");
		btnQuadrat.setBounds(68, 236, 89, 23);
		btnQuadrat.addActionListener(viereck);
		contentPane.add(btnQuadrat);

		btnKreis = new JButton("Kreis");
		btnKreis.setBounds(162, 236, 89, 23);
		btnKreis.addActionListener(kreis);
		contentPane.add(btnKreis);

		btnRahmen = new JButton("Rahmen");
		btnRahmen.setBounds(261, 236, 89, 23);
		btnRahmen.addActionListener(rahmen);
		contentPane.add(btnRahmen);

		btnEigenesBild = new JButton("Eigenes Bild");
		btnEigenesBild.setBounds(68, 263, 282, 23);
		btnEigenesBild.addActionListener(imageimport);
		contentPane.add(btnEigenesBild);

		lblNewLabel = new JLabel(
				"<html>Wird beim Import eines eigenen Bildes automatisch auf dessen Maße gesetzt</html>");
		lblNewLabel.setBounds(249, 109, 101, 82);
		contentPane.add(lblNewLabel);

		lblTransformation = new JLabel("Transformation");
		lblTransformation.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTransformation.setBounds(10, 307, 89, 14);
		contentPane.add(lblTransformation);

		btnTransformieren = new JButton("Transformation");
		btnTransformieren.setBounds(68, 332, 127, 23);
		btnTransformieren.addActionListener(transform);
		contentPane.add(btnTransformieren);

		btnTranslation = new JButton("Translation");
		btnTranslation.setBounds(204, 332, 127, 23);
		btnTranslation.addActionListener(translate);
		contentPane.add(btnTranslation);

		lblOder = new JLabel("oder");
		lblOder.setBounds(173, 363, 46, 14);
		contentPane.add(lblOder);

		lblWiederholungNach = new JLabel();
		lblWiederholungNach.setBounds(285, 49, 82, 44);
		contentPane.add(lblWiederholungNach);

		lblAnzahlDerDurchgnge = new JLabel("Anzahl der Durchgänge:");
		lblAnzahlDerDurchgnge.setBounds(68, 385, 127, 14);
		contentPane.add(lblAnzahlDerDurchgnge);

		spinner_iter = new JSpinner();
		spinner_iter.setBounds(186, 382, 70, 20);
		spinner_iter.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		contentPane.add(spinner_iter);

		btnAuto = new JButton("Durchführen");
		btnAuto.setBounds(261, 381, 113, 23);
		btnAuto.addActionListener(auto);
		contentPane.add(btnAuto);

		lblExport = new JLabel("Export");
		lblExport.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblExport.setBounds(10, 436, 52, 14);
		contentPane.add(lblExport);

		btnKleinesFeld = new JButton("Kleines Feld");
		btnKleinesFeld.setBounds(70, 470, 89, 23);
		btnKleinesFeld.addActionListener(saveKlein);
		contentPane.add(btnKleinesFeld);

		btnGroesFeld = new JButton("Großes Feld");
		btnGroesFeld.setBounds(214, 470, 89, 23);
		btnGroesFeld.addActionListener(saveGross);
		contentPane.add(btnGroesFeld);

		chk_stop = new JCheckBox("Nach einem Zyklus stoppen");
		chk_stop.setBounds(196, 406, 171, 23);
		contentPane.add(chk_stop);

		spinner_m11.addChangeListener(mat_change);
		spinner_m12.addChangeListener(mat_change);
		spinner_m21.addChangeListener(mat_change);
		spinner_m22.addChangeListener(mat_change);

		mat_change.stateChanged(null);

		pnl_klein = new pnlkleinFrame("Kleines Feld", this);
		pnl_klein.setResizable(false);
		pnl_klein.setVisible(true);
		pnl_klein.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pnl_klein.setLocation(50, 150);

		setLocation(pnl_klein.getWidth() + 100, 150);

		pnl_gross = new pnlgrossFrame("Großes Feld", this);
		pnl_gross.setResizable(false);
		pnl_gross.setVisible(false);
		pnl_gross.setLocationRelativeTo(this);
		pnl_gross.setLocation(getWidth() + 300, 150);

		size_change.stateChanged(null);
	}

	private ChangeListener mat_change = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			int m11 = (int) spinner_m11.getValue();
			int m12 = (int) spinner_m12.getValue();
			int m21 = (int) spinner_m21.getValue();
			int m22 = (int) spinner_m22.getValue();
			akt_matrix = new Matrix(m11, m12, m21, m22);

			mat_valid = (Math.abs(akt_matrix.determinante()) == 1);

			btnAuto.setEnabled(mat_valid);
			btnTransformieren.setEnabled(mat_valid);
			btnTranslation.setEnabled(mat_valid);

			lblStatus.setText(
					String.format("<html>Matrix ist <b>%s</b><html>", mat_valid ? "zulässig" : "nicht zulässig"));
			lblStatus.setForeground(mat_valid ? Color.GREEN : Color.RED);
			updateRepeat(0);
			setLast_transformed(false);
		}
	};

	private ChangeListener size_change = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			int length = (int) spinner_length.getValue();
			pxl = new Pixelfeld(length);
			pnl_klein.getContentPane().setSize(new Dimension(length, length));
			pnl_klein.getContentPane().setPreferredSize(new Dimension(length, length));
			pnl_klein.pack();
			setLast_transformed(false);
			resetRepeat();
			updateRepeat(0);
		}
	};

	public void pnl_klein_update() {
		ColorDrawer.draw(pxl.getFeld_klein(), pnl_klein);
	}

	public void pnl_gross_update() {
		pnl_gross.setVisible(true);

		Color[][] feld_gross = pxl.getFeld_gross();
		int width = feld_gross.length;
		int height = feld_gross[0].length;

		pnl_gross.getContentPane().setSize(new Dimension(width, height));
		pnl_gross.getContentPane().setPreferredSize(new Dimension(width, height));
		pnl_gross.pack();
		ColorDrawer.draw(feld_gross, pnl_gross);
	}

	private ActionListener viereck = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int length = (int) spinner_length.getValue() - 1;

			int x0 = (int) (0.20 * length);
			int x1 = length - x0;
			int y0 = (int) (0.20 * length);
			int y1 = length - y0;

			size_change.stateChanged(null);

			pxl.viereckErstellen(x0, y0, x1, y1, Color.BLACK);
			pnl_klein_update();
			resetRepeat();
		}
	};

	private ActionListener rahmen = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int length = (int) spinner_length.getValue() - 2;
			pxl.viereckRandErstellen(0, 0, length, length, Color.BLACK);
			pnl_klein_update();
			resetRepeat();
		}
	};

	private ActionListener kreis = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int length = (int) spinner_length.getValue() - 1;

			size_change.stateChanged(null);

			pxl.kreisErstellen(length / 2, length / 2, 0.3 * length, Color.green);
			pnl_klein_update();
			resetRepeat();
		}

	};

	private ActionListener imageimport = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter("Bilder", ImageIO.getReaderFileSuffixes()));
			if (chooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
				int length = ImageReader.getDimensions(chooser.getSelectedFile());
				if (length == 0) {
					JOptionPane.showMessageDialog(null, "Bild ist nicht quadratisch!", "Fehler!",
							JOptionPane.OK_OPTION);
					return;
				}
				spinner_length.setValue(length);
				ImageReader.read(pxl, chooser.getSelectedFile());
			}
			pnl_klein_update();
			resetRepeat();
		}
	};

	private ActionListener transform = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			pxl.matrixAnwenden(akt_matrix);
			pnl_gross_update();
			setLast_transformed(true);
			resetRepeat();
		}
	};

	private ActionListener translate = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			pxl.zusammenfassen();

			Graphics graphics = pnl_gross.getContentPane().getGraphics();

			int kleinlength = pxl.getFeld_klein().length;
			int grosswidth = pxl.getFeld_gross().length;
			int grossheight = pxl.getFeld_gross()[0].length;

			for (int i = 0; i < grosswidth; i++) {
				if (i % kleinlength == 0) {
					graphics.drawLine(i, 0, i, grossheight);
				}
			}
			for (int i = 0; i < grossheight; i++) {
				if (i % kleinlength == 0) {
					graphics.drawLine(0, i, grosswidth, i);
				}
			}

			pnl_klein_update();
			setLast_transformed(false);
			resetRepeat();
		}
	};

	private ActionListener auto = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (start == null) {
				start = pxl.getFeld_klein();
			}
			int iterations = (int) spinner_iter.getValue();

			for (int i = 0; i < ((chk_stop.isSelected() && matched && iterations > iterations_wo_match)
					? iterations_wo_match : iterations); i++) {
				pxl.matrixAnwenden(akt_matrix);
				pnl_gross_update();
				pxl.zusammenfassen();
				pnl_klein_update();

				if (!matched) {
					iterations_wo_match++;
					if (Arrays.deepEquals(pxl.getFeld_klein(), start)) {
						updateRepeat(iterations_wo_match);
						if (chk_stop.isSelected()) {
							return;
						}
					}

				}
			}
		}

	};

	private void updateRepeat(int i) {
		if (i == 0) {
			lblWiederholungNach.setText("<html>Wiederholung nach N/A Durchgängen</html>");
			matched = false;
			iterations_wo_match = 0;
			start = null;
		} else {
			lblWiederholungNach.setText(String.format("<html>Wiederholung nach <b>%s</b> Durchgängen</html>", i));
			matched = true;
		}
	}

	private void resetRepeat() {
		matched = false;
		iterations_wo_match = 0;
		start = null;
	}

	private ActionListener saveGross = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			saveImage(pxl.getFeld_gross());
		}
	};

	private ActionListener saveKlein = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			saveImage(pxl.getFeld_klein());
		}
	};

	private void saveImage(Color[][] colors) {
		JFileChooser chooser = new JFileChooser();

		chooser.setFileFilter(new FileNameExtensionFilter("PNG", "png"));
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			if (chooser.getSelectedFile().getAbsolutePath().endsWith(".png")) {
				ImageWriter.WriteImage(colors, chooser.getSelectedFile());
			} else {
				ImageWriter.WriteImage(colors, new File(chooser.getSelectedFile().getAbsolutePath() + ".png"));
			}
		}
	}

	private void setLast_transformed(boolean b) {

		if (b) {
			Font font = new JButton().getFont();
			btnTransformieren.setFont(new Font(font.getFontName(), Font.PLAIN, font.getSize()));
			btnTranslation.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize()));
		} else {
			Font font = new JButton().getFont();
			btnTransformieren.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize()));
			btnTranslation.setFont(new Font(font.getFontName(), Font.PLAIN, font.getSize()));
		}

	}
}
