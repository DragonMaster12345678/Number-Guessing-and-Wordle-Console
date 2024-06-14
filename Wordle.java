import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Wordle extends JFrame {
  private ArrayList<String> words = new ArrayList<String>();
  GridBagConstraints gbc;
  private JPanel panel;
  private JTextField input;
  private JLabel prompt;
  private JLabel warning;
  private JButton submit;
  private Font font;
  private String answer;
  private int guesses = 0;
  private int position = 3;

  public Wordle() throws FileNotFoundException {
    super("Temu Wordle");

    // funny snippet that i "borrowed"
    panel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
          BufferedImage image = ImageIO.read(new FileInputStream("wordcloud.png"));
          g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

          BufferedImage blank = ImageIO.read(new FileInputStream("blank.png"));
          g.drawImage(blank, getWidth() / 2 - 200, getHeight() / 2 - 400, 400, 800, this);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };

    Scanner scanner = new Scanner(new File("words.txt"));

    while (scanner.hasNextLine())
      words.add(scanner.nextLine());
    scanner.close();

    answer = words.get((int) (Math.random() * words.size() + 1) + 1);

    panel.setLayout(new GridBagLayout());
    setContentPane(panel);
    gbc = new GridBagConstraints();

    font = new Font("Calibri", Font.BOLD, 20);

    prompt = new JLabel("Guess a 5 letter word.");
    prompt.setFont(font);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.insets = new Insets(10, 10, 10, 10);
    panel.add(prompt, gbc);

    input = new JTextField(5);
    input.setText("");
    input.setEditable(true);
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    panel.add(input, gbc);

    submit = new JButton("Submit");
    gbc.gridx = 1;
    gbc.gridy = 1;
    panel.add(submit, gbc);

    warning = new JLabel("");
    warning.setFont(font);
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    panel.add(warning, gbc);

    ActionListener submitAction = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        submitHandler();
      }
    };

    submit.addActionListener(submitAction);
    input.addActionListener(submitAction);
  }

  private void submitHandler() {
    String response = input.getText();
    input.setText("");
    warning.setText("");

    if (response.length() != 5)
      warning.setText("Answer must be 5 characters long!");
    else if (!words.contains(response))
      warning.setText("Invalid Word!");
    else {
      String content = "<html>";
      String temp = answer;

      for (int i = 0; i < response.length(); i++) {
        char character = response.charAt(i);

        if (character == temp.charAt(i))
          content += ("<font color='#00ff00'>" + character + "</font>");
        else if (temp.indexOf(character) != -1)
          content += ("<font color='#ffff00'>" + character + "</font>");
        else
          content += ("<font color='#cacaca'>" + character + "</font>");

        temp = temp.replace(character, '0');
      }

      JLabel list = new JLabel(content + "</html>");
      list.setFont(font);
      gbc.gridx = 0;
      gbc.gridy = position++;
      gbc.gridwidth = 2;
      panel.add(list, gbc);
      panel.revalidate();
      panel.repaint();

      guesses++;

      if (response.equals(answer)) {
        input.setEnabled(false);
        submit.setEnabled(false);

        warning.setText("Word guessed correctly in " + guesses + " guesses!");

        return;
      }

      if (guesses > 5) {
        input.setEnabled(false);
        submit.setEnabled(false);

        warning.setText("Wordle Failed! Correct word: " + answer);
      }
    }
  }
}