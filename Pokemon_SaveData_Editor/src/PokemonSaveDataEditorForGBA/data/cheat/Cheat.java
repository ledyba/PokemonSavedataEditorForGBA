package PokemonSaveDataEditorForGBA.data.cheat;

import PokemonSaveDataEditorForGBA.data.SaveFileData;
import java.util.LinkedList;
import java.util.Iterator;
/**
 * <p>タイトル: チート用</p>
 *
 * <p>説明: チートを行うクラスです</p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author PSI
 * @version 1.0
 */
public class Cheat {
  CheatCode[] Codes;
  SaveFileData Data;
  public Cheat(String input, SaveFileData data) {
    this.Data = data;
    this.Codes = this.getCheatCode(input);
  }
  public boolean check(){
    boolean result = true;
    for(int i=0;i<Codes.length;i++){
      if(Codes[i].check() == false){
        result = false;
      }
    }
    return result;
  }
  public boolean cheat(){
    boolean result = true;
    for(int i=0;i<Codes.length;i++){
      if(Codes[i].cheat(this.Data) == false){
        result = false;
      }
    }
    return result;
  }
  public String toString(){
    String result = "";
    for(int i=0;i<Codes.length;i++){
      result += Codes[i].toString()+"\n";
    }
    return result;
  }
  private CheatCode[] getCheatCode(String input){
    String str[] = input.split("\n");
    LinkedList codes = new LinkedList();
    for(int i=0;i<str.length;i++){
//      if(!str[i].startsWith(";")){//コメント行は無視
        codes.add(new CheatCode(str[i]));
//      }
    }
    return LinkedListToArray(codes);
  }
  private CheatCode[] LinkedListToArray(LinkedList list){
    CheatCode[] result = new CheatCode[list.size()];
    Iterator it = list.iterator();
    int count=0;
    while(it.hasNext()){
      result[count] = (CheatCode)it.next();
      count++;
    }
    return result;
  }
}
