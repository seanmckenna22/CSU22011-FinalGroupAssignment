import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This will be our interface control, providing the functionality to our project.
 * It will be run in the terminal
 * @param args
 */
public class Interface {

    /**
     * This method manages the users requests and manages the display of the
     * appropriate data
     *
     * @param result
     * @return Int determining whether or not the user wants to continue
     */
    public static int addPanel(int result) {
        Object[] options2 = {"Quit", "Return To Menu"};

        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("What would you like to do now?"));

        if(result ==0)
        {
            panel2.add(new JLabel("Return Trips by Arrival Time"));
        }
        else if(result==1)
        {
            BusStopSearch.manageRequest();
        }
        else if(result==2)
        {
            panel2.add(new JLabel("Return Shortest Path Between Stops"));
        }

        int result2 = JOptionPane.showOptionDialog(null, panel2, "Vancouver Bus Management System",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                options2, null);

        if(result2 == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Goodbye");
            result2 = 1;
        }
        else
        {
            result2 = 0;
        }

        return result2;
    }

    /**
     * This main method runs in conjunction with the users command and continued utilisation of the service
     * When the user wants to quit the program terminates.
     *
     * @param args
     */
    public static void main(String[] args) {

        boolean Finished = false;

        Object[] options1 = { "Search Trips By Arrival Time", "Search Stops By Name", "Find Shortest Path Between Stops" };

        JPanel panel = new JPanel();
        panel.add(new JLabel("What would you like to do?"));

        while(!Finished)
        {
            //Main menu options
            int result = JOptionPane.showOptionDialog(null, panel, "Vancouver Bus Management System",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    options1, null);

            if(addPanel(result) == 1) {
                Finished = true;
            }

        }
    }
}
