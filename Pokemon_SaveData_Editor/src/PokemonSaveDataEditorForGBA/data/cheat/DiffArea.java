package PokemonSaveDataEditorForGBA.data.cheat;

/**
 * <p>�^�C�g��: �|�P�����Z�[�u�f�[�^�G�f�B�^ for GBA</p>
 *
 * <p>����: �ω����Ă���G���A���L�q����N���X</p>
 *
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 *
 * <p>��Ж�: �Ձi�v�T�C�j�̋����֐S���</p>
 *
 * @author PSI
 * @version 1.0
 */
public class DiffArea {
  int Block;
  int StartOff=0;
  int EndOff=0;
  public DiffArea(int block,int start,int end) {
    this.Block = block;
    StartOff = start;
    EndOff = end;
  }
}
