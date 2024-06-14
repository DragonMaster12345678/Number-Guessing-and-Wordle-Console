import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NumberGuessing extends JFrame {
  private JTextField input;
  private JLabel prompt;
  private JLabel answer;
  private JButton submit;
  private int guesses = 0;
  private int number = (int) (Math.random() * 1000) + 1;

  public NumberGuessing() {
    super("Guessing Game");

    // funny snippet that i "borrowed"
    JPanel panel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
          BufferedImage image = ImageIO.read(new FileInputStream("numbers.jpg"));
          g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

          BufferedImage blank = ImageIO.read(new FileInputStream("blank.png"));
          g.drawImage(blank, getWidth() / 2 - 300, getHeight() / 2 - 100, 600, 200, this);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };

    panel.setLayout(new GridBagLayout());
    setContentPane(panel);
    GridBagConstraints gbc = new GridBagConstraints();

    Font font = new Font("Calibri", Font.BOLD, 20);

    prompt = new JLabel("Guess a number between 1 and 1000.");
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

    answer = new JLabel("");
    answer.setFont(font);
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    panel.add(answer, gbc);

    ActionListener submitAction = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        submitHandler();
      }
    };

    submit.addActionListener(submitAction);
    input.addActionListener(submitAction);
  }

  private void submitHandler() {
    answer.setText("");
    int response;

    try {
      response = Integer.parseInt(input.getText());
    } catch (Exception e) {
      answer.setText("Not a number!");
      input.setText("");

      e.printStackTrace();
      return;
    }

    input.setText("");
    guesses++;

    if (response == number) {
      answer.setText("Correct! Succesfully guessed in " + guesses + " guesses.");

      input.setEnabled(false);
      submit.setEnabled(false);
    } else if (response > number)
      answer.setText("The answer is lower.");
    else
      answer.setText("The answer is higher.");
  }
}
