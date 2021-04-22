import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class BusStopSearch {

    /**
     * This skeleton file will search for a bus stop by name or first few characters.
     * This will involve the implementation of a Ternary Search tree
     * We will also need to move key word flag stops from the start of the word to the end
     */

    /**
     * This method manages the users request from the interface, it controls the request that is made to search for the bus stop by name
     */
    public static void manageRequest()
    {
        Object[] options1 = { "Search by Complete Bus Stop Name", "Search by First Few Characters" };

        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("How would you like to to search for a bus stop?"));

        int result = JOptionPane.showOptionDialog(null, panel2, "Vancouver Bus Management System",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                options1, null);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Bus Stop: "));
        JTextField textField = new JTextField(50);
        panel.add(textField);

        Object[] option2 = { "Enter"};

        int result2 = JOptionPane.showOptionDialog(null, panel, "Vancouver Bus Management System",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                option2, null);


        if(result2 ==0)
        {
            String key = textField.getText();
            key = key.toUpperCase();
            TST.manageRequest(result, key);
        }

    }

}

