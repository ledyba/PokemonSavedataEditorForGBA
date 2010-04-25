package PokemonSaveDataEditorForGBA.data;

import PokemonSaveDataEditorForGBA.*;

/**
 * <p>タイトル: セーブデータ一回分を表現するクラス</p>
 *
 * <p>説明: セーブデータ一回分を表現するクラス</p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: </p>
 *
 * @author 未入力
 * @version 1.0
 */
public class Save {
  public static int BLOCKS = 14;
  private SaveFileData Parent;
  private BlockData Blocks[] = new BlockData[BLOCKS]; //データの並び順．実際の並びで表現
  public Save(SaveFileData parent) {
    for (int i = 0; i < Blocks.length; i++) {
      Blocks[i] = new BlockData(this);
    }
    this.Parent = parent;
  }

  private int addr;//アドレス

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
   * セーブデータの回数を設定する
   * @param saveCount int
   */
  public void setSaveCount(long saveCount) {
    for (int i = 0; i < this.getBlocks(); i++) {
      Blocks[i].setSaveCount(saveCount);
    }
    //並び替え．この式で合ってるかなぁ．
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
   * ブロック番号から実際の並び順を返す
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
   * ブロックデータを返す．何も無いなら，nullを返す
   * @param number int
   * @return BlockData
   */
  public BlockData getBlockData(int number) {
    if (0 <= number && number < BLOCKS) {
      return Blocks[number];
    }
    else {
      try {
        throw new Exception("そのようなブロックはありません");
      }
      catch (Exception e) {}
      return null;
    }
  }

  public CustomizedTreeNode getNode() {
    return new CustomizedTreeNode(this);
  }

  public String toString() {
    return "第" + this.getSaveCount() + "回セーブデータ";
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
