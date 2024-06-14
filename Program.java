import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.FileNotFoundException;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Program extends JPanel {
  private static ArrayList<Integer[]> clicks = new ArrayList<Integer[]>();

  public void paint(Graphics g) {
    g.setColor(Color.black);
    g.fillRect(0, 0, 1080, 720);

    g.setColor(Color.white);
    g.setFont(new Font("Calibri", Font.BOLD, 48));
    g.drawString("Retro Gaming Console", 300, 50);

    g.setFont(new Font("Calibri", Font.BOLD, 24));
    g.drawString("By: Ritwik Singh, Michael Chang", 300, 100);

    g.setColor(Color.red);

    g.drawRect(50, 150, 450, 500);
    g.drawRect(550, 150, 450, 500);

    g.setColor(Color.green);
    g.setFont(new Font("Calibri", Font.BOLD, 18));

    g.drawString("Number Guessing", 180, 180);
    g.drawString("Temu Wordle", 730, 180);

    g.setColor(Color.white);
    g.setFont(new Font("Calibri", Font.BOLD, 14));

    g.drawString("Step into the exciting world of our Number Guessing Game,", 60, 220);
    g.drawString("a fun and challenging activity designed to test your intuition", 60, 240);
    g.drawString("and logic. Perfect for all ages, this game will keep you", 60, 260);
    g.drawString("entertained as you try to guess the secret number within", 60, 280);
    g.drawString("a specified range.", 60, 300);
    g.drawString("Difficulty: 5/10", 180, 450);
    g.drawString("Recommended For: Everyone", 180, 470);

    g.drawString("Wordle is a very simple game. Players have six attempts to", 560, 220);
    g.drawString("guess a five-letter word, with feedback given for each guess", 560, 240);
    g.drawString("in the form of colored tiles indicating when letters match or", 560, 260);
    g.drawString("occupy the correct position.", 560, 280);
    g.drawString("Difficulty: 10/10", 730, 450);
    g.drawString("Recommended For: Scholars with a 1600 on the SAT", 600, 470);

    for (Integer[] point : clicks) {
      if (point[0] >= 50 && point[0] <= 500 && point[1] >= 150 && point[1] <= 650) {
        NumberGuessing game = new NumberGuessing();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setSize(1080, 720);
        game.setVisible(true);
      } else if (point[0] >= 550 && point[0] <= 1000 && point[1] >= 150 && point[1] <= 650) {
        Wordle game;

        try {
          game = new Wordle();
          game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          game.setSize(1080, 720);
          game.setVisible(true);
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }

      }

      clicks = new ArrayList<Integer[]>();
    }
  }

  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        JFrame win = new JFrame("Retro Gaming Console");
        win.setSize(1080, 720);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Program canvas = new Program();
        win.add(canvas);
        win.setVisible(true);

        win.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent me) {
            clicks.add(new Integer[] { me.getX(), me.getY() });
            win.repaint();
          }
        });

        win.addMouseMotionListener(new MouseAdapter() {
          public void mouseMoved(MouseEvent me) {
            win.repaint();
          }
        });
      }
    });
  }
}
