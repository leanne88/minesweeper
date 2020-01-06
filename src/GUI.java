import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame {

  int spacing = 5;
  int mx = -100;
  int my = -100;

  Random rand = new Random();

  int neighbour = 0;

  int[][] mines = new int[16][9];
  int[][] neighbours = new int[16][9];
  boolean[][] revealed = new boolean[16][9];
  boolean[][] flagged = new boolean[16][9];

  public GUI() {
    this.setTitle("Minesweeper");
    this.setSize(1280, 800);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
    this.setResizable(false);

    for(int i=0; i<16; i++){
      for(int j=0; j<9; j++){
        if(rand.nextInt(100) < 20) {
          mines[i][j] = 1;
        } else {
          mines[i][j] = 0;
        }
        revealed[i][j] = false;
      }
    }

    for(int i=0; i<16; i++){
      for(int j=0; j<9; j++){
        neighbour = 0;
        for(int m=0; m<16; m++){
          for(int n=0; n<9; n++){
            if(!(m==i && n==j)) {
              if (isNeighbour(i, j, m, n) == true) {
                neighbour++;
              }
            }
          }
        }
        neighbours[i][j] = neighbour;
      }
    }

    Board board = new Board();
    this.setContentPane(board);
    Move move = new Move();
    this.addMouseMotionListener(move);
    Click click = new Click();
    this.addMouseListener(click);
  }

  public class Board extends JPanel {
    @Override
    public void paintComponent(Graphics g) {
      g.setColor(Color.DARK_GRAY);
      g.fillRect(0, 0, 1280, 800);
      for(int i=0; i<16; i++){
        for(int j=0; j<9; j++){
          g.setColor(Color.gray);
          if(revealed[i][j] == true) {
            if(mines[i][j] == 1){
              g.setColor(Color.RED);
            } else {
              g.setColor(Color.white);
            }
          }
          if(mx >= spacing+i*80 && mx < spacing+i*80+80-2*spacing && my >= spacing+j*80+80 && my < spacing+j*80+80+80-2*spacing){
            g.setColor(Color.lightGray);
          }
          g.fillRect(spacing+i*80,spacing+j*80+80-26,80-2*spacing, 80-2*spacing);
          if(revealed[i][j] == true) {
            g.setColor(Color.black);
            if(mines[i][j] == 1) {
              g.fillRect(i*80+30, j*80+80-6, 20, 40);
              g.fillRect(i*80+20, j*80+80+4, 40, 20);
              g.fillRect(i*80+25, j*80+80-26+25, 30, 30);
            } else {
              g.setFont(new Font("Ariel", Font.BOLD, 40));
              g.drawString(Integer.toString(neighbours[i][j]),i*80+27, j*80+80+27);
            }
          }
        }
      }
    }
  }

  public class Move implements MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
      mx = e.getX();
      my = e.getY();
      repaint();
    }
  }

  public class Click implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
      if(inboxX() != -1 && inboxY() != -1){
        revealed[inboxX()][inboxY()] = true;
      }
      repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
  }

  public int inboxX() {
    for(int i=0; i<16; i++){
      for(int j=0; j<9; j++) {
        if (mx >= spacing + i * 80 && mx < spacing + i * 80 + 80 - 2 * spacing
            && my >= spacing + j * 80 + 80 && my < spacing + j * 80 + 80 + 80 - 2 * spacing) {
          return i;
        }
      }
    }
    return -1;
  }

  public int inboxY() {
    for(int i=0; i<16; i++){
      for(int j=0; j<9; j++) {
        if (mx >= spacing + i * 80 && mx < spacing + i * 80 + 80 - 2 * spacing
            && my >= spacing + j * 80 + 80 && my < spacing + j * 80 + 80 + 80 - 2 * spacing) {
          return j;
        }
      }
    }
    return -1;
  }

  public boolean isNeighbour(int mX, int mY, int nX, int nY) {
    if(mX - nX < 2 && mX -nX > -2 && mY - nY < 2 && mY - nY > -2 && mines[nX][nY] == 1){
      return true;
    }
    return false;
  }

}
