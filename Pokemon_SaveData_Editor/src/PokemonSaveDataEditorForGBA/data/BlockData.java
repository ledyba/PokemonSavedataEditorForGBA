package PokemonSaveDataEditorForGBA.data;

import PokemonSaveDataEditorForGBA.CustomizedTreeNode;
import java.io.ByteArrayInputStream;
import java.awt.Color;
import PokemonSaveDataEditorForGBA.CellColorRenderer;
import psi.lib.CalTools.PokeTools;
import psi.lib.CalTools.CalcLittleEndian;
/**
 * <p>タイトル: ブロックデータ</p>
 *
 * <p>説明: ブロックデータを表現する</p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: </p>
 *
 * @author 未入力
 * @version 1.0
 */
public class BlockData {
  public static int BLOCK_LENGTH = 0x1000;
  byte Data[];
  private int Addr = 0;
  private Save Parent;
  BlockData(Save parent){
    Parent = parent;
    init();
  }
  void init(){
    Data = new byte[BLOCK_LENGTH]; //実際のデータ
  }
  public int getLength(){
    return this.BLOCK_LENGTH;
  }
  public byte getByte(int offset){
    if(offset < this.getLength()){
      return Data[offset];
    }else{
      return 0;
    }
  }
  public int getAddr(){
    return Addr;
  }
  public void setAddr(int addr){
    Addr = addr;
  }
  /**
   * チェックサムを返す
   * @return String
   */
  public String getChecksum(){
    return PokeTools.space(PokeTools.toHex(Data[0xff7]),2,"0") + PokeTools.space(PokeTools.toHex(Data[0xff6]),2,"0");
  }
  /**
   * セーブカウントをHexで返します
   * @return String
   */
  public String getSaveCountHex(){
    return PokeTools.space(PokeTools.toHex(Data[0xffd]),2,"0") + PokeTools.space(PokeTools.toHex(Data[0xffc]),2,"0");
  }
  /**
   * セーブカウントを返します
   * @return int
   */
  public long getSaveCount(){
    return PokeTools.ByteToInt(Data[0xffc]) + (PokeTools.ByteToInt(Data[0xffd]) << 8);
  }
  /**
   * セーブカウントを設定します
   * @param SaveCount int
   */
  public void setSaveCount(long SaveCount){
    Data[0xffd] = (byte)((SaveCount >> 8) & 0xff);
    Data[0xffc] = (byte)(SaveCount & 0xff);
  }
  public void setByte(byte number,int offset){
    if(offset < this.getLength()){
      Data[offset] = number;
    }
  }
  public byte[] getByteArray(){
    return Data;
  }
  public void setByteArray(byte[] data){
    if(data.length == getLength()){
      Data = data;
    }
  }
  public int getBlockNumber() {
    return PokeTools.ByteToInt(Data[0xff4]);
  }
  public Save getParent(){
    return Parent;
  }
  public CustomizedTreeNode getNode(){
    return new CustomizedTreeNode(this);
  }
  public String toString(){
    return "第"+PokeTools.space(Integer.toHexString(this.getBlockNumber()),2,"0")+"ブロック";
  }
  public void calcChecksum(){
    byte checksum[] = new byte[4];
    byte[] tmpBuff = new byte[4];
    ByteArrayInputStream bais = new ByteArrayInputStream(Data, 0, 0xff4);
    while (bais.available() > 0) {
      bais.read(tmpBuff, 0, 4);
      //checksum = CalcLittleEndian.addHalfWord(checksum, PokeTools.toLong(tmpBuff));
      checksum = CalcLittleEndian.addWord(checksum,tmpBuff,4);
    }
    //結合
    byte tmpBuff2[] = new byte[2];
    byte writing[] = new byte[2];
    ByteArrayInputStream bais2 = new ByteArrayInputStream(checksum, 0, 4);
    while (bais2.available() > 0) {
      bais2.read(tmpBuff2, 0, 2);
      writing = CalcLittleEndian.addWord(writing, tmpBuff2, 2);
    }
    //書き込み
    for (int i = 0; i < 2; i++) {
        this.setByte(writing[i], 0xff6 + i);
    }
  }
  public String getCellText(int rowIndex,int columnIndex){
    if (rowIndex >= 0xff) { //フッタは編集させない
      if (columnIndex == 0x04 + 0x2) {
        return "0xff4：ブロックナンバー";
      }
      else if (columnIndex == 0x05 + 0x2) {
        return "0xff5：定数（=0x00）";
      }
      else if (0x06 + 0x2 <= columnIndex && columnIndex <= 0x07 + 0x2) {
        return "0xff6 -> 0xff7：チェックサム";
      }
      else if (0x08 + 0x2 <= columnIndex && columnIndex <= 0x0b + 0x2) {
        return "0xff8 -> 0x00b：定数(0x08012025)";
      }
      else if (0x0c + 0x2 <= columnIndex && columnIndex <= 0x0d + 0x2) {
        return "0xffc -> 0xffd：セーブ回数";
      }
      else if (0x0e + 0x2 <= columnIndex && columnIndex <= 0x0f + 0x2) {
        return "0xffe -> 0xfff：定数(0x0000)";
      }
    }
    return null;
  }
  public Color getBackColor(int Row, int Column, boolean isSelected) {
  Color tmpColor = null;
    //フッタは編集させない
    if (Row >= 0xff) {
      if (Column == 0x04 + 0x2) {
        //0xff4：ブロックナンバー
        tmpColor = Color.cyan;
      }
      else if (Column == 0x05 + 0x2) {
        //0xff5：定数（=0x00）
        tmpColor = Color.lightGray;
      }
      else if (0x06 + 0x2 <= Column && Column <= 0x07 + 0x2) {
        //statusSet("0xff6 -> 0xff7：チェックサム");
        tmpColor = Color.GREEN.brighter();
      }
      else if (0x08 + 0x2 <= Column && Column <= 0x0b + 0x2) {
        //statusSet("0xff8 -> 0x00b：定数(0x08012025)");
        tmpColor = Color.lightGray;
      }
      else if (0x0c + 0x2 <= Column && Column <= 0x0d + 0x2) {
        //statusSet("0xffc -> 0xffd：セーブ回数");
        tmpColor = Color.pink;
      }
      else if (0x0e + 0x2 <= Column && Column <= 0x0f + 0x2) {
        //statusSet("0xffe -> 0xfff：定数(0x0000)");
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
}
