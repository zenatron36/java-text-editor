import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
//Now this is going to be epic.
public class TextEditor extends JFrame {

    private JTextArea textArea;
    private String fileName;

    public TextEditor() {
        super("Super Simple Text Editor");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new OpenListener());
        fileMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new SaveListener());
        fileMenu.add(saveItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ExitListener());
        fileMenu.add(exitItem);

        setVisible(true);
    }

    private class OpenListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(TextEditor.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                fileName = file.getAbsolutePath();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(fileName));
                    String line = "";
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    textArea.setText(sb.toString());
                    reader.close();
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(TextEditor.this, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (fileName == null) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(TextEditor.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    fileName = file.getAbsolutePath();
                }
            }
            if (fileName != null) {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                    writer.write(textArea.getText());
                    writer.close();
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(TextEditor.this, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class ExitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new TextEditor();
    }
}
