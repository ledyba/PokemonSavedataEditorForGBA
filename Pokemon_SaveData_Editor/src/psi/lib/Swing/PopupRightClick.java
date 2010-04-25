package psi.lib.Swing;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 * <p>タイトル: ポケモンセーブデータエディタ for GBA</p>
 *
 * <p>説明: </p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author PSI
 * @version 1.0
 */
public class PopupRightClick
    implements MouseListener, ActionListener {
  JPopupMenu popup;
  JMenuItem CutMenu;
  JMenuItem CopyMenu;
  JMenuItem PasteMenu;
  JTextComponent Owner;
  public static final String Actin_Cut = "CUT";
  public static final String Actin_Copy = "COPY";
  public static final String Actin_Paste = "PASTE";
  public PopupRightClick(JTextComponent owner) {
    this.Owner = owner;
    popup = new JPopupMenu("メニュー");
    CopyMenu = new JMenuItem("コピー(CTRL + C)");
    CopyMenu.setActionCommand(this.Actin_Copy);
    CopyMenu.addActionListener(this);
    popup.add(CopyMenu);

    CutMenu = new JMenuItem("切り取り(CTRL + X)");
    CutMenu.setActionCommand(this.Actin_Cut);
    CutMenu.addActionListener(this);
    popup.add(CutMenu);

    PasteMenu = new JMenuItem("貼り付け(CTRL + V)");
    PasteMenu.setActionCommand(this.Actin_Paste);
    PasteMenu.addActionListener(this);
    popup.add(PasteMenu);
  }

  /**
   * Invoked when the mouse button has been clicked (pressed and released) on a
   * component.
   *
   * @param e MouseEvent
   * @todo この java.awt.event.MouseListener メソッドを実装
   */
  public void mouseClicked(MouseEvent e) {
    //右クリックの時だけの話
    if (SwingUtilities.isRightMouseButton(e)) {
      popup.show(e.getComponent(), e.getX(), e.getY());
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
    if (ActionCommand.equals(this.Actin_Cut)) { //カット
      Owner.cut();
    }
    else if (ActionCommand.equals(this.Actin_Copy)) { //コピー
      Owner.copy();
    }
    else if (ActionCommand.equals(this.Actin_Paste)) { //貼り付け
      Owner.paste();
    }
  }
}
