package PokemonSaveDataEditorForGBA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import PokemonSaveDataEditorForGBA.data.*;
import PokemonSaveDataEditorForGBA.data.RW.*;


/**
 * <p>タイトル: ポケモン　セーブデータエディタ</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2005 PSI</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */

public class SaveDlg extends JDialog {
  boolean isSaveDataMode;
  JPanel MainPanel = new JPanel();
  JLabel canWrite_Label = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel Button_Panel = new JPanel();
  JButton NO_Button = new JButton();
  JButton YES_Button = new JButton();
  GridLayout gridLayout1 = new GridLayout();
  Object data;
  String Filename;
  String Text="上書きしてしまいます．よろしいですか？";
  PokemonSaveDataEditorForGBA.Frame Owner;

  public SaveDlg(Object data,String Filename,PokemonSaveDataEditorForGBA.Frame owner,String text) {
    super(owner, "警告！", true);
    this.Text = text;
    this.data = data;
    this.Filename = Filename;
    this.Owner = owner;
    try {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      jbInit();
      pack();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    canWrite_Label.setText(Text);
    MainPanel.setLayout(gridBagLayout1);
    NO_Button.setText("いいえ");
    NO_Button.addActionListener(new SaveDlg_NO_Button_actionAdapter(this));
    YES_Button.setText("はい");
    YES_Button.addActionListener(new SaveDlg_YES_Button_actionAdapter(this));
    Button_Panel.setLayout(gridLayout1);
    this.getContentPane().add(MainPanel, BorderLayout.CENTER);
    MainPanel.add(canWrite_Label,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    MainPanel.add(Button_Panel,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    Button_Panel.add(YES_Button, null);
    Button_Panel.add(NO_Button, null);
  }
  public void setVisible(boolean flag){
    if(flag){
      Dimension dlgSize = this.getPreferredSize();
      Dimension frmSize = Owner.getSize();
//      System.out.println(dlgSize.toString());
//      System.out.println(frmSize.toString());
      Point loc = Owner.getLocation();
      this.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                       (frmSize.height - dlgSize.height) / 2 + loc.y);
      this.setModal(true);
      super.setVisible(true);
    }else{
      super.setVisible(false);
    }
  }
  void NO_Button_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }

  void YES_Button_actionPerformed(ActionEvent e) {
    FileWrite write = new FileWrite(Filename,data);
    write.write();
    Owner.setFileName(this.Filename);
    Owner.rewrite();
    this.setVisible(false);
  }
}

class SaveDlg_NO_Button_actionAdapter implements java.awt.event.ActionListener {
  SaveDlg adaptee;

  SaveDlg_NO_Button_actionAdapter(SaveDlg adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.NO_Button_actionPerformed(e);
  }
}

class SaveDlg_YES_Button_actionAdapter implements java.awt.event.ActionListener {
  SaveDlg adaptee;

  SaveDlg_YES_Button_actionAdapter(SaveDlg adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.YES_Button_actionPerformed(e);
  }
}
