package PokemonSaveDataEditorForGBA.data;

/**
 * <p>�^�C�g��: �|�P�����@�Z�[�u�f�[�^�G�f�B�^</p>
 *
 * <p>����: �Z�[�u�f�[�^��\���N���X</p>
 *
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 *
 * <p>��Ж�: </p>
 *
 * @author ������
 * @version 1.0
 */
public class SaveFileData {
  public static final int SAVES = 3;
  private Save Saves[] = new Save[SAVES-1];
  private Save EndSave;
  /**
   * �f�[�^�̏�����
   */
  public SaveFileData() {
    for (int i = 0; i < Saves.length; i++) {
      Saves[i] = new Save(this);
    }
    EndSave = new EndSave(this);
  }
  public long getLength(){
    return Save.getLength() + EndSave.getLength();
  }
  /**
   * ��ɂ���ق���ǂݏo��
   * @param number int
   * @return Save
   */
  public Save getSave(int number){
    if(number < SAVES-1){
      return Saves[number];
    }
    if(number >= SAVES-1 && number < SAVES){
      return EndSave;
    }
    try{
      throw new Exception("���̂悤�ȃZ�[�u�͂���܂���");
    }catch(Exception e){

    }
    return null;
  }
  /**
   * �Z�[�u�f�[�^��Ԃ��D�����炵���ق����Â��ق����������āD
   * @param isNew boolean
   * @return Save
   */
  public Save getSave(boolean isNew){
    if(isNew){
      if (Saves[0].getSaveCount() > Saves[1].getSaveCount()) {
        return Saves[0];
      }
      else {
        return Saves[1];
      }
    }else{//�Â��ق���Ԃ�
      if (Saves[0].getSaveCount() < Saves[1].getSaveCount()) {
        return Saves[0];
      }
      else {
        return Saves[1];
      }
    }
  }
  /**
   * �`�F�b�N�T�����v�Z�����܂�
   */
  public void calcCheckSum() {
    for (int i = 0; i < this.SAVES; i++) {
      this.getSave(i).calcCheckSum();
    }
  }
}
