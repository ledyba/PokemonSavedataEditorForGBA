package PokemonSaveDataEditorForGBA.data;

/**
 * <p>タイトル: 最終ブロック</p>
 *
 * <p>説明: 最終ブロックを表すセーブデータ</p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: </p>
 *
 * @author 未入力
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
   * 最終ブロックは-1を返す
   * @return int
   */
  public long getSaveCount() {
    return -1;
  }

  /**
   * 返す
   * @param number int
   * @return BlockData
   */
  public BlockData getBlockData(int number) {
    return Block[number];
  }

  public BlockData getBlockData(boolean flag) { //使ってません
    return null;
  }

  public String toString() {
    return "最終データ";
  }

  /**
   * ブロック番号から実際の並び順を返す．この場合，同じなのでそのまま返す．
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
