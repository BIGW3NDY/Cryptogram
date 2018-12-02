package CodeGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import CodeService.*;

public class MainFrame extends JFrame {
	JButton transform_encode_btn;
	JButton transform_decode_btn;
	JTextArea input_en;
	JTextArea input_de;
	JTextArea output_en;
	JTextArea output_de;
	JTextField input_key_encode;
	JTextField input_key_decode;
	JRadioButton stroption_en;
	JRadioButton fileoption_en;
	JRadioButton stroption_de;
	JRadioButton fileoption_de;
	ButtonGroup en_btn_grp = new ButtonGroup();
	ButtonGroup de_btn_grp = new ButtonGroup();

	JLabel label_key_encode;
	JLabel label_key_decode;

	JRadioButton Ceasar_opt;
	JRadioButton Des_opt;
	JRadioButton RSA_opt;
	ButtonGroup opt_grp = new ButtonGroup();

	Ceasar ceasar = new Ceasar();
	Des des = new Des();
	RSA rsa = new RSA();

	public MainFrame() {
		this.setTitle("Caesar密码");
		this.setVisible(true);

		JPanel pane = new JPanel();
		this.setContentPane(pane);
		pane.setLayout(new FlowLayout());

		JPanel optionPane = new JPanel();
		optionPane.setLayout(new FlowLayout());
		Ceasar_opt = new JRadioButton("Ceasar");
		Des_opt = new JRadioButton("Des");
		RSA_opt = new JRadioButton("RSA");
		opt_grp.add(Ceasar_opt);
		opt_grp.add(Des_opt);
		opt_grp.add(RSA_opt);
		Ceasar_opt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (Ceasar_opt.isSelected()) {
					label_key_encode.setText("秘钥:");
					label_key_decode.setText("秘钥:");
				}
			}
		});
		Des_opt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (Des_opt.isSelected()) {
					label_key_encode.setText("秘钥产生文件");
					label_key_decode.setText("秘钥存储文件");
				}
			}
		});
		RSA_opt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (RSA_opt.isSelected()) {
					label_key_encode.setText("公钥产生文件");
					label_key_decode.setText("私钥存储文件");
				}
			}

		});

		optionPane.add(Ceasar_opt);
		optionPane.add(Des_opt);
		optionPane.add(RSA_opt);
		pane.add(optionPane);

		/********************************* encode part *******************************/

		// input
		stroption_en = new JRadioButton("输入字符串");
		fileoption_en = new JRadioButton("输入件路径");
		en_btn_grp.add(stroption_en);
		en_btn_grp.add(fileoption_en);

		input_en = new JTextArea();
		input_key_encode = new JTextField(5);
		label_key_encode = new JLabel("秘钥：");
		inputView inputview_encode = new inputView(input_en, input_key_encode, stroption_en, fileoption_en,
				label_key_encode);

		// output
		JLabel label_encode_output = new JLabel("加密结果");
		output_en = new JTextArea();
		outputView outputview_encode = new outputView(output_en, label_encode_output);

		transform_encode_btn = new JButton("转换");
		transform_encode_btn.addMouseListener(btn_listener);

		mainView main_view_encode = new mainView(inputview_encode, outputview_encode, transform_encode_btn, "加密");

		pane.add(main_view_encode);
		/****************************************************************************/

		
		/********************************* decode part *******************************/

		// input
		stroption_de = new JRadioButton("输入字符串");
		fileoption_de = new JRadioButton("输入文件路径");
		de_btn_grp.add(stroption_de);
		de_btn_grp.add(fileoption_de);

		input_de = new JTextArea();
		input_key_decode = new JTextField(5);
		label_key_decode = new JLabel("秘钥");
		inputView inputview_decode = new inputView(input_de, input_key_decode, stroption_de, fileoption_de,
				label_key_decode);

		// output
		JLabel label_decode_output = new JLabel("加密结果");
		output_de = new JTextArea();
		outputView outputview_decode = new outputView(output_de, label_decode_output);

		transform_decode_btn = new JButton("转换");
		transform_decode_btn.addMouseListener(btn_listener);

		mainView main_view_decode = new mainView(inputview_decode, outputview_decode, transform_decode_btn, "解密");

		pane.add(main_view_decode);
		/****************************************************************************/

		this.setSize(650, 650);

	}

	MouseListener btn_listener = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			try {
				/***************************************加密按钮事件****************************************/
				if (arg0.getSource() == transform_encode_btn) {
					int mode=0;
					if(stroption_en.isSelected())
						mode = 1;
					else if(fileoption_en.isSelected())
						mode = 2;
					
					String str = input_en.getText();
					
					//Ceasar
					if (Ceasar_opt.isSelected()) {
						String key = input_key_encode.getText();
						String res = ceasar.encode(str, key, mode);
						output_en.setText(res);
					} 
					//Des
					else if (Des_opt.isSelected()) {
						des.Generate_key(input_key_encode.getText());
						output_en.setText(des.encode(str, input_key_encode.getText(), mode));

					}
					//RSA
					else if (RSA_opt.isSelected()) {
						rsa.Generate_key(input_key_encode.getText(), "prkeyof_" + input_key_encode.getText());
						String output = rsa.encode(str, input_key_encode.getText(), mode);
						output += "\n\n秘钥文件产生在" + "prkeyof_" + input_key_encode.getText();
						output_en.setText(output);
					}
				}
				/***************************************************************************************/
				
				
				
				/**************************************解密按钮事件****************************************/
				else if (arg0.getSource() == transform_decode_btn) {
					String str = input_de.getText();
					
					int mode = 0;
					if(stroption_de.isSelected())
						mode = 1;
					else if(fileoption_de.isSelected())
						mode = 2;
					
					//Ceasar
					if (Ceasar_opt.isSelected()) {
						String key = input_key_decode.getText();
						String res = ceasar.decode(str, key, mode);
						output_de.setText(res);
					}
					//Des
					else if (Des_opt.isSelected()) {
						output_de.setText(des.decode(str, input_key_decode.getText(), mode));
					}
					//RSA
					else if (RSA_opt.isSelected()) {
						output_de.setText(rsa.decode(str, input_key_decode.getText(), mode));
					}
				}
				/***********************************************************************************/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void mouseEntered(MouseEvent arg0) {
		}

		public void mouseExited(MouseEvent arg0) {
		}

		public void mousePressed(MouseEvent arg0) {
		}

		public void mouseReleased(MouseEvent arg0) {
		}

	};

	public static void main(String[] args) {
		MainFrame newFrame = new MainFrame();
	}
}

class inputView extends JPanel {
	public inputView(JTextArea code, JTextField key, JRadioButton b1, JRadioButton b2, JLabel label_key) {
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(250, 230));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(200, 150));

		// code.setPreferredSize(new Dimension(200,150));
		code.setLineWrap(true);
		code.setWrapStyleWord(true);
		scrollPane.setViewportView(code);

		label_key.setPreferredSize(new Dimension(100, 30));

		this.add(b1);
		this.add(b2);
		this.add(scrollPane);
		this.add(label_key);
		this.add(key);

	}
}

class outputView extends JPanel {
	public outputView(JTextArea code, JLabel label) {
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(250, 230));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(200, 150));

		// code.setPreferredSize(new Dimension(200,150));
		code.setLineWrap(true);
		code.setWrapStyleWord(true);
		scrollPane.setViewportView(code);

		this.add(label);
		this.add(scrollPane);
	}
}

class mainView extends JPanel {
	public mainView(inputView input, outputView output, JButton trans, String str) {
		JPanel p = new JPanel(null);
		p.add(trans);
		trans.setBounds(0, 70, 100, 50);

		JLabel title = new JLabel("================== " + str + " ==================");
		title.setFont(new Font("宋体", Font.BOLD, 25));

		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(600, 270));
		this.add(title, BorderLayout.NORTH);
		this.add(input, BorderLayout.WEST);
		this.add(p, BorderLayout.CENTER);
		this.add(output, BorderLayout.EAST);
	}
}