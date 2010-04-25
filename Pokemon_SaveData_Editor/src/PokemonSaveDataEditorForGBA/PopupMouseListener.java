package PokemonSaveDataEditorForGBA;

import java.awt.event.*;
import javax.swing.*;

import PokemonSaveDataEditorForGBA.data.*;
import java.awt.Point;
import java.awt.Dimension;

/**
 * <p>タイトル: ポケモン　セーブデータエディタ</p>
 *
 * <p>説明: </p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: </p>
 *
 * @author 未入力
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
    popup = new JPopupMenu("メニュー");
    ChangeSaveCountMenu = new JMenuItem("セーブ回数の変更");
    ChangeSaveCountMenu.setActionCommand(this.Actin_ChangeSave);
    ChangeSaveCountMenu.addActionListener(this);
    popup.add(ChangeSaveCountMenu);

    SaveOneSaveMenu = new JMenuItem("このセーブだけ一つのファイルに保存");
    SaveOneSaveMenu.setActionCommand(this.Actin_SaveSave);
    SaveOneSaveMenu.addActionListener(this);
    popup.add(SaveOneSaveMenu);
  }

  /**
   * Invoked when the mouse button has been clicked (pressed and released) on a
   * component.
   *
   * @param e MouseEvent
   * @todo この java.awt.event.MouseListener メソッドを実装
   */
  public void mouseClicked(MouseEvent e) {
    Tree = (JTree) (e.getSource());
    //右クリックの時だけの話
    if (SwingUtilities.isRightMouseButton(e)) {
      //右クリックでも選択できるようにする
      int selRow = Tree.getRowForLocation(e.getX(), e.getY());
      if (selRow != -1) {
        Tree.setSelectionRow(selRow);
      }
      //セーブデータだったらの話
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
   * @todo この java.awt.event.MouseListener メソッドを実装
   */
  public void mouseEntered(MouseEvent e) {
  }

  /**
   * Invoked when the mouse exits a component.
   *
   * @param e MouseEvent
   * @todo この java.awt.event.MouseListener メソッドを実装
   */
  public void mouseExited(MouseEvent e) {
  }

  /**
   * Invoked when a mouse button has been pressed on a component.
   *
   * @param e MouseEvent
   * @todo この java.awt.event.MouseListener メソッドを実装
   */
  public void mousePressed(MouseEvent e) {
  }

  /**
   * Invoked when a mouse button has been released on a component.
   *
   * @param e MouseEvent
   * @todo この java.awt.event.MouseListener メソッドを実装
   */
  public void mouseReleased(MouseEvent e) {
  }

  /**
   * こっちはPopupMenu
   * @param e ActionEvent
   * @todo この java.awt.event.ActionListener メソッドを実装
   */
  public void actionPerformed(ActionEvent e) {
    String ActionCommand = e.getActionCommand();
    if(Frame.isFileOpen){
      if (ActionCommand.equals(this.Actin_ChangeSave)) { //セーブ回数の変更
        showChangeDialog(false);
      }
      else if (ActionCommand.equals(this.Actin_SaveSave)) { //セーブの個々のファイルへの保存
        showChangeDialog(true);
      }
    }else{
      Frame.setStatus("その前にファイルを開いて下さい！");
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
