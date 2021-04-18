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
    public static void manageRequest()
    {
        Object[] options1 = { "Search by complete Bus Stop Name", "Search by first few characters" };

        int result = JOptionPane.showOptionDialog(null, null, "Vancouver Bus Management System",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                options1, null);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Bus Stop: "));
        JTextField textField = new JTextField(30);
        panel.add(textField);

        Object[] option2 = { "Enter"};

        int result2 = JOptionPane.showOptionDialog(null, panel, "Vancouver Bus Management System",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                option2, null);

        if(result2 ==0)
        {
            TST.manageRequest(result, textField.getText());
        }

    }

}

