package PokemonSaveDataEditorForGBA.data.RW;

import PokemonSaveDataEditorForGBA.Frame;
import PokemonSaveDataEditorForGBA.data.Save;
import java.io.File;
import java.io.FileOutputStream;

/**
 * <p>タイトル: ポケモン　セーブデータエディタ</p>
 *
 * <p>説明: セーブデータのみを書き込む</p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: </p>
 *
 * @author 未入力
 * @version 1.0
 */
public class SaveWrite {
  private FileOutputStream out;
  private Save data;
  public SaveWrite(FileOutputStream out,Save data) {
    this.data = data;
    this.out = out;
  }
  public void write(){
    data.calcCheckSum();//チェックサム計算
    try{
        for (int j = 0; j < data.getBlocks(); j++) {
          out.write(data.getBlockData(j).getByteArray());
      }
    }catch(java.io.IOException e){
      Frame.setStatus("IOエラー："+e.getMessage());
    }
    Frame.setStatus("ファイル書き込み完了！");
  }
}
