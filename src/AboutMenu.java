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

    private JFrame parent; // Reference to parent window (for hiding/showing)
    private EmbeddedMediaPlayerComponent mediaPlayerComponent; // VLCJ media player for video playback
    private ArrayList<JLabel> textLabels = new ArrayList<>(); // Labels to scale fonts dynamically
    private JPanel videoPanel; // Panel that holds the video

    public AboutMenu(JFrame parent) {
        this.parent = parent;

        // Hide parent frame if it's open
        if (parent != null) {
            parent.setVisible(false);
        }

        setTitle("About");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Close only this window
        setSize(800, 400);
        setMinimumSize(new Dimension(600, 300));
        setLocationRelativeTo(null); // Center on screen
        setResizable(true);
        setLayout(new BorderLayout(20, 20)); // Main layout with spacing

        // Text panel with vertical layout
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding

        // Info labels
        JLabel devLabel = new JLabel("The author: Poyraz Erdem Koyuncu");
        JLabel idLabel = new JLabel("Student number: 20230702066");
        JLabel emailLabel = new JLabel("E-Mail: poyrazerdem.koyuncu@std.yeditepe.edu.tr");

        // Add labels to list for dynamic resizing
        textLabels.add(devLabel);
        textLabels.add(idLabel);
        textLabels.add(emailLabel);

        // Add labels and spacing to panel
        textPanel.add(devLabel);
        textPanel.add(Box.createVerticalStrut(20));
        textPanel.add(idLabel);
        textPanel.add(Box.createVerticalStrut(20));
        textPanel.add(emailLabel);

        // Add text panel to left side
        add(textPanel, BorderLayout.WEST);

        // Setup VLCJ video player panel
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        videoPanel = new JPanel(new BorderLayout());
        videoPanel.add(mediaPlayerComponent, BorderLayout.CENTER);
        add(videoPanel, BorderLayout.CENTER); // Center of frame

        // Adjust label font sizes dynamically when window is resized
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

        // Load video path and start playing in loop
        String videoPath = new File("src/poy_ai_dans.mp4").getAbsolutePath();
        SwingUtilities.invokeLater(() -> {
            mediaPlayerComponent.mediaPlayer().media().play(videoPath);
            mediaPlayerComponent.mediaPlayer().controls().setRepeat(true);
        });

        // Handle window close — stop video, release VLC resources, show parent again
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

        setVisible(true); // Show the window
    }
}


