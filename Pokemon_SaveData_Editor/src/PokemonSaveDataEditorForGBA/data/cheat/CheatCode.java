package PokemonSaveDataEditorForGBA.data.cheat;

import PokemonSaveDataEditorForGBA.data.*;
import psi.lib.CalTools.PokeTools;

/**
 * <p>タイトル: チートコード</p>
 *
 * <p>説明: チートコードを表現するクラスです</p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author PSI
 * @version 1.0
 */
public class CheatCode {
  public static final int MODE_WRITE_NEWER_SAVE_DATA = 1; //新しいほうのセーブデータに書きこむ
  public static final int MODE_WRITE_OLDER_SAVE_DATA = 2; //古いほうのセーブデータに書き込む
  public static final int MODE_WRITE_BOTH_SAVE_DATA = 3; //両方に書き込む
  public static final int MODE_WRITE_END_SAVE_DATA = 4; //最終セーブデータに書き込む
  public static final int MODE_WRITE_NOTHING = -1; //エラーなどで何も書き込まない
  public static final int MODE_COMMENT = -2; //コメントですので．何か？
  public static final String[] MODE_TEXT = {
      "", "new", "old", "both", "end"}; //一応対応させておきます
  public static final String ERROR_MSG = "[ERROR]";
  private String CodeText = ""; //テキスト
  private int Mode = 0; //モード
  private int Block = 0; //ブロック
  private int Addr = 0; //アドレス
  private byte[] Code; //コード
  private SaveFileData Data;
  public CheatCode(String codeStr) {
    this.CodeText = codeStr;
    this.strAnalysis(codeStr);
  }

  public CheatCode(int Mode, int Block, int Addr, byte[] Code) {
    this.Mode = Mode; //モード
    this.Block = Block; //ブロック
    this.Addr = Addr; //アドレス
    this.Code = Code; //コード
  }

  public boolean check() {
    return this.Mode != this.MODE_WRITE_NOTHING;
  }

  public boolean cheat(SaveFileData Data) {
    switch (Mode) {
      case MODE_WRITE_NEWER_SAVE_DATA:
        this.write(Data.getSave(true), Block, Addr, Code);
        break;
      case MODE_WRITE_OLDER_SAVE_DATA:
        this.write(Data.getSave(false), Block, Addr, Code);
        break;
      case MODE_WRITE_BOTH_SAVE_DATA:
        this.write(Data.getSave(true), Block, Addr, Code);
        this.write(Data.getSave(false), Block, Addr, Code);
        break;
      case MODE_WRITE_END_SAVE_DATA:
        //System.out.println(Block);
        if (Block == -1) { //新仕様にあわせ修正
          Block = Addr / 0x1000;
          Addr = Addr - (Block * 0x1000);
        }
        this.write(Data.getSave(2), Block, Addr, Code);
        break;
      case MODE_COMMENT: //コメント
        break;
      case MODE_WRITE_NOTHING: //エラーですから
        return false;
    }
    return true;
  }

  public String toString() {
    if (this.Mode == this.MODE_WRITE_NOTHING) {
      if (!this.CodeText.startsWith(this.ERROR_MSG)) {
        this.CodeText = this.ERROR_MSG + this.getCodeText();
      }
      return this.getCodeText();
    }
    else {
      return this.getCodeText();
    }
  }

  private void write(Save save, int blockNumber, int addr, byte[] code) {
    BlockData block = save.getBlockData(save.getBlockNumberInOrder(blockNumber));
    for (int i = 0; i < code.length; i++) {
      block.setByte(code[i], addr + i);
    }
  }

  private void strAnalysis(String str) {
    /**
     * フォーマット詳細
     * MODE:BLOCK:ADDR:EVAL
     * データの並びはビッグエンディアン（実際の並び）
     * ";"で始まる行は無視
     */
    if (str.startsWith(";")) {
      this.Mode = this.MODE_COMMENT;
      return;
    }
    try {
      String[] array = str.split(":");
      if (array.length != 4) { //4つのブロックに分けられない＝エラー
        this.Mode = this.MODE_WRITE_NOTHING; //エラーですよね
        return;
      }
      this.Mode = this.getModeCode(array[0]);

      if (array[1].equals("") || array[1] == null) { //何も書いて無いなら
        this.Block = -1;
      }
      else {
        this.Block = Integer.parseInt(array[1], 16);
      }
      this.Addr = Integer.parseInt(array[2], 16);

      if (this.Mode == this.MODE_WRITE_END_SAVE_DATA && Block < 0) { //最終ブロックに書き込み，オフセット書き込みならば
        if (0 <= Addr && Addr < EndSave.getLength()) { //範囲内
        }
        else { //範囲外
          this.Mode = this.MODE_WRITE_NOTHING; //エラーですよね
          return;
        }
      }
      else {
        if (0 <= Block && Block < Save.BLOCKS
            && 0 <= Addr && Addr < BlockData.BLOCK_LENGTH) { //範囲内
        }
        else { //範囲外
          this.Mode = this.MODE_WRITE_NOTHING; //エラーですよね
          return;
        }
      }
      if ( (array[3].length() & 1) == 1) { //下位ビット１＝バイトごとで無い
        this.Mode = this.MODE_WRITE_NOTHING; //エラーですよね
        return;
      }
      this.Code = this.getByteArray(array[3]);

    }
    catch (Exception e) {
      this.Mode = this.MODE_WRITE_NOTHING; //エラーですよね
      return;
    }
  }

  public static int getModeCode(String str) {
    int result = MODE_WRITE_NOTHING;
    str = str.toLowerCase();
    if (str.equals("new")) {
      result = MODE_WRITE_NEWER_SAVE_DATA;
    }
    else if (str.equals("old")) {
      result = MODE_WRITE_OLDER_SAVE_DATA;
    }
    else if (str.equals("both")) {
      result = MODE_WRITE_BOTH_SAVE_DATA;
    }
    else if (str.equals("end")) {
      result = MODE_WRITE_END_SAVE_DATA;
    }
    return result;
  }

  private byte[] getByteArray(String str) {
    byte[] result = new byte[str.length() >> 1];
    int count = 0;
    while (count + 2 <= str.length()) {
      result[count >>
          1] = PokeTools.IntToByte(Integer.parseInt(str.substring(count, count + 2),
                                                16));
      count += 2;
    }
    return result;
  }

  //コードを返す
  private String getCodeText() {
    if (this.CodeText == null || this.CodeText.equals("")) { //空欄ならば
      if (this.Mode >= 0) {
        String str = CheatCode.MODE_TEXT[this.Mode] + ":";
        str += PokeTools.space(Integer.toHexString(this.Block), 2, "0") + ":";
        str += PokeTools.space(Integer.toHexString(this.Addr), 4, "0") + ":";
        for (int i = 0; i < this.Code.length; i++) {
          str += PokeTools.space(PokeTools.toHex(this.Code[i]), 2, "0");
        }
        return str;
      }
      else {
        return this.ERROR_MSG;
      }
    }
    else {
      return CodeText;
    }
  }
}
