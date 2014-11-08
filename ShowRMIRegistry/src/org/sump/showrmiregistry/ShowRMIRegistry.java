package org.sump.showrmiregistry;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ShowRMIRegistry implements ActionListener, ListSelectionListener {
    // updates details with given resource
    public void updateDetails(String url) {
        String text;

        try {
            Object o = (Object)Naming.lookup(url);
            text = HTMLToolKit.createClassDetails(o.getClass());
        } catch (Exception e) {
            text = e.getMessage();
        }

        this.details.setText(text);
    }

    // updates list with given registry
    public void updateList(String reg) {
        try {
            String[] list = Naming.list(reg);
            for (int i = 0; i < list.length; i++) {
                this.list.setListData(list);
            }
            this.details.setText("");
        } catch (Exception e) {
            this.details.setText(e.getMessage());
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        updateDetails((String)list.getSelectedValue());
    }

    public void actionPerformed(ActionEvent e) {
        updateList(this.registry.getText());
    }

    protected GridBagConstraints createConstraints(int x, int y, int w, int h, double wx, double wy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(2, 2, 0, 0);
        gbc.gridx = x; gbc.gridy = y;
        gbc.gridwidth = w; gbc.gridheight = h;
        gbc.weightx = wx; gbc.weighty = wy;
        return (gbc);
    }

    protected String getDefaultRegistry() {
        String registryText = System.getProperty("org.sump.showrmiregistry.defaultregistry");
//    	String registryText= "rmi://localhost:1099"; // the default registry port http://docs.oracle.com/javase/1.4.2/docs/tooldocs/windows/rmiregistry.html
        return registryText == null ? "rmi://localhost/" : registryText;
    }

    protected Component createComponents() {
        JPanel pane = new JPanel();
        pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pane.setLayout(new GridBagLayout());

        this.registry = new JTextField(getDefaultRegistry());
        pane.add(this.registry, createConstraints(0, 0, 8, 1, 1, 0));

        JButton update = new JButton("Retrieve");
        update.addActionListener(this);
        pane.add(update, createConstraints(8, 0, 2, 1, 0, 0));

        this.list = new JList();
        this.list.addListSelectionListener(this);
        pane.add(new JScrollPane(this.list), createConstraints(0, 1, 10, 1, 1, 0));

        this.details = new JEditorPane("text/html", "");
        this.details.setEditable(false);
        pane.add(new JScrollPane(this.details), createConstraints(0, 2, 10, 6, 1, 1));

        return ((Component)pane);
    }

    public static void main(String[] args) {
    	
    	
		// activate security manager
		if (System.getSecurityManager() == null) {
	      System.setSecurityManager(new RMISecurityManager());
	    }
    			
        JFrame frame = new JFrame("RMI Registry Viewer");
        ShowRMIRegistry app = new ShowRMIRegistry();
        frame.getContentPane().add(app.createComponents());
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.setSize(700,500);
        frame.setVisible(true);
    }


    private JTextField registry;
    private JList list;
    private JEditorPane details;
}
