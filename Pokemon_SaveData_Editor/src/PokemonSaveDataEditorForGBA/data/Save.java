package PokemonSaveDataEditorForGBA.data;

import PokemonSaveDataEditorForGBA.*;

/**
 * <p>�^�C�g��: �Z�[�u�f�[�^��񕪂�\������N���X</p>
 *
 * <p>����: �Z�[�u�f�[�^��񕪂�\������N���X</p>
 *
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 *
 * <p>��Ж�: </p>
 *
 * @author ������
 * @version 1.0
 */
public class Save {
  public static int BLOCKS = 14;
  private SaveFileData Parent;
  private BlockData Blocks[] = new BlockData[BLOCKS]; //�f�[�^�̕��я��D���ۂ̕��тŕ\��
  public Save(SaveFileData parent) {
    for (int i = 0; i < Blocks.length; i++) {
      Blocks[i] = new BlockData(this);
    }
    this.Parent = parent;
  }

  private int addr;//�A�h���X

  public SaveFileData getParent() {
    return Parent;
  }

  protected void setParent(SaveFileData parent) {
    this.Parent = parent;
  }

  public int getBlocks() {
    return BLOCKS;
  }

  /**
   * �Z�[�u�f�[�^�̉񐔂�ݒ肷��
   * @param saveCount int
   */
  public void setSaveCount(long saveCount) {
    for (int i = 0; i < this.getBlocks(); i++) {
      Blocks[i].setSaveCount(saveCount);
    }
    //���ёւ��D���̎��ō����Ă邩�Ȃ��D
    BlockData[] newBlocks = new BlockData[this.getBlocks()];
    int addr = this.getAddr();
    for (int i = 0; i < this.getBlocks(); i++) {
      newBlocks[i] = this.getBlockData(getBlockNumberInOrder((int) ( ( ( ( (0xe - ( (saveCount - 0x01) % 0xe)) % 0xe) + i) % 0xe))));
      newBlocks[i].setAddr(addr);
      addr+=newBlocks[i].getLength();
    }
    this.Blocks = newBlocks;
  }

  public void calcCheckSum() {
    for (int i = 0; i < this.getBlocks(); i++) {
      Blocks[i].calcChecksum();
    }
  }

  public long getSaveCount() {
    return Blocks[0].getSaveCount();
  }

  /**
   * �u���b�N�ԍ�������ۂ̕��я���Ԃ�
   * @param order int
   * @return int
   */
  public int getBlockNumberInOrder(int order) {
    if (0 <= order && order < BLOCKS) {
      return (int) ( (BLOCKS - Blocks[0].getBlockNumber() + order) % BLOCKS);
    }
    else {
      return -1;
    }
  }

  /**
   * �u���b�N�f�[�^��Ԃ��D���������Ȃ�Cnull��Ԃ�
   * @param number int
   * @return BlockData
   */
  public BlockData getBlockData(int number) {
    if (0 <= number && number < BLOCKS) {
      return Blocks[number];
    }
    else {
      try {
        throw new Exception("���̂悤�ȃu���b�N�͂���܂���");
      }
      catch (Exception e) {}
      return null;
    }
  }

  public CustomizedTreeNode getNode() {
    return new CustomizedTreeNode(this);
  }

  public String toString() {
    return "��" + this.getSaveCount() + "��Z�[�u�f�[�^";
  }

  public void setAddr(int addr) {
    this.addr = addr;
  }

  public int getAddr() {
    return addr;
  }
  public static int getLength(){
   return BlockData.BLOCK_LENGTH * Save.BLOCKS;
  }
}
