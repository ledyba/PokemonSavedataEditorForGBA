package PokemonSaveDataEditorForGBA.data.cheat;

import PokemonSaveDataEditorForGBA.data.*;
import psi.lib.CalTools.PokeTools;

/**
 * <p>�^�C�g��: �`�[�g�R�[�h</p>
 *
 * <p>����: �`�[�g�R�[�h��\������N���X�ł�</p>
 *
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 *
 * <p>��Ж�: �Ձi�v�T�C�j�̋����֐S���</p>
 *
 * @author PSI
 * @version 1.0
 */
public class CheatCode {
  public static final int MODE_WRITE_NEWER_SAVE_DATA = 1; //�V�����ق��̃Z�[�u�f�[�^�ɏ�������
  public static final int MODE_WRITE_OLDER_SAVE_DATA = 2; //�Â��ق��̃Z�[�u�f�[�^�ɏ�������
  public static final int MODE_WRITE_BOTH_SAVE_DATA = 3; //�����ɏ�������
  public static final int MODE_WRITE_END_SAVE_DATA = 4; //�ŏI�Z�[�u�f�[�^�ɏ�������
  public static final int MODE_WRITE_NOTHING = -1; //�G���[�Ȃǂŉ����������܂Ȃ�
  public static final int MODE_COMMENT = -2; //�R�����g�ł��̂ŁD�����H
  public static final String[] MODE_TEXT = {
      "", "new", "old", "both", "end"}; //�ꉞ�Ή������Ă����܂�
  public static final String ERROR_MSG = "[ERROR]";
  private String CodeText = ""; //�e�L�X�g
  private int Mode = 0; //���[�h
  private int Block = 0; //�u���b�N
  private int Addr = 0; //�A�h���X
  private byte[] Code; //�R�[�h
  private SaveFileData Data;
  public CheatCode(String codeStr) {
    this.CodeText = codeStr;
    this.strAnalysis(codeStr);
  }

  public CheatCode(int Mode, int Block, int Addr, byte[] Code) {
    this.Mode = Mode; //���[�h
    this.Block = Block; //�u���b�N
    this.Addr = Addr; //�A�h���X
    this.Code = Code; //�R�[�h
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
        if (Block == -1) { //�V�d�l�ɂ��킹�C��
          Block = Addr / 0x1000;
          Addr = Addr - (Block * 0x1000);
        }
        this.write(Data.getSave(2), Block, Addr, Code);
        break;
      case MODE_COMMENT: //�R�����g
        break;
      case MODE_WRITE_NOTHING: //�G���[�ł�����
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
     * �t�H�[�}�b�g�ڍ�
     * MODE:BLOCK:ADDR:EVAL
     * �f�[�^�̕��т̓r�b�O�G���f�B�A���i���ۂ̕��сj
     * ";"�Ŏn�܂�s�͖���
     */
    if (str.startsWith(";")) {
      this.Mode = this.MODE_COMMENT;
      return;
    }
    try {
      String[] array = str.split(":");
      if (array.length != 4) { //4�̃u���b�N�ɕ������Ȃ����G���[
        this.Mode = this.MODE_WRITE_NOTHING; //�G���[�ł����
        return;
      }
      this.Mode = this.getModeCode(array[0]);

      if (array[1].equals("") || array[1] == null) { //���������Ė����Ȃ�
        this.Block = -1;
      }
      else {
        this.Block = Integer.parseInt(array[1], 16);
      }
      this.Addr = Integer.parseInt(array[2], 16);

      if (this.Mode == this.MODE_WRITE_END_SAVE_DATA && Block < 0) { //�ŏI�u���b�N�ɏ������݁C�I�t�Z�b�g�������݂Ȃ��
        if (0 <= Addr && Addr < EndSave.getLength()) { //�͈͓�
        }
        else { //�͈͊O
          this.Mode = this.MODE_WRITE_NOTHING; //�G���[�ł����
          return;
        }
      }
      else {
        if (0 <= Block && Block < Save.BLOCKS
            && 0 <= Addr && Addr < BlockData.BLOCK_LENGTH) { //�͈͓�
        }
        else { //�͈͊O
          this.Mode = this.MODE_WRITE_NOTHING; //�G���[�ł����
          return;
        }
      }
      if ( (array[3].length() & 1) == 1) { //���ʃr�b�g�P���o�C�g���ƂŖ���
        this.Mode = this.MODE_WRITE_NOTHING; //�G���[�ł����
        return;
      }
      this.Code = this.getByteArray(array[3]);

    }
    catch (Exception e) {
      this.Mode = this.MODE_WRITE_NOTHING; //�G���[�ł����
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

  //�R�[�h��Ԃ�
  private String getCodeText() {
    if (this.CodeText == null || this.CodeText.equals("")) { //�󗓂Ȃ��
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
