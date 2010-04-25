package PokemonSaveDataEditorForGBA;

import java.awt.*;
import java.awt.Frame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import PokemonSaveDataEditorForGBA.data.SaveFileData;
import PokemonSaveDataEditorForGBA.data.cheat.Cheat;

/**
 * <p>�^�C�g��: �|�P�����Z�[�u�f�[�^�G�f�B�^ for GBA</p>
 *
 * <p>����: </p>
 *
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 *
 * <p>��Ж�: �Ձi�v�T�C�j�̋����֐S���</p>
 *
 * @author PSI
 * @version 1.0
 */
public class CheatDialog
    extends JDialog {
  public static final String ERROR_MSG = ";�G���[���������Ă��܂�\n";
  JPanel Panel = new JPanel();
  JScrollPane ChatScroll = new JScrollPane();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel ButtonPanel = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton CancelButton = new JButton();
  JButton OKButton = new JButton();
  PokemonSaveDataEditorForGBA.Frame owner;
  SaveFileData Data;
  JTextArea CheatCodeArea = new JTextArea();
  public CheatDialog(PokemonSaveDataEditorForGBA.Frame owner, String title, boolean modal, SaveFileData data) {
    super(owner, title, modal);
    this.owner = owner;
    this.Data = data;
    try {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public CheatDialog(SaveFileData data) {
    this((PokemonSaveDataEditorForGBA.Frame)new Frame(), "�`�[�g�R�[�h�̓���", false,data );
  }

  public CheatDialog(PokemonSaveDataEditorForGBA.Frame owner,SaveFileData data) {
    this(owner, "�`�[�g�R�[�h�̓���", false, data);
  }

  private void jbInit() throws Exception {
    Panel.setLayout(borderLayout1);
    this.setTitle("�`�[�g�R�[�h�̓���");
    ButtonPanel.setLayout(gridBagLayout1);
    CancelButton.setText("Cancel");
    CancelButton.addActionListener(new CheatDialog_CancelButton_actionAdapter(this));
    OKButton.setPreferredSize(new Dimension(67, 25));
    OKButton.setText("OK");
    OKButton.addActionListener(new CheatDialog_OKButton_actionAdapter(this));
    CheatCodeArea.setText(";�`�[�g�R�[�h����͂��Ă�������");
    CheatCodeArea.addMouseListener(new psi.lib.Swing.PopupRightClick(CheatCodeArea));
    getContentPane().add(Panel);
    Panel.add(ChatScroll, java.awt.BorderLayout.CENTER);
    ChatScroll.getViewport().add(CheatCodeArea);
    Panel.add(ButtonPanel, java.awt.BorderLayout.SOUTH);
    ButtonPanel.add(CancelButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    ButtonPanel.add(OKButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
  }
  public void setVisible(boolean flag){
    if(flag){
      this.setPreferredSize(new Dimension(400,300));
      this.setSize(this.getPreferredSize());
      Dimension dlgSize = this.getPreferredSize();
      Dimension frmSize = owner.getSize();
//      System.out.println(dlgSize.toString());
//      System.out.println(frmSize.toString());
      Point loc = owner.getLocation();
      this.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                       (frmSize.height - dlgSize.height) / 2 + loc.y);
      this.setModal(true);
      super.setVisible(true);
    }else{
      super.setVisible(false);
    }
  }
  /**
   * Cancel�{�^���������ꂽ�Ƃ�
   * @param e ActionEvent
   */
  public void CancelButton_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }
  /**
   * OK�{�^���������ꂽ�Ƃ�
   * @param e ActionEvent
   */
  public void OKButton_actionPerformed(ActionEvent e) {
    String input = this.CheatCodeArea.getText();
    Cheat cheat = new Cheat(input,this.Data);
    if(cheat.check()){
      cheat.cheat();
//      this.owner.dataChanged();
      this.owner.setStatus("�`�[�g���������܂���");
      this.setVisible(false);
    }else{
      String msg = cheat.toString();
      if(msg.startsWith(this.ERROR_MSG)){
        this.CheatCodeArea.setText(cheat.toString());
      }else{
        this.CheatCodeArea.setText(this.ERROR_MSG+cheat.toString());
      }
    }
  }
}

class CheatDialog_OKButton_actionAdapter
    implements ActionListener {
  private CheatDialog adaptee;
  CheatDialog_OKButton_actionAdapter(CheatDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.OKButton_actionPerformed(e);
  }
}

class CheatDialog_CancelButton_actionAdapter
    implements ActionListener {
  private CheatDialog adaptee;
  CheatDialog_CancelButton_actionAdapter(CheatDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.CancelButton_actionPerformed(e);
  }
}
