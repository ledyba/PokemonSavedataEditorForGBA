package PokemonSaveDataEditorForGBA.data;

import PokemonSaveDataEditorForGBA.CustomizedTreeNode;
import java.io.ByteArrayInputStream;
import java.awt.Color;
import PokemonSaveDataEditorForGBA.CellColorRenderer;
import psi.lib.CalTools.PokeTools;
import psi.lib.CalTools.CalcLittleEndian;
/**
 * <p>�^�C�g��: �u���b�N�f�[�^</p>
 *
 * <p>����: �u���b�N�f�[�^��\������</p>
 *
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 *
 * <p>��Ж�: </p>
 *
 * @author ������
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
    Data = new byte[BLOCK_LENGTH]; //���ۂ̃f�[�^
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
   * �`�F�b�N�T����Ԃ�
   * @return String
   */
  public String getChecksum(){
    return PokeTools.space(PokeTools.toHex(Data[0xff7]),2,"0") + PokeTools.space(PokeTools.toHex(Data[0xff6]),2,"0");
  }
  /**
   * �Z�[�u�J�E���g��Hex�ŕԂ��܂�
   * @return String
   */
  public String getSaveCountHex(){
    return PokeTools.space(PokeTools.toHex(Data[0xffd]),2,"0") + PokeTools.space(PokeTools.toHex(Data[0xffc]),2,"0");
  }
  /**
   * �Z�[�u�J�E���g��Ԃ��܂�
   * @return int
   */
  public long getSaveCount(){
    return PokeTools.ByteToInt(Data[0xffc]) + (PokeTools.ByteToInt(Data[0xffd]) << 8);
  }
  /**
   * �Z�[�u�J�E���g��ݒ肵�܂�
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
    return "��"+PokeTools.space(Integer.toHexString(this.getBlockNumber()),2,"0")+"�u���b�N";
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
    //����
    byte tmpBuff2[] = new byte[2];
    byte writing[] = new byte[2];
    ByteArrayInputStream bais2 = new ByteArrayInputStream(checksum, 0, 4);
    while (bais2.available() > 0) {
      bais2.read(tmpBuff2, 0, 2);
      writing = CalcLittleEndian.addWord(writing, tmpBuff2, 2);
    }
    //��������
    for (int i = 0; i < 2; i++) {
        this.setByte(writing[i], 0xff6 + i);
    }
  }
  public String getCellText(int rowIndex,int columnIndex){
    if (rowIndex >= 0xff) { //�t�b�^�͕ҏW�����Ȃ�
      if (columnIndex == 0x04 + 0x2) {
        return "0xff4�F�u���b�N�i���o�[";
      }
      else if (columnIndex == 0x05 + 0x2) {
        return "0xff5�F�萔�i=0x00�j";
      }
      else if (0x06 + 0x2 <= columnIndex && columnIndex <= 0x07 + 0x2) {
        return "0xff6 -> 0xff7�F�`�F�b�N�T��";
      }
      else if (0x08 + 0x2 <= columnIndex && columnIndex <= 0x0b + 0x2) {
        return "0xff8 -> 0x00b�F�萔(0x08012025)";
      }
      else if (0x0c + 0x2 <= columnIndex && columnIndex <= 0x0d + 0x2) {
        return "0xffc -> 0xffd�F�Z�[�u��";
      }
      else if (0x0e + 0x2 <= columnIndex && columnIndex <= 0x0f + 0x2) {
        return "0xffe -> 0xfff�F�萔(0x0000)";
      }
    }
    return null;
  }
  public Color getBackColor(int Row, int Column, boolean isSelected) {
  Color tmpColor = null;
    //�t�b�^�͕ҏW�����Ȃ�
    if (Row >= 0xff) {
      if (Column == 0x04 + 0x2) {
        //0xff4�F�u���b�N�i���o�[
        tmpColor = Color.cyan;
      }
      else if (Column == 0x05 + 0x2) {
        //0xff5�F�萔�i=0x00�j
        tmpColor = Color.lightGray;
      }
      else if (0x06 + 0x2 <= Column && Column <= 0x07 + 0x2) {
        //statusSet("0xff6 -> 0xff7�F�`�F�b�N�T��");
        tmpColor = Color.GREEN.brighter();
      }
      else if (0x08 + 0x2 <= Column && Column <= 0x0b + 0x2) {
        //statusSet("0xff8 -> 0x00b�F�萔(0x08012025)");
        tmpColor = Color.lightGray;
      }
      else if (0x0c + 0x2 <= Column && Column <= 0x0d + 0x2) {
        //statusSet("0xffc -> 0xffd�F�Z�[�u��");
        tmpColor = Color.pink;
      }
      else if (0x0e + 0x2 <= Column && Column <= 0x0f + 0x2) {
        //statusSet("0xffe -> 0xfff�F�萔(0x0000)");
        tmpColor = Color.lightGray;
      }
    }
  if (isSelected) { //�I������Ă���
    if (tmpColor == null) { //�t�b�^�łȂ�
      return CellColorRenderer.DefaultSelectedBaclGround;
    }
    else {
      return tmpColor.darker();
    }
  }
  else { //�I������Ă��Ȃ��Ȃ�
    if (tmpColor == null) { //�t�b�^�łȂ�
      return CellColorRenderer.DefaultBackGround;
    }
    else {
      return tmpColor;
    }
  }
  }
}
