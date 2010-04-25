package PokemonSaveDataEditorForGBA.data;

import java.io.ByteArrayInputStream;
import PokemonSaveDataEditorForGBA.CellColorRenderer;
import java.awt.Color;
import psi.lib.CalTools.PokeTools;

/**
 * <p>�^�C�g��: �|�P�����Z�[�u�f�[�^�G�f�B�^ for GBA</p>
 *
 * <p>����: �a������f�[�^������킵�܂��D</p>
 *
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 *
 * <p>��Ж�: �Ձi�v�T�C�j�̋����֐S���</p>
 *
 * @author PSI
 * @version 1.0
 */
public class EndClearBlock
    extends BlockData {
  public static int BLOCK_LENGTH = 0x1000;
//  public static final int START_ADDR[] = {0x1c000,0x1d000};//���ꂼ��̓a������f�[�^�̎n�܂�Ƃ���
//  public static final int START_ADDR_1 = 0x1c000;//���ꂼ��̓a������f�[�^�̎n�܂�Ƃ���
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
   * �`�F�b�N�T����Ԃ�
   * @return String
   */
  public String getChecksum() {
      //ff4,ff5���`�F�b�N�T���D
    return PokeTools.space(PokeTools.toHex(this.getByte(0xff5)), 2, "0") +
        PokeTools.space(PokeTools.toHex(this.getByte(0xff4)), 2, "0");
  }
  /**
   * �Z�[�u�J�E���g��Hex�ŕԂ��܂�
   * @return String
   */
  public String getSaveCountHex(){
    return "****";
  }
  /**
   * �Z�[�u�J�E���g��Ԃ��܂�
   * @return int
   */
  public long getSaveCount(){
    return -1;
  }
  /**
   * �Z�[�u�J�E���g��ݒ肵�܂�
   * @param SaveCount int
   */
  public void setSaveCount(int SaveCount){
  }
  /**
   * �u���b�N�i���o�[�͎��ۂ̃f�[�^���я��Ƃ��܂��D
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
    return num1+":�a������u���b�N"+num2;
  }
  public String getCellText(int rowIndex, int columnIndex) {
    if (rowIndex == 0xff) { //�t�b�^�͕ҏW�����Ȃ�
      if (0x04 + 0x2 <= columnIndex && columnIndex <= 0x05 + 0x2) {
        return "0xff4 -> 0xff5�F�`�F�b�N�T��";
      }else if (0x06 + 0x2 <= columnIndex && columnIndex <= 0x07 + 0x2) {
        return "0xff6 -> 0xff7�F�萔�i=0x0000�j";
      }else if (0x08 + 0x2 <= columnIndex && columnIndex <= 0x0b + 0x2) {
        return "0xff8 -> 0xffb�F�萔(0x08012025)";
      }else if (0x0c + 0x2 <= columnIndex && columnIndex <= 0x0f + 0x2) {
        return "0xffc -> 0xfff�F�萔�i=0x00000000�j";
      }
    }
    return null;
  }

  public Color getBackColor(int Row, int Column, boolean isSelected) {
    Color tmpColor = null;
    //�t�b�^�͕ҏW�����Ȃ�
    if (Row == 0xff) {
      if (0x04 + 0x2 <= Column && Column <= 0x05 + 0x2) {
        //return "0xff4 -> 0xff5�F�`�F�b�N�T��";
        tmpColor = Color.GREEN.brighter();
      }else if (0x06 + 0x2 <= Column && Column <= 0x0f + 0x2) {
        //�S���萔
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

  public void calcChecksum(){//�`�F�b�N�T������
    byte checksum[] = new byte[4];
    byte[] tmpBuff = new byte[4];
    ByteArrayInputStream bais = new ByteArrayInputStream(Data, 0, 0xff4);
    while (bais.available() > 0) {
      bais.read(tmpBuff, 0, 4);
      //checksum = CalcLittleEndian.addHalfWord(checksum, PokeTools.toLong(tmpBuff));
      checksum = psi.lib.CalTools.CalcLittleEndian.addWord(checksum,tmpBuff,4);
    }
    //����
    byte tmpBuff2[] = new byte[2];
    byte writing[] = new byte[2];
    ByteArrayInputStream bais2 = new ByteArrayInputStream(checksum, 0, 4);
    while (bais2.available() > 0) {
      bais2.read(tmpBuff2, 0, 2);
      writing = psi.lib.CalTools.CalcLittleEndian.addWord(writing, tmpBuff2, 2);
    }
    //�������݁@�J�n��ff4����
    for (int i = 0; i < 2; i++) {
        this.setByte(writing[i], 0xff4 + i);
    }
  }
}
