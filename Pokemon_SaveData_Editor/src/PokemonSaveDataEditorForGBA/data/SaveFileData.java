package PokemonSaveDataEditorForGBA.data;

/**
 * <p>タイトル: ポケモン　セーブデータエディタ</p>
 *
 * <p>説明: セーブデータを表すクラス</p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: </p>
 *
 * @author 未入力
 * @version 1.0
 */
public class SaveFileData {
  public static final int SAVES = 3;
  private Save Saves[] = new Save[SAVES-1];
  private Save EndSave;
  /**
   * データの初期化
   */
  public SaveFileData() {
    for (int i = 0; i < Saves.length; i++) {
      Saves[i] = new Save(this);
    }
    EndSave = new EndSave(this);
  }
  public long getLength(){
    return Save.getLength() + EndSave.getLength();
  }
  /**
   * 先にあるほうを読み出す
   * @param number int
   * @return Save
   */
  public Save getSave(int number){
    if(number < SAVES-1){
      return Saves[number];
    }
    if(number >= SAVES-1 && number < SAVES){
      return EndSave;
    }
    try{
      throw new Exception("そのようなセーブはありません");
    }catch(Exception e){

    }
    return null;
  }
  /**
   * セーブデータを返す．あたらしいほうか古いほうかを示して．
   * @param isNew boolean
   * @return Save
   */
  public Save getSave(boolean isNew){
    if(isNew){
      if (Saves[0].getSaveCount() > Saves[1].getSaveCount()) {
        return Saves[0];
      }
      else {
        return Saves[1];
      }
    }else{//古いほうを返す
      if (Saves[0].getSaveCount() < Saves[1].getSaveCount()) {
        return Saves[0];
      }
      else {
        return Saves[1];
      }
    }
  }
  /**
   * チェックサムを計算させます
   */
  public void calcCheckSum() {
    for (int i = 0; i < this.SAVES; i++) {
      this.getSave(i).calcCheckSum();
    }
  }
}
