import javax.swing.JFrame;
import javax.swing.JPanel;
public class ViewEngine{
    private JFrame frame;
    private JPanel mainPanel, currentPanel, introDisplay, chamberDisplay, mapDisplay, endDisplay;
    private String gameView;

    public ViewEngine(){
        setup();
    }

    public void setup(){
        frame = new JFrame();
        //introDisplay = new MenuView();
        mainPanel.setLayout(null);
		mainPanel.setSize(frame.getSize());
        //mainPanel.add(introDisplay);
		frame.add(mainPanel);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}