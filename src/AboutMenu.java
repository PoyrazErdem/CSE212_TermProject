import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

public class AboutMenu extends JFrame {

    private JFrame parent;
    private EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private ArrayList<JLabel> textLabels = new ArrayList<>();
    private JPanel videoPanel;

    public AboutMenu(JFrame parent) {
        this.parent = parent;

        if (parent != null) {
            parent.setVisible(false);
        }

        setTitle("About");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setMinimumSize(new Dimension(600, 300));
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new BorderLayout(20, 20));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel devLabel = new JLabel("The author: Poyraz Erdem Koyuncu");
        JLabel idLabel = new JLabel("Student number: 20230702066");
        JLabel emailLabel = new JLabel("E-Mail: poyrazerdem.koyuncu@std.yeditepe.edu.tr");

        textLabels.add(devLabel);
        textLabels.add(idLabel);
        textLabels.add(emailLabel);

        textPanel.add(devLabel);
        textPanel.add(Box.createVerticalStrut(20));
        textPanel.add(idLabel);
        textPanel.add(Box.createVerticalStrut(20));
        textPanel.add(emailLabel);

        add(textPanel, BorderLayout.WEST);

        // VLCJ Video panel
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        videoPanel = new JPanel(new BorderLayout());
        videoPanel.add(mediaPlayerComponent, BorderLayout.CENTER);
        add(videoPanel, BorderLayout.CENTER);

        // Resize fonts dynamically with window
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int height = getHeight();
                int newFontSize = Math.max(12, height / 20);
                for (JLabel label : textLabels) {
                    label.setFont(new Font("SansSerif", Font.PLAIN, newFontSize));
                }
            }
        });

        String videoPath = new File("src/poy_ai_dans.mp4").getAbsolutePath();
        SwingUtilities.invokeLater(() -> {
            mediaPlayerComponent.mediaPlayer().media().play(videoPath);
            mediaPlayerComponent.mediaPlayer().controls().setRepeat(true);
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                mediaPlayerComponent.mediaPlayer().controls().stop();
                mediaPlayerComponent.release();
                if (parent != null) {
                    parent.setVisible(true);
                }
            }
        });

        setVisible(true);
    }
}



