package PokemonSaveDataEditorForGBA.data;

/**
 * <p>�^�C�g��: �ŏI�u���b�N</p>
 *
 * <p>����: �ŏI�u���b�N��\���Z�[�u�f�[�^</p>
 *
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 *
 * <p>��Ж�: </p>
 *
 * @author ������
 * @version 1.0
 */

public class EndSave
    extends Save {
  public static int BLOCKS = 4;
  private BlockData Block[] = new BlockData[this.getBlocks()];
  EndSave(SaveFileData parent) {
    super(parent);
    Block[0] = new EndClearBlock(this, 0);
    Block[1] = new EndClearBlock(this, 1);
    Block[2] = new EndBlankDataBlock(this);
    Block[3] = new EndBattleDataBlock(this);
    /*
         for(int i=0;i<BLOCKS;i++){
      Blocks[i] = new EndBlockData(this);
         }*/
    this.setParent(parent);
  }

  public int getBlocks() {
    return BLOCKS;
  }

  /**
   * �ŏI�u���b�N��-1��Ԃ�
   * @return int
   */
  public long getSaveCount() {
    return -1;
  }

  /**
   * �Ԃ�
   * @param number int
   * @return BlockData
   */
  public BlockData getBlockData(int number) {
    return Block[number];
  }

  public BlockData getBlockData(boolean flag) { //�g���Ă܂���
    return null;
  }

  public String toString() {
    return "�ŏI�f�[�^";
  }

  /**
   * �u���b�N�ԍ�������ۂ̕��я���Ԃ��D���̏ꍇ�C�����Ȃ̂ł��̂܂ܕԂ��D
   * @param order int
   * @return int
   */
  public int getBlockNumberInOrder(int order) {
    return order;
  }

  public void calcCheckSum() {
    for (int i = 0; i < this.BLOCKS; i++) {
      this.getBlockData(i).calcChecksum();
    }
  }

  public static int getLength() {
    return EndClearBlock.BLOCK_LENGTH * 2 + EndBlankDataBlock.BLOCK_LENGTH +
        EndBattleDataBlock.BLOCK_LENGTH;
  }
}
