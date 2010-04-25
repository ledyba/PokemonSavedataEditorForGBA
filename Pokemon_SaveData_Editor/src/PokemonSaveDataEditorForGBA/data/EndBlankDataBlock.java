package PokemonSaveDataEditorForGBA.data;

import PokemonSaveDataEditorForGBA.CellColorRenderer;
import java.awt.Color;

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
public class EndBlankDataBlock
    extends BlockData {
  public static int BLOCK_LENGTH = 0x1000;
  EndBlankDataBlock(Save parent) {
    super(parent);
  }

  void init() {
    Data = new byte[BLOCK_LENGTH]; //���ۂ̃f�[�^
  }

  public int getLength() {
    return this.BLOCK_LENGTH;
  }

  /**
   * �`�F�b�N�T����Ԃ�
   * @return String
   */
  public String getChecksum() {
    return "****";
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

  public int getBlockNumber() {
    return 2;
  }

  public String toString() {
    return "2:�u�����N�f�[�^";
  }

  public void calcChecksum() { //�������Ȃ�
  }

  public String getCellText(int rowIndex, int columnIndex) {
    //�t�b�^����������
    return null;
  }

  public Color getBackColor(int Row, int Column, boolean isSelected) {
    //�������Ȃ��D
    if (isSelected) { //�I������Ă���
      return CellColorRenderer.DefaultSelectedBaclGround;
    }
    else { //�I������Ă��Ȃ��Ȃ�
      return CellColorRenderer.DefaultBackGround;
    }
  }
}
