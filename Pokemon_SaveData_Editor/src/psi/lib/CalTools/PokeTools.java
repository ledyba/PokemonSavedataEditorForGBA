package psi.lib.CalTools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <p>タイトル: ポケモン　セーブデータエディタ</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2005 PSI</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */

public class PokeTools {
  public static final String[] PokeCode = {
      "　", "あ", "い", "う", "え", "お", "か", "き", "く", "け", "こ", "さ", "し", "す", "せ",
      "そ",
      "た", "ち", "つ", "て", "と", "な", "に", "ぬ", "ね", "の", "は", "ひ", "ふ", "へ", "ほ",
      "ま",
      "み", "む", "め", "も", "や", "ゆ", "よ", "ら", "り", "る", "れ", "ろ", "わ", "を", "ん",
      "ぁ",
      "ぃ", "ぅ", "ぇ", "ぉ", "ゃ", "ゅ", "ょ", "が", "ぎ", "ぐ", "げ", "ご", "ざ", "じ", "ず",
      "ぜ",

      "ぞ", "だ", "じ", "ず", "で", "ど", "ば", "び", "ぶ", "べ", "ぼ", "ぱ", "ぴ", "ぷ", "ぺ",
      "ぽ",
      "っ", "ア", "イ", "ウ", "エ", "オ", "カ", "キ", "ク", "ケ", "コ", "サ", "シ", "ス", "セ",
      "ソ",
      "タ", "チ", "ツ", "テ", "ト", "ナ", "ニ", "ヌ", "ネ", "ノ", "ハ", "ヒ", "フ", "ヘ", "ホ",
      "マ",
      "ミ", "ム", "メ", "モ", "ヤ", "ユ", "ヨ", "ラ", "リ", "ル", "レ", "ロ", "ワ", "ヲ", "ン",
      "ァ",

      "ィ", "ゥ", "ェ", "ォ", "ャ", "ュ", "ョ", "ガ", "ギ", "グ", "ゲ", "ゴ", "ザ", "ジ", "ズ",
      "ゼ",
      "ゾ", "ダ", "ヂ", "ヅ", "デ", "ド", "バ", "ビ", "ブ", "ベ", "ボ", "パ", "ピ", "プ", "ペ",
      "ポ",
      "ッ", "０", "１", "２", "３", "４", "５", "６", "７", "８", "９", "！", "？", "。", "ー",
      "・",
      "･･", "『", "』", "「", "」", "♂", "♀", "円", "．", "ん", "／", "Ａ", "Ｂ", "Ｃ",
      "Ｄ", "Ｅ",

      "Ｆ", "Ｇ", "Ｈ", "Ｉ", "Ｊ", "Ｋ", "Ｌ", "Ｍ", "Ｎ", "Ｏ", "Ｐ", "Ｑ", "Ｒ", "Ｓ", "Ｔ",
      "Ｕ",
      "Ｖ", "Ｗ", "Ｘ", "Ｙ", "Ｚ", "ａ", "ｂ", "ｃ", "ｄ", "ｅ", "ｆ", "ｇ", "ｈ", "ｉ", "ｊ",
      "ｋ",
      "ｌ", "ｍ", "ｎ", "ｏ", "ｐ", "ｑ", "ｒ", "ｓ", "ｔ", "ｕ", "ｖ", "ｗ", "ｘ", "ｙ", "ｚ",
      "▼",
      "：", "A\"", "O\"", "U\"", "a\"", "o\"", "u\"", "↑", "↓", "←", "→", "＿",
      "＿", "＿", "＿", "＿", //特殊文字はごまかさせてください
  };
  public static String toPokemonCode(byte code) {
    return PokeCode[ByteToInt(code)];
  }

  //テキスト整形用
  //value:処理の元の文字
  //digit：あわせたい文字数
  //spacer穴埋めする文字
  public static String space(String value, int digit, String spacer) {
    String str = "";
    int insert_space; //空白を入れる数
    insert_space = digit - value.length();
    for (int i = 0; i < insert_space; i++) {
      str += spacer;
    }
    str += value;
    return str;
  }

  public static String[][] toString(byte[] data, int bytes, int start_add) { //byteからStringに変換
    String[][] returning;
    if (bytes % 16 == 0) {
      returning = new String[bytes / 16][18];
    }
    else {
      returning = new String[bytes / 16 + 1][18];
    }
    int line_count = 0;
    boolean file_end = true;
    while (file_end) {
      //オフセット・アドレス表記
      returning[line_count][0] = space(Integer.toHexString(line_count * 16 +
          start_add), 8, "0");
      returning[line_count][1] = space(Integer.toHexString(line_count * 16), 8,
                                       "0");
      //最後が無い場合
      if ( (line_count * 16 + 16) < bytes) {
        for (int i = 2; i < 18; i++) {
          returning[line_count][i] = space(toHex(data[line_count * 16 + i - 2]),
                                           2, "0");
        }
      }
      else { //ファイルが終了するとき
        file_end = false;
        int count = bytes - (16 * line_count) + 2;
        for (int i = 2; i < count; i++) {
          returning[line_count][i] = space(toHex(data[line_count * 16 + i - 2]),
                                           2, "0");
        }
      }
      line_count++;
    }
    return returning;
  }

  public static String toHex(byte data) {
    String HexString = "";
    if (data < 0) {
      //符号付で0未満なら+256
      HexString = Integer.toHexString( (int) data + 256);
    }
    else {
      HexString = Integer.toHexString( (int) data);
    }
    return HexString;
  }

  public static byte IntToByte(int data) {
    byte returning = 0;
    if (data > 127) {
      returning = (byte) (data - 256);
    }
    else {
      returning = (byte) data;
    }
    return returning;
  }

  public static int ByteToInt(byte data) {
    if (data >= 0) {
      return data;
    }
    else {
      return data + 256;
    }
  }

  public static long toLong(byte[] num) {
    long result = 0;
    int radix = 0;
    for (int i = 0; i < num.length; i++) {
      result += (ByteToInt(num[i]) << radix);
      radix += 8;
    }
    /*
         if(result < 0){
      System.out.println(result);
      radix = 0;
      for(int i=0;i<num.length;i++){
        System.out.println(tools.ByteToInt(num[i]) << radix);
        radix += 8;
      }
         }*/
    return result;
  }

  public static byte[] toByteArray(int num) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    int mask = 0xff;
    int mag = 0;
    int writing = 0;
    writing = (num & mask);
    do {
      /*
      System.out.println("n:" + Integer.toHexString(num) + " m:" +
                         Integer.toHexString(mask) + " w;" +
                         Integer.toHexString(writing) + "->" +
                         Integer.toHexString((byte)((num & mask) >> mag)));
      */
      writing = (byte) (writing >> (mag));
      mag += 8;
      mask = mask << 8;
      baos.write(writing);
      writing = (num & mask);
    }
    while ( ( (mask <= num) || (writing != 0) ) && mag < 32);
    return baos.toByteArray();
  }

  public static byte[] toByteArray(int num, int length) {
    try {
      byte[] arr = PokeTools.toByteArray(num);
      /*
      for (int i = 0; i < arr.length; i++) {
        System.out.print(PokeTools.space(String.valueOf(Integer.toHexString(arr[
            i])), 2, "0")+" ");
      }
      System.out.println("");*/
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      if (arr.length > length) {
        baos.write(arr, 0, 4);
      }
      else {
        byte[] space = new byte[length - arr.length];
        baos.write(arr);
        baos.write(space);
      }
      return baos.toByteArray();
    }
    catch (IOException ex) {
      return null;
    }
  }
}
