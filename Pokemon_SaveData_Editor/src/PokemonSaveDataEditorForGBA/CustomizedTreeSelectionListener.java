package PokemonSaveDataEditorForGBA;

import javax.swing.event.*;
import javax.swing.tree.*;
import PokemonSaveDataEditorForGBA.data.*;

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
public class CustomizedTreeSelectionListener
    implements TreeSelectionListener {
  PokemonSaveDataEditorForGBA.Frame Frame;
  public CustomizedTreeSelectionListener(PokemonSaveDataEditorForGBA.Frame Frame) {
    this.Frame = Frame;
  }

  /**
   * Called whenever the value of the selection changes.
   *
   * @param e the event that characterizes the change.
   * @todo この javax.swing.event.TreeSelectionListener メソッドを実装
   */
  public void valueChanged(TreeSelectionEvent e) {
    CustomizedTreeNode node = ( CustomizedTreeNode )Frame.BlockTree.getLastSelectedPathComponent();
    if(node != null){
      Object obj = node.getUserObject();
      if (obj instanceof BlockData) {
        Frame.setSelectedBlock( (BlockData) obj);
      }
    }
  }
}
