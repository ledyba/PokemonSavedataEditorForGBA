package PokemonSaveDataEditorForGBA;

import java.awt.event.*;
import javax.swing.*;

import PokemonSaveDataEditorForGBA.data.*;
import java.awt.Point;
import java.awt.Dimension;

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
public class PopupMouseListener
    implements MouseListener, ActionListener {
  PokemonSaveDataEditorForGBA.Frame Frame;
  JTree Tree;
  JPopupMenu popup;
  Save selectedSave;
  JMenuItem ChangeSaveCountMenu;
  JMenuItem SaveOneSaveMenu;
  public static final String Actin_ChangeSave = "Change save count.";
  public static final String Actin_SaveSave = "Save one save data to a file.";
  public PopupMouseListener(PokemonSaveDataEditorForGBA.Frame frame) {
    Frame = frame;
    popup = new JPopupMenu("���j���[");
    ChangeSaveCountMenu = new JMenuItem("�Z�[�u�񐔂̕ύX");
    ChangeSaveCountMenu.setActionCommand(this.Actin_ChangeSave);
    ChangeSaveCountMenu.addActionListener(this);
    popup.add(ChangeSaveCountMenu);

    SaveOneSaveMenu = new JMenuItem("���̃Z�[�u������̃t�@�C���ɕۑ�");
    SaveOneSaveMenu.setActionCommand(this.Actin_SaveSave);
    SaveOneSaveMenu.addActionListener(this);
    popup.add(SaveOneSaveMenu);
  }

  /**
   * Invoked when the mouse button has been clicked (pressed and released) on a
   * component.
   *
   * @param e MouseEvent
   * @todo ���� java.awt.event.MouseListener ���\�b�h������
   */
  public void mouseClicked(MouseEvent e) {
    Tree = (JTree) (e.getSource());
    //�E�N���b�N�̎������̘b
    if (SwingUtilities.isRightMouseButton(e)) {
      //�E�N���b�N�ł��I���ł���悤�ɂ���
      int selRow = Tree.getRowForLocation(e.getX(), e.getY());
      if (selRow != -1) {
        Tree.setSelectionRow(selRow);
      }
      //�Z�[�u�f�[�^��������̘b
      Object selectedObject = ((CustomizedTreeNode)(Tree.getLastSelectedPathComponent())).getUserObject();
      if (selectedObject != null && selectedObject instanceof Save) {
        selectedSave = (Save) selectedObject;
        if(selectedObject instanceof EndSave){
          ChangeSaveCountMenu.setEnabled(false);
        }else{
          ChangeSaveCountMenu.setEnabled(true);
        }
        popup.show(e.getComponent(), e.getX(), e.getY());
    }
    }
  }

  /**
   * Invoked when the mouse enters a component.
   *
   * @param e MouseEvent
   * @todo ���� java.awt.event.MouseListener ���\�b�h������
   */
  public void mouseEntered(MouseEvent e) {
  }

  /**
   * Invoked when the mouse exits a component.
   *
   * @param e MouseEvent
   * @todo ���� java.awt.event.MouseListener ���\�b�h������
   */
  public void mouseExited(MouseEvent e) {
  }

  /**
   * Invoked when a mouse button has been pressed on a component.
   *
   * @param e MouseEvent
   * @todo ���� java.awt.event.MouseListener ���\�b�h������
   */
  public void mousePressed(MouseEvent e) {
  }

  /**
   * Invoked when a mouse button has been released on a component.
   *
   * @param e MouseEvent
   * @todo ���� java.awt.event.MouseListener ���\�b�h������
   */
  public void mouseReleased(MouseEvent e) {
  }

  /**
   * ��������PopupMenu
   * @param e ActionEvent
   * @todo ���� java.awt.event.ActionListener ���\�b�h������
   */
  public void actionPerformed(ActionEvent e) {
    String ActionCommand = e.getActionCommand();
    if(Frame.isFileOpen){
      if (ActionCommand.equals(this.Actin_ChangeSave)) { //�Z�[�u�񐔂̕ύX
        showChangeDialog(false);
      }
      else if (ActionCommand.equals(this.Actin_SaveSave)) { //�Z�[�u�̌X�̃t�@�C���ւ̕ۑ�
        showChangeDialog(true);
      }
    }else{
      Frame.setStatus("���̑O�Ƀt�@�C�����J���ĉ������I");
    }

  }
  public void showChangeDialog(boolean isSaveMode){
    SaveCountChangeDialog dlg = new SaveCountChangeDialog(this.selectedSave, this.Frame, isSaveMode);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = Frame.getSize();
    Point loc = Frame.getLocation();
    dlg.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                    (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.pack();
    dlg.setVisible(true);
  }

}
