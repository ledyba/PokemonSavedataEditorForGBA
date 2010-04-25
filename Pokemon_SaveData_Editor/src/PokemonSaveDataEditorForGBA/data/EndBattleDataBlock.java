package PokemonSaveDataEditorForGBA.data;

import java.io.ByteArrayInputStream;
import PokemonSaveDataEditorForGBA.CellColorRenderer;
import java.awt.Color;
import psi.lib.CalTools.PokeTools;
import psi.lib.CalTools.CalcLittleEndian;

/**
 * <p>タイトル: ポケモンセーブデータエディタ for GBA</p>
 *
 * <p>説明: バトルデータ</p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author PSI
 * @version 1.0
 */
public class EndBattleDataBlock
    extends BlockData {
  public static int BLOCK_LENGTH = 0x1000;
  EndBattleDataBlock(Save parent) {
    super(parent);
  }

  void init() {
    Data = new byte[this.getLength()];
  }

  public int getLength() {
    return this.BLOCK_LENGTH;
  }

  /**
   * チェックサムを返す
   * @return String
   */
  public String getChecksum() {
    //f80 から f83がチェックサム．
    return
     PokeTools.space(PokeTools.toHex(this.getByte(0xf83)), 2, "0") +
     PokeTools.space(PokeTools.toHex(this.getByte(0xf82)), 2, "0") +
     PokeTools.space(PokeTools.toHex(this.getByte(0xf81)), 2, "0") +
     PokeTools.space(PokeTools.toHex(this.getByte(0xf80)), 2, "0")
     ;
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

  /**
   * ブロックナンバーは実際のデータ並び順とします．
   * @return int
   */
  public int getBlockNumber() {
    return 3;
  }

  public String toString() {
    return "3:Emバトルログ関連データ";
  }

  public String getCellText(int rowIndex, int columnIndex) {
    if(rowIndex == 0x0){//先頭は固定
      if (0x00 + 0x2 <= columnIndex && columnIndex <= 0x03 + 0x2) {
              return "0x000 -> 0x003：定数（=0x0000b39d）";
      }
    } else if (rowIndex == 0xf8) { //フッタは編集させない
      if (0x00 + 0x2 <= columnIndex && columnIndex <= 0x03 + 0x2) {
        return "0xf80 -> 0xf83：チェックサム";
      }
    }
    return null;
  }

  public Color getBackColor(int Row, int Column, boolean isSelected) {
    Color tmpColor = null;
    if(Row == 0x0){//先頭は固定
      if (0x00 + 0x2 <= Column && Column <= 0x03 + 0x2) {
          //return "0x000 -> 0x003：定数（=0x0000b39d）";
          tmpColor = Color.lightGray;
      }
    } else if (Row == 0xf8) { //フッタは編集させない
      if (0x00 + 0x2 <= Column && Column <= 0x03 + 0x2) {
          //return "0xf80 -> 0xf83：チェックサム";
          tmpColor = Color.GREEN.brighter();
      }
    }
    if (isSelected) { //選択されている
      if (tmpColor == null) { //フッタでない
        return CellColorRenderer.DefaultSelectedBaclGround;
      }
      else {
        return tmpColor.darker();
      }
    }
    else { //選択されていないなら
      if (tmpColor == null) { //フッタでない
        return CellColorRenderer.DefaultBackGround;
      }
      else {
        return tmpColor;
      }
    }
  }

  public void calcChecksum() { //チェックサム　今のところは計算しない
        byte checksum[] = new byte[4];
        byte[] tmpBuff = new byte[4];
        ByteArrayInputStream bais = new ByteArrayInputStream(Data, 0, 0xf80);
    bais.skip(4);
        while (bais.available() > 0) {
          bais.read(tmpBuff, 0, 1);
          //checksum = CalcLittleEndian.addHalfWord(checksum, PokeTools.toLong(tmpBuff));
          checksum = CalcLittleEndian.addWord(checksum,tmpBuff,4);
        }

        //書き込み　開始はf80から
        for (int i = 0; i < 4; i++) {
            this.setByte((byte)(checksum[i]), 0xf80 + i);
        }
  }
}
