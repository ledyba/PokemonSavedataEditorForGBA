package PokemonSaveDataEditorForGBA.data.RW;
import PokemonSaveDataEditorForGBA.data.*;
import PokemonSaveDataEditorForGBA.Frame;
import java.io.File;
import java.io.FileInputStream;

/**
 * <p>タイトル: ポケモン　セーブデータエディタ</p>
 *
 * <p>説明: </p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: </p>
 *
 * @author 未入力
 * @version 1.0
 */
public class FileRead {
  File InputFile;
  SaveFileData dataFile;
  FileInputStream in;
  int Addr=0;
  public FileRead(String FileName) {
    InputFile = new File(FileName);
    dataFile = new SaveFileData();
  }
  public SaveFileData read(){
    if(FileRead()){
      Frame.setStatus("ファイル読み込み完了！");
      return dataFile;//返す
    }
    return null;
  }
  private boolean FileRead(){
    try{
      in = new FileInputStream(InputFile);
      if(in.available() != 128 * 1024){
        Frame.setStatus("ファイルサイズが正規の物ではありません");
        return false;
      }
      for(int i=0;i<SaveFileData.SAVES;i++){
        dataFile.getSave(i).setAddr(Addr);
        SaveRead(i);
      }
      in.close();
    }catch(java.io.IOException e){
      Frame.setStatus("IOエラー："+e.getMessage());
      return false;
    }
    return true;
  }
  /**
   * 二つのセーブデータについて読み込みます
   * @param number int
   */
  private void SaveRead(int number){
    byte[] Data;
    try{
      for (int i = 0; i < dataFile.getSave(number).getBlocks(); i++) {
        Data = new byte[dataFile.getSave(number).getBlockData(i).getLength()];//データを確保
        dataFile.getSave(number).getBlockData(i).setAddr(Addr);//アドレスの設定
        Addr += Data.length;
        in.read(Data);//読み込み
        dataFile.getSave(number).getBlockData(i).setByteArray(Data);//書き込み
      }
    }catch(java.io.IOException e){
      Frame.setStatus("IOエラー："+e.getMessage());
    }
  }
}
