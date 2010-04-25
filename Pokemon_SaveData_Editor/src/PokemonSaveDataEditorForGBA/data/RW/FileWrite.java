package PokemonSaveDataEditorForGBA.data.RW;

import PokemonSaveDataEditorForGBA.Frame;
import PokemonSaveDataEditorForGBA.data.*;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;


/**
 * <p>タイトル: セーブデータ書き込み</p>
 *
 * <p>説明: セーブデータを書き込みます．</p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: </p>
 *
 * @author 未入力
 * @version 1.0
 */
public class FileWrite {
  private File OutputFile;
  private FileOutputStream out;
  private SaveFileData data;
  private Save saveData;
  private boolean isSaveDataMode;//セーブデータのみの書き込み
  public FileWrite(String filename,Object data) {
    OutputFile = new File(filename);
    if(data instanceof SaveFileData){
      this.data = (SaveFileData)data;
      isSaveDataMode = false;
    }else if(data instanceof Save){
      this.saveData = (Save)data;
      isSaveDataMode=true;
    }
    try{
      out = new FileOutputStream(OutputFile);
    }catch(java.io.FileNotFoundException e){
      Frame.setStatus("FileNotFound:"+OutputFile.getPath()+OutputFile.getName());
    }
  }
  public void write(){
    if (isSaveDataMode) {//セーブ一つだけ書き込み
      SaveWrite sw = new SaveWrite(out, saveData);
      sw.write();
    }else{
      for (int i = 0; i < data.SAVES; i++) { //セーブ単位で書き込み
        SaveWrite sw = new SaveWrite(out, data.getSave(i));
        sw.write();
      }
      Frame.setStatus("ファイル書き込み完了！");
    }
    //ストリームを閉じる
    try{
      out.close();
    }catch(IOException e){
      //何もしない＾＾
      }
  }
}
