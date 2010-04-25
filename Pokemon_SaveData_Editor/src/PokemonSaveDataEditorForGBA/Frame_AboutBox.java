package PokemonSaveDataEditorForGBA;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * <p>�^�C�g��: �|�P�����@�Z�[�u�f�[�^�G�f�B�^</p>
 * <p>����: </p>
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 * <p>��Ж�: </p>
 * @author ������
 * @version 1.0
 */

public class Frame_AboutBox extends JDialog implements ActionListener {

  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JPanel insetsPanel1 = new JPanel();
  JPanel insetsPanel2 = new JPanel();
  JPanel insetsPanel3 = new JPanel();
  JButton button1 = new JButton();
  JLabel imageLabel = new JLabel();
  JLabel label1 = new JLabel();
  JLabel label2 = new JLabel();
  JLabel label3 = new JLabel();
  JLabel label4 = new JLabel();
  ImageIcon image1 = new ImageIcon();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  GridLayout gridLayout1 = new GridLayout();
  /*
  String product = "\u30dd\u30b1\u30e2\u30f3\u3000\u30bb\u30fc\u30d6\u30c7\u30fc\u30bf\u30a8\u30c7\u30a3\u30bf";
  String version = "1.0";
  String copyright = "Copyright (c) 2005 PSI";
  String comments = "";
  */
  javax.swing.JLabel jLabel1 = new JLabel();
  public Frame_AboutBox(Frame parent) {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  //�R���|�[�l���g�̏�����
  private void jbInit() throws Exception  {
    image1 = new ImageIcon(PokemonSaveDataEditorForGBA.Frame.class.getResource("icon.png"));
    imageLabel.setIcon(image1);
    this.setTitle("�o�[�W�������");
    panel1.setLayout(borderLayout1);
    panel2.setLayout(borderLayout2);
    insetsPanel1.setLayout(flowLayout1);
    insetsPanel2.setLayout(flowLayout1);
    insetsPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    gridLayout1.setRows(4);
    gridLayout1.setColumns(1);
    label1.setText("�|�P�����Z�[�u�f�[�^�G�f�B�^ for GBA");
    label2.setText("1.36(2006/04/10)");
    label3.setText("Copyright (c) 2006 PSI");
    insetsPanel3.setLayout(gridLayout1);
    insetsPanel3.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 10));
    button1.setText("OK");
    button1.addActionListener(this);
    label4.setText("�Ձi�v�T�C�j�̋����֐S���");
    insetsPanel2.add(imageLabel, null);
    panel2.add(insetsPanel2, BorderLayout.WEST);
    this.getContentPane().add(panel1, null);
    insetsPanel3.add(label1, null);
    insetsPanel3.add(label2, null);
    insetsPanel3.add(label3, null);
    insetsPanel3.add(label4, null);
    panel2.add(insetsPanel3, BorderLayout.CENTER);
    insetsPanel1.add(button1, null);
    panel1.add(insetsPanel1, BorderLayout.SOUTH);
    panel1.add(panel2, BorderLayout.NORTH);
    setResizable(true);
  }
  //�E�B���h�E������ꂽ�Ƃ��ɏI������悤�ɃI�[�o�[���C�h
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      cancel();
    }
    super.processWindowEvent(e);
  }
  //�_�C�A���O�����
  void cancel() {
    dispose();
  }
  //�{�^���C�x���g�Ń_�C�A���O�����
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == button1) {
      cancel();
    }
  }
}
