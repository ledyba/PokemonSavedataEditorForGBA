package PokemonSaveDataEditorForGBA.data;

import PokemonSaveDataEditorForGBA.CellColorRenderer;
import java.awt.Color;

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
public class EndBlankDataBlock
    extends BlockData {
  public static int BLOCK_LENGTH = 0x1000;
  EndBlankDataBlock(Save parent) {
    super(parent);
  }

  void init() {
    Data = new byte[BLOCK_LENGTH]; //実際のデータ
  }

  public int getLength() {
    return this.BLOCK_LENGTH;
  }

  /**
   * チェックサムを返す
   * @return String
   */
  public String getChecksum() {
    return "****";
  }

  /**
   * セーブカウントをHexで返します
   * @return String
   */
  public String getSaveCountHex() {
    return "****";
  }

  /**
   * セーブカウントを返します
   * @return int
   */
  public long getSaveCount() {
    return -1;
  }

  /**
   * セーブカウントを設定します
   * @param SaveCount int
   */
  public void setSaveCount(int SaveCount) {
  }

  public int getBlockNumber() {
    return 2;
  }

  public String toString() {
    return "2:ブランクデータ";
  }

  public void calcChecksum() { //何もしない
  }

  public String getCellText(int rowIndex, int columnIndex) {
    //フッタも何も無い
    return null;
  }

  public Color getBackColor(int Row, int Column, boolean isSelected) {
    //何もしない．
    if (isSelected) { //選択されている
      return CellColorRenderer.DefaultSelectedBaclGround;
    }
    else { //選択されていないなら
      return CellColorRenderer.DefaultBackGround;
    }
  }
}
