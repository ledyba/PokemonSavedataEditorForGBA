package PokemonSaveDataEditorForGBA;

import javax.swing.event.*;
import javax.swing.tree.*;
import PokemonSaveDataEditorForGBA.data.*;

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
   * @todo ���� javax.swing.event.TreeSelectionListener ���\�b�h������
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
