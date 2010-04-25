package PokemonSaveDataEditorForGBA;

import java.awt.Color;
import javax.swing.JTable;

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
public class CellColorRenderer {
  public static final Color DefaultSelectedBaclGround = (new JTable()).
      getSelectionBackground();
  public static final Color DefaultBackGround = (new JTable()).getBackground();

  public static Color getBackColor(PokemonSaveDataEditorForGBA.Frame Frame,int Row, int Column, boolean isSelected) {
    return Frame.SelectedBlock.getBackColor(Row,Column,isSelected);
  }
}
