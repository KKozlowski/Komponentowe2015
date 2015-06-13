import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Otwiera okno z informacjami o programie.
 */
public class AboutWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AboutWindow() {
		setTitle("O programie");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 280, 247);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel nazwaProgramu = new JLabel("Organizer", SwingConstants.CENTER);
		nazwaProgramu.setFont(new Font("Tahoma", Font.PLAIN, 27));
		nazwaProgramu.setBounds(10, 11, 254, 41);
		contentPane.add(nazwaProgramu);
		
		JLabel opisProgramu = new JLabel("Prosty organizer zdarze\u0144 i kalendarz.", SwingConstants.CENTER);
		opisProgramu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		opisProgramu.setBounds(10, 63, 254, 41);
		contentPane.add(opisProgramu);
		
		JLabel autorProgramu = new JLabel("by Kamil Koz\u0142owski & Piotr Komorowski", SwingConstants.CENTER);
		autorProgramu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		autorProgramu.setBounds(10, 109, 254, 41);
		contentPane.add(autorProgramu);
		
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		closeButton.setBounds(10, 161, 254, 46);
		contentPane.add(closeButton);
	}
}
