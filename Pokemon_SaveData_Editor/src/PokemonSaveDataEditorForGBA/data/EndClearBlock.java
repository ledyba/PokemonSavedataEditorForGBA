package PokemonSaveDataEditorForGBA.data;

import java.io.ByteArrayInputStream;
import PokemonSaveDataEditorForGBA.CellColorRenderer;
import java.awt.Color;
import psi.lib.CalTools.PokeTools;

/**
 * <p>タイトル: ポケモンセーブデータエディタ for GBA</p>
 *
 * <p>説明: 殿堂入りデータをあらわします．</p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author PSI
 * @version 1.0
 */
public class EndClearBlock
    extends BlockData {
  public static int BLOCK_LENGTH = 0x1000;
//  public static final int START_ADDR[] = {0x1c000,0x1d000};//それぞれの殿堂入りデータの始まるところ
//  public static final int START_ADDR_1 = 0x1c000;//それぞれの殿堂入りデータの始まるところ
//  public static final int START_ADDR_2 = 0x1d000;
  public int BlockNumber;
  EndClearBlock(Save parent, int BlockNumber) {
    super(parent);
    this.BlockNumber = BlockNumber;
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
      //ff4,ff5がチェックサム．
    return PokeTools.space(PokeTools.toHex(this.getByte(0xff5)), 2, "0") +
        PokeTools.space(PokeTools.toHex(this.getByte(0xff4)), 2, "0");
  }
  /**
   * セーブカウントをHexで返します
   * @return String
   */
  public String getSaveCountHex(){
    return "****";
  }
  /**
   * セーブカウントを返します
   * @return int
   */
  public long getSaveCount(){
    return -1;
  }
  /**
   * セーブカウントを設定します
   * @param SaveCount int
   */
  public void setSaveCount(int SaveCount){
  }
  /**
   * ブロックナンバーは実際のデータ並び順とします．
   * @return int
   */
  public int getBlockNumber() {
/*    int num = -1;
    switch (this.getAddr()) {
      case START_ADDR_1:
        num = 1;
        break;
      case START_ADDR_2:
        num = 2;
        break;
    }*/
    return this.BlockNumber;
  }
  public String toString(){
    String num1 = String.valueOf(this.getBlockNumber());
    String num2 = String.valueOf(this.getBlockNumber() + 1);
    return num1+":殿堂入りブロック"+num2;
  }
  public String getCellText(int rowIndex, int columnIndex) {
    if (rowIndex == 0xff) { //フッタは編集させない
      if (0x04 + 0x2 <= columnIndex && columnIndex <= 0x05 + 0x2) {
        return "0xff4 -> 0xff5：チェックサム";
      }else if (0x06 + 0x2 <= columnIndex && columnIndex <= 0x07 + 0x2) {
        return "0xff6 -> 0xff7：定数（=0x0000）";
      }else if (0x08 + 0x2 <= columnIndex && columnIndex <= 0x0b + 0x2) {
        return "0xff8 -> 0xffb：定数(0x08012025)";
      }else if (0x0c + 0x2 <= columnIndex && columnIndex <= 0x0f + 0x2) {
        return "0xffc -> 0xfff：定数（=0x00000000）";
      }
    }
    return null;
  }

  public Color getBackColor(int Row, int Column, boolean isSelected) {
    Color tmpColor = null;
    //フッタは編集させない
    if (Row == 0xff) {
      if (0x04 + 0x2 <= Column && Column <= 0x05 + 0x2) {
        //return "0xff4 -> 0xff5：チェックサム";
        tmpColor = Color.GREEN.brighter();
      }else if (0x06 + 0x2 <= Column && Column <= 0x0f + 0x2) {
        //全部定数
        tmpColor = Color.lightGray;
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

  public void calcChecksum(){//チェックサムあり
    byte checksum[] = new byte[4];
    byte[] tmpBuff = new byte[4];
    ByteArrayInputStream bais = new ByteArrayInputStream(Data, 0, 0xff4);
    while (bais.available() > 0) {
      bais.read(tmpBuff, 0, 4);
      //checksum = CalcLittleEndian.addHalfWord(checksum, PokeTools.toLong(tmpBuff));
      checksum = psi.lib.CalTools.CalcLittleEndian.addWord(checksum,tmpBuff,4);
    }
    //結合
    byte tmpBuff2[] = new byte[2];
    byte writing[] = new byte[2];
    ByteArrayInputStream bais2 = new ByteArrayInputStream(checksum, 0, 4);
    while (bais2.available() > 0) {
      bais2.read(tmpBuff2, 0, 2);
      writing = psi.lib.CalTools.CalcLittleEndian.addWord(writing, tmpBuff2, 2);
    }
    //書き込み　開始はff4から
    for (int i = 0; i < 2; i++) {
        this.setByte(writing[i], 0xff4 + i);
    }
  }
}
