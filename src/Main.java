import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main {

    private Clip clip;
    private boolean isPlaying = false;

    JFrame frame1;
    JButton playButton, browseButton;
    JButton stopButton;
    JButton resetButton;
    JButton quitButton;
    JPanel panel1;

    Container c;

    Main() {
        frame1 = new JFrame("Music Player");
        frame1.setBounds(200, 100, 600, 600);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setResizable(false);

        c = frame1.getContentPane();
        c.setLayout(null);

        // Load icons
        ImageIcon playIcon = new ImageIcon("C:\\Users\\it solution\\Downloads\\play2.png");
        ImageIcon pauseIcon = new ImageIcon("C:\\Users\\it solution\\Downloads\\pause.png");
        ImageIcon resetIcon = new ImageIcon("C:\\Users\\it solution\\Downloads\\icons8-reset-48.png");
        ImageIcon exitIcon = new ImageIcon("C:\\Users\\it solution\\Downloads\\exit.png");
        ImageIcon speakerIcon = new ImageIcon("C:\\Users\\it solution\\Downloads\\icons8-speaker-96.png");
        ImageIcon fileIcon = new ImageIcon("C:\\Users\\it solution\\Downloads\\icons8-file-48.png");

        // Create "Play," "Pause," "Reset," and "Exit" buttons
        playButton = new JButton(playIcon);
        stopButton = new JButton(pauseIcon);
        resetButton = new JButton(resetIcon);
        quitButton = new JButton(exitIcon);

        playButton.setBounds(150, 450, 90, 50);
        stopButton.setBounds(250, 450, 90, 50);
        resetButton.setBounds(350, 450, 90, 50);
        quitButton.setBounds(450, 450, 90, 50);

        // Add ActionListener for each button
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPlaying) {
                    playAudio();
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPlaying) {
                    stopAudio();
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetAudio();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitAudio();
            }
        });

        // Create "Browse" button
        browseButton = new JButton(fileIcon);
        browseButton.setBackground(new Color(91, 148, 145, 250));
        browseButton.setBounds(50, 450, 70, 50);

        // Add ActionListener for "Browse" button
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAudioFile();
            }
        });


        panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(50, 25, 500, 350);
        panel1.setBackground(new Color(91, 148, 145, 250));
        panel1.setBorder(BorderFactory.createLineBorder(Color.yellow));

        JLabel backgroundLabel = new JLabel(speakerIcon);
        backgroundLabel.setBounds(200, 100, speakerIcon.getIconWidth(), speakerIcon.getIconHeight());
        panel1.add(backgroundLabel);

        // Add components to the container
        c.add(panel1);
        c.add(playButton);
        c.add(stopButton);
        c.add(resetButton);
        c.add(quitButton);
        c.add(browseButton);

        frame1.setVisible(true);
    }

    private void playAudio() {
        try {
            File file = new File("C:\\Users\\it solution\\Music\\wav.wav");   // default song
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);

            if (clip == null) {
                clip = AudioSystem.getClip();
                clip.open(stream);
            }

            clip.start();
            isPlaying = true;
        } catch (Exception e) {
            System.out.println("Error playing audio: " + e.getMessage());
        }
    }

    private void stopAudio() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPlaying = false;
        }
    }

    private void resetAudio() {
        if (clip != null) {
            clip.setMicrosecondPosition(0);
        }
    }

    private void quitAudio() {
        if (clip != null) {
            clip.close();
        }
        System.exit(0);
    }

    private void selectAudioFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int option = fileChooser.showOpenDialog(frame1);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile != null) {
                playAudio(selectedFile);
            }
        }
    }

    private void playAudio(File selectedFile) {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(selectedFile);

            if (clip != null) {
                clip.stop();
                clip.close();
            }

            clip = AudioSystem.getClip();
            clip.open(stream);

            clip.start();
            isPlaying = true;
        } catch (Exception e) {
            System.out.println("Error playing audio: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
