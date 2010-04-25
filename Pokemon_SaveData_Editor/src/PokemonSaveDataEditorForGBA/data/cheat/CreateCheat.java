package PokemonSaveDataEditorForGBA.data.cheat;

import PokemonSaveDataEditorForGBA.data.*;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * <p>タイトル: ポケモンセーブデータエディタ for GBA</p>
 *
 * <p>説明: </p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author PSI
 * @version 1.0
 */
public class CreateCheat {
  SaveFileData Data;
  int Mode;
  int Style=0;
  int Limit = 0;
  int Line = 0;
  public static final int MODE_OLD_NEW = 1;//旧→新
  public static final int MODE_NEW_OLD = 2;//新→旧
  public CreateCheat(SaveFileData Data,int Mode,int Style,int line,int limit) {
    this.Data = Data;
    this.Mode = Mode;
    this.Line = line;
    this.Style=Style;
    this.Limit = limit;
  }
  public CheatCode[] CreateCheatCode(){
    LinkedList DiffList = new LinkedList();
    Save before_save=null;
    Save after_save=null;
    if(Mode == this.MODE_OLD_NEW){//旧→新
      before_save = Data.getSave(false);
      after_save = Data.getSave(true);
    }else if(Mode == this.MODE_NEW_OLD){//新→旧
      before_save = Data.getSave(true);
      after_save = Data.getSave(false);
    }

    //違うところリスト化
    for(int i=0;i<Save.BLOCKS;i++){//変化を記述
      BlockData before = before_save.getBlockData(before_save.getBlockNumberInOrder(i));
      BlockData after = after_save.getBlockData(after_save.getBlockNumberInOrder(i));
      int limit = 0;
      int Start = 0;
      int End = 0;
      boolean isChange = false;//フラグ
      for(int j=0;j<0xff4;j++){//フッタはサーチしない
        if(before.getByte(j) != after.getByte(j)){
//          System.out.println(tools.toHex(before.getByte(j)) +":"+ tools.toHex(after.getByte(j)));
          limit = 0;//リミットは元に戻る
          End = j;//最後はここになるかも．
          if(!isChange){//閾値を越えた後
            Start = j;
          }
          isChange = true;
        }else{
          if (isChange) {
            limit++;
            if (limit >= this.Limit) { //閾値に達しているならば
              isChange = false;
              limit = 0;
//              System.out.println(i+":"+Start+":"+End);
              DiffList.add(new DiffArea(i,Start,End));
            }
          }
        }
      }
    }

    //チートコード変換
    Iterator It = DiffList.listIterator();
    LinkedList CodeList = new LinkedList();
    while(It.hasNext()){
      DiffArea area = (DiffArea)It.next();
      int off = area.StartOff;
      while(off <= area.EndOff){
        int line = this.Line;
        if(area.EndOff - off < this.Line){
          line = (area.EndOff - off)+1;
        }
        byte[] b = new byte[line];
        BlockData block = after_save.getBlockData(after_save.getBlockNumberInOrder(area.Block));
        for(int i=0;i<line;i++){
          b[i] = block.getByte(off+i);
        }
        CodeList.add(new CheatCode(this.Style,area.Block,off,b));
        off += this.Line;
      }
    }
    //コード変換
    CheatCode[] Codes = new CheatCode[CodeList.size()];
    int count2 = 0;
    Iterator It2 = CodeList.listIterator();
    while(It2.hasNext()){
      Codes[count2] = (CheatCode)It2.next();
      count2++;
    }
    return Codes;
  }
}
