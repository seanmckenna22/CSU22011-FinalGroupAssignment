import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class SearchArrivalHandler {

    /**
     * This java file handles the Search Arrival Time Request for JOPtion Pane.
     * It calls on the search Arrival Time manage request method which then sorts the code
     */

    /**
     * This method manages the users request from the interface, it controls the request that is made to search for the bus stop by name
     */
    public static void manageRequest()
    {

        JPanel panel = new JPanel();
        panel.add(new JLabel("\"Please Enter the Arrival Time you are looking for in the format H:MM:SS \": "));
        JTextField textField = new JTextField(50);
        panel.add(textField);

        Object[] option = {"Enter"};

        int result = JOptionPane.showOptionDialog(null,panel,"Vancouver Bus Management System",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,
                null,option,null);

        if(result == 0){
            String key = textField.getText();
            SearchArrivalTime.manageRequest(key);
        }


        }

    }