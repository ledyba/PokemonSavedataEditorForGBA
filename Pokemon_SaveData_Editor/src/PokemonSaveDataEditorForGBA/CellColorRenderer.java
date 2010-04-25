package PokemonSaveDataEditorForGBA;

import java.awt.Color;
import javax.swing.JTable;

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
public class CellColorRenderer {
  public static final Color DefaultSelectedBaclGround = (new JTable()).
      getSelectionBackground();
  public static final Color DefaultBackGround = (new JTable()).getBackground();

  public static Color getBackColor(PokemonSaveDataEditorForGBA.Frame Frame,int Row, int Column, boolean isSelected) {
    return Frame.SelectedBlock.getBackColor(Row,Column,isSelected);
  }
}
