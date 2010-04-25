package PokemonSaveDataEditorForGBA.data.cheat;

/**
 * <p>タイトル: ポケモンセーブデータエディタ for GBA</p>
 *
 * <p>説明: 変化しているエリアを記述するクラス</p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author PSI
 * @version 1.0
 */
public class DiffArea {
  int Block;
  int StartOff=0;
  int EndOff=0;
  public DiffArea(int block,int start,int end) {
    this.Block = block;
    StartOff = start;
    EndOff = end;
  }
}
