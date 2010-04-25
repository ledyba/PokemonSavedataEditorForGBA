package PokemonSaveDataEditorForGBA.data;

import java.io.ByteArrayInputStream;
import PokemonSaveDataEditorForGBA.CellColorRenderer;
import java.awt.Color;
import psi.lib.CalTools.PokeTools;
import psi.lib.CalTools.CalcLittleEndian;

/**
 * <p>�^�C�g��: �|�P�����Z�[�u�f�[�^�G�f�B�^ for GBA</p>
 *
 * <p>����: �o�g���f�[�^</p>
 *
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 *
 * <p>��Ж�: �Ձi�v�T�C�j�̋����֐S���</p>
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
   * �`�F�b�N�T����Ԃ�
   * @return String
   */
  public String getChecksum() {
    //f80 ���� f83���`�F�b�N�T���D
    return
     PokeTools.space(PokeTools.toHex(this.getByte(0xf83)), 2, "0") +
     PokeTools.space(PokeTools.toHex(this.getByte(0xf82)), 2, "0") +
     PokeTools.space(PokeTools.toHex(this.getByte(0xf81)), 2, "0") +
     PokeTools.space(PokeTools.toHex(this.getByte(0xf80)), 2, "0")
     ;
  }

  /**
   * �Z�[�u�J�E���g��Hex�ŕԂ��܂�
   * @return String
   */
  public String getSaveCountHex() {
    return "****";
  }

  /**
   * �Z�[�u�J�E���g��Ԃ��܂�
   * @return int
   */
  public long getSaveCount() {
    return -1;
  }

  /**
   * �Z�[�u�J�E���g��ݒ肵�܂�
   * @param SaveCount int
   */
  public void setSaveCount(int SaveCount) {
  }

  /**
   * �u���b�N�i���o�[�͎��ۂ̃f�[�^���я��Ƃ��܂��D
   * @return int
   */
  public int getBlockNumber() {
    return 3;
  }

  public String toString() {
    return "3:Em�o�g�����O�֘A�f�[�^";
  }

  public String getCellText(int rowIndex, int columnIndex) {
    if(rowIndex == 0x0){//�擪�͌Œ�
      if (0x00 + 0x2 <= columnIndex && columnIndex <= 0x03 + 0x2) {
              return "0x000 -> 0x003�F�萔�i=0x0000b39d�j";
      }
    } else if (rowIndex == 0xf8) { //�t�b�^�͕ҏW�����Ȃ�
      if (0x00 + 0x2 <= columnIndex && columnIndex <= 0x03 + 0x2) {
        return "0xf80 -> 0xf83�F�`�F�b�N�T��";
      }
    }
    return null;
  }

  public Color getBackColor(int Row, int Column, boolean isSelected) {
    Color tmpColor = null;
    if(Row == 0x0){//�擪�͌Œ�
      if (0x00 + 0x2 <= Column && Column <= 0x03 + 0x2) {
          //return "0x000 -> 0x003�F�萔�i=0x0000b39d�j";
          tmpColor = Color.lightGray;
      }
    } else if (Row == 0xf8) { //�t�b�^�͕ҏW�����Ȃ�
      if (0x00 + 0x2 <= Column && Column <= 0x03 + 0x2) {
          //return "0xf80 -> 0xf83�F�`�F�b�N�T��";
          tmpColor = Color.GREEN.brighter();
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

  public void calcChecksum() { //�`�F�b�N�T���@���̂Ƃ���͌v�Z���Ȃ�
        byte checksum[] = new byte[4];
        byte[] tmpBuff = new byte[4];
        ByteArrayInputStream bais = new ByteArrayInputStream(Data, 0, 0xf80);
    bais.skip(4);
        while (bais.available() > 0) {
          bais.read(tmpBuff, 0, 1);
          //checksum = CalcLittleEndian.addHalfWord(checksum, PokeTools.toLong(tmpBuff));
          checksum = CalcLittleEndian.addWord(checksum,tmpBuff,4);
        }

        //�������݁@�J�n��f80����
        for (int i = 0; i < 4; i++) {
            this.setByte((byte)(checksum[i]), 0xf80 + i);
        }
  }
}
