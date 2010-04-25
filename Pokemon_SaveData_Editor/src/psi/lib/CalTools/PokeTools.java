package psi.lib.CalTools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <p>�^�C�g��: �|�P�����@�Z�[�u�f�[�^�G�f�B�^</p>
 * <p>����: </p>
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 * <p>��Ж�: </p>
 * @author ������
 * @version 1.0
 */

public class PokeTools {
  public static final String[] PokeCode = {
      "�@", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
      "��",
      "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
      "��",
      "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
      "��",
      "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
      "��",

      "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
      "��",
      "��", "�A", "�C", "�E", "�G", "�I", "�J", "�L", "�N", "�P", "�R", "�T", "�V", "�X", "�Z",
      "�\",
      "�^", "�`", "�c", "�e", "�g", "�i", "�j", "�k", "�l", "�m", "�n", "�q", "�t", "�w", "�z",
      "�}",
      "�~", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
      "�@",

      "�B", "�D", "�F", "�H", "��", "��", "��", "�K", "�M", "�O", "�Q", "�S", "�U", "�W", "�Y",
      "�[",
      "�]", "�_", "�a", "�d", "�f", "�h", "�o", "�r", "�u", "�x", "�{", "�p", "�s", "�v", "�y",
      "�|",
      "�b", "�O", "�P", "�Q", "�R", "�S", "�T", "�U", "�V", "�W", "�X", "�I", "�H", "�B", "�[",
      "�E",
      "��", "�w", "�x", "�u", "�v", "��", "��", "�~", "�D", "��", "�^", "�`", "�a", "�b",
      "�c", "�d",

      "�e", "�f", "�g", "�h", "�i", "�j", "�k", "�l", "�m", "�n", "�o", "�p", "�q", "�r", "�s",
      "�t",
      "�u", "�v", "�w", "�x", "�y", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
      "��",
      "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
      "��",
      "�F", "A\"", "O\"", "U\"", "a\"", "o\"", "u\"", "��", "��", "��", "��", "�Q",
      "�Q", "�Q", "�Q", "�Q", //���ꕶ���͂��܂������Ă�������
  };
  public static String toPokemonCode(byte code) {
    return PokeCode[ByteToInt(code)];
  }

  //�e�L�X�g���`�p
  //value:�����̌��̕���
  //digit�F���킹����������
  //spacer�����߂��镶��
  public static String space(String value, int digit, String spacer) {
    String str = "";
    int insert_space; //�󔒂����鐔
    insert_space = digit - value.length();
    for (int i = 0; i < insert_space; i++) {
      str += spacer;
    }
    str += value;
    return str;
  }

  public static String[][] toString(byte[] data, int bytes, int start_add) { //byte����String�ɕϊ�
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
      //�I�t�Z�b�g�E�A�h���X�\�L
      returning[line_count][0] = space(Integer.toHexString(line_count * 16 +
          start_add), 8, "0");
      returning[line_count][1] = space(Integer.toHexString(line_count * 16), 8,
                                       "0");
      //�Ōオ�����ꍇ
      if ( (line_count * 16 + 16) < bytes) {
        for (int i = 2; i < 18; i++) {
          returning[line_count][i] = space(toHex(data[line_count * 16 + i - 2]),
                                           2, "0");
        }
      }
      else { //�t�@�C�����I������Ƃ�
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
      //�����t��0�����Ȃ�+256
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
