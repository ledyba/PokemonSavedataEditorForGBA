package PokemonSaveDataEditorForGBA;

import java.awt.*;
import java.awt.Frame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import PokemonSaveDataEditorForGBA.data.*;
import PokemonSaveDataEditorForGBA.data.RW.SaveWrite;
import PokemonSaveDataEditorForGBA.data.RW.FileWrite;
import java.io.File;

/**
 * <p>�^�C�g��: �|�P�����@�Z�[�u�f�[�^�G�f�B�^</p>
 *
 * <p>����: </p>
 *
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 *
 * <p>��Ж�: </p>
 *
 * @author ������
 * @version 1.0
 */
public class SaveCountChangeDialog
    extends JDialog {
  public static final Image WinIcon = PokemonSaveDataEditorForGBA.Frame.WinIcon;
  Save SelectedSave;
  PokemonSaveDataEditorForGBA.Frame Frame;
  boolean isSaveMode;
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JPanel jPanel2 = new JPanel();
  JButton OK_Button = new JButton();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JButton Cancel_Button = new JButton();
  JPanel jPanel3 = new JPanel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JTextField SaveCountField = new JTextField();
  JLabel jLabel2 = new JLabel();
  public SaveCountChangeDialog(PokemonSaveDataEditorForGBA.Frame owner, String title, boolean modal,
                               Save selectedSave,
                               boolean isSaveMode) {
    super(owner, title, modal);
    try {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      this.SelectedSave = selectedSave;
      this.Frame = owner;
      this.isSaveMode = isSaveMode;
      jbInit();
      pack();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public SaveCountChangeDialog(Save selectedSave,
                               PokemonSaveDataEditorForGBA.Frame owner,
                               boolean isSaveMode) {
    //�e�t���[����ݒ肷��ƁC�e�̃A�C�R���ɂȂ邼�I
    this(owner, "�񐔂̐ݒ�", false, selectedSave, isSaveMode);
  }
  public void setVisible(boolean flag){
    if(this.SelectedSave instanceof EndSave){
      this.save();
    }else{
      super.setVisible(flag);
    }
  }
  private void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    jPanel1.setLayout(gridBagLayout1);
    jLabel1.setText("�Z�[�u�񐔂͉���ɂ��܂����H");
    OK_Button.setPreferredSize(new Dimension(67, 25));
    OK_Button.setText("�ݒ�");
    OK_Button.addActionListener(new
                                SaveCountChangeDialog_OK_Button_actionAdapter(this));
    jPanel2.setLayout(gridBagLayout2);
    Cancel_Button.setText("��߂�");
    Cancel_Button.addActionListener(new
                                    SaveCountChangeDialog_Cancel_Button_actionAdapter(this));
    jPanel3.setLayout(gridBagLayout3);
    SaveCountField.setText(String.valueOf(SelectedSave.getSaveCount()));
    SaveCountField.addMouseListener(new psi.lib.Swing.PopupRightClick(SaveCountField));
    jLabel2.setText("��");
    getContentPane().add(panel1);
    panel1.add(jPanel1, java.awt.BorderLayout.CENTER);
    jPanel1.add(jPanel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(jLabel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jPanel3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(SaveCountField, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(OK_Button, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.HORIZONTAL,
                                                  new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(Cancel_Button, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(5, 5, 5, 5), 0, 0));
  }

  public void OK_Button_actionPerformed(ActionEvent e) {
    if (isSaveMode) { //�ʃZ�[�u�f�[�^�̕ۑ��܂ōs���Ƃ�
      long oldCount = SelectedSave.getSaveCount();
      SelectedSave.setSaveCount(Long.parseLong(this.SaveCountField.getText()));
      this.save();
      SelectedSave.setSaveCount(oldCount); //���ɖ߂�
    }
    else {
      //�ݒ�
      SelectedSave.setSaveCount(Long.parseLong(this.SaveCountField.getText()));
//      Frame.dataChanged();
    }
    Frame.rewrite();
    this.setVisible(false);

  }

  public void save() {
    JFileChooser save_chooser = new JFileChooser(new File(Frame.FileName).getPath());
    int selected = save_chooser.showSaveDialog(this);
    if (selected == JFileChooser.APPROVE_OPTION) { //�u�ۑ��v��������Ă�����
      String tmpFileName = save_chooser.getSelectedFile().getPath(); //�������݃t�@�C�����̎擾
      File File = new File(tmpFileName);
      if (File.canRead()) { //�t�@�C�����ǂݍ��߂遁���݂���Ȃ�C�㏑���ɂȂ��Ă��܂�
        SaveDlg SaveDlg = new SaveDlg(SelectedSave, tmpFileName, Frame,"�㏑�����Ă��܂��܂��D��낵���ł����H");
      }
      else { //�łȂ���Ε��ʂɏ�������
        FileWrite FW = new FileWrite(tmpFileName, SelectedSave);
        FW.write();
      }
    }
  }

  public void Cancel_Button_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }
}

class SaveCountChangeDialog_Cancel_Button_actionAdapter
    implements ActionListener {
  private SaveCountChangeDialog adaptee;
  SaveCountChangeDialog_Cancel_Button_actionAdapter(SaveCountChangeDialog
      adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.Cancel_Button_actionPerformed(e);
  }
}

class SaveCountChangeDialog_OK_Button_actionAdapter
    implements ActionListener {
  private SaveCountChangeDialog adaptee;
  SaveCountChangeDialog_OK_Button_actionAdapter(SaveCountChangeDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.OK_Button_actionPerformed(e);
  }
}
