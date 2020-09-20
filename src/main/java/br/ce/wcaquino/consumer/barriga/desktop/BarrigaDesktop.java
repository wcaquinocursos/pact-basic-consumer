package br.ce.wcaquino.consumer.barriga.desktop;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import br.ce.wcaquino.consumer.barriga.service.BarrigaConsumer;

public class BarrigaDesktop extends JFrame {
	private static final long serialVersionUID = -2143094901302014381L;
	BarrigaConsumer consumer = new BarrigaConsumer("https://barrigarest.wcaquino.me");
	
	private JTextField email;
	private JPasswordField password;
	private JButton loginButton;
	
	private JTable table = new JTable();
	private DefaultTableModel tableModel;
	private JButton addButton;
	private JButton editButton;
	private JButton removeButton;
	private JButton resetButton;
	
	private String token;
	
	public BarrigaDesktop() {
		setTitle("Barriga Desktop");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(getSigninPanel());
		
		setSize(500, 250);
		setVisible(true);
	}
	
	private JPanel getSigninPanel() {
		JPanel panel = new JPanel();
		
		email = new JTextField("", 10);
		password = new JPasswordField("", 10);
		loginButton = new JButton("Signin");
		
		loginButton.addActionListener(e -> performAction("login", email.getText(), new String(password.getPassword())));
		
		panel.add(email);
		panel.add(password);
		panel.add(loginButton);
		
		return panel;
	}
	
	@SuppressWarnings("rawtypes")
	private JPanel getAccountsPanel() throws IOException {
		JPanel panel = new JPanel(new BorderLayout());
		tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		tableModel.setNumRows(0);
		tableModel.addColumn("Id");
		tableModel.addColumn("Name");
		table.getColumnModel().getColumn(1).setPreferredWidth(400);
		List<Map> accounts = consumer.getAccounts(token);
		for(Map acc: accounts) {
			tableModel.addRow(new Object[] {acc.get("id"), acc.get("nome")});
		}
		JScrollPane scroll = new JScrollPane(table, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel.add(scroll, BorderLayout.CENTER);
		
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		addButton = new JButton("Add");
		editButton = new JButton("Edit");
		removeButton = new JButton("Remove");
		resetButton = new JButton("Reset");
		buttonsPanel.add(addButton);
		buttonsPanel.add(editButton);
		buttonsPanel.add(removeButton);
		buttonsPanel.add(resetButton);
		panel.add(buttonsPanel, BorderLayout.SOUTH);
		configureButtonsActions();
		
		return panel;
	}

	private void configureButtonsActions() {
		addButton.addActionListener(e -> {
				String accName = JOptionPane.showInputDialog(null, "Enter the Account's name");
				if(accName == null || accName.trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Name is empty", "Fail", JOptionPane.ERROR_MESSAGE);
				} else {
					performAction("add", accName);
				}
			});
		
		resetButton.addActionListener(e -> performAction("reset"));
		
		editButton.addActionListener(e -> {
				int selectedRow = table.getSelectedRow();
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Select one row to proceed", "Fail", JOptionPane.ERROR_MESSAGE);
				} else {
					Integer accId = (Integer) tableModel.getValueAt(selectedRow, 0);
					String accName = JOptionPane.showInputDialog(null, "Enter the New Account's name");
					if(accName == null || accName.trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Name is empty", "Fail", JOptionPane.ERROR_MESSAGE);
					} else {
						performAction("edit", accId.toString(), accName);
					}
				}
			});
		
		removeButton.addActionListener(e -> {
				int selectedRow = table.getSelectedRow();
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Select one row to proceed", "Fail", JOptionPane.ERROR_MESSAGE);
				} else {
					Integer accId = (Integer) tableModel.getValueAt(selectedRow, 0);
					performAction("remove", accId.toString());
				}
			});
	}
	
	private void performAction(String action, String... params) {
		try {
			switch(action) {
				case "add": consumer.insertAccount(params[0], token); break;
				case "reset": consumer.reset(token); break;
				case "edit": consumer.updateAccount(params[0], params[1], token); break;
				case "remove": consumer.deleteAccount(params[0], token); break;
				case "login": token = "JWT " + consumer.login(params[0], params[1]);
							  System.out.println(token);
							  break;
			}
			getContentPane().removeAll();
			getContentPane().add(getAccountsPanel());
			revalidate();
		} catch (IOException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Fail", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		new BarrigaDesktop();
	}
}
