package PokemonSaveDataEditorForGBA.data.RW;
import PokemonSaveDataEditorForGBA.data.*;
import PokemonSaveDataEditorForGBA.Frame;
import java.io.File;
import java.io.FileInputStream;

/**
 * <p>�^�C�g��: �|�P�����@�Z�[�u�f�[�^�G�f�B�^</p>
 *
 * <p>����: </p>
 *
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 *
 * <p>��Ж�: </p>
 *
 * @author ������
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
      Frame.setStatus("�t�@�C���ǂݍ��݊����I");
      return dataFile;//�Ԃ�
    }
    return null;
  }
  private boolean FileRead(){
    try{
      in = new FileInputStream(InputFile);
      if(in.available() != 128 * 1024){
        Frame.setStatus("�t�@�C���T�C�Y�����K�̕��ł͂���܂���");
        return false;
      }
      for(int i=0;i<SaveFileData.SAVES;i++){
        dataFile.getSave(i).setAddr(Addr);
        SaveRead(i);
      }
      in.close();
    }catch(java.io.IOException e){
      Frame.setStatus("IO�G���[�F"+e.getMessage());
      return false;
    }
    return true;
  }
  /**
   * ��̃Z�[�u�f�[�^�ɂ��ēǂݍ��݂܂�
   * @param number int
   */
  private void SaveRead(int number){
    byte[] Data;
    try{
      for (int i = 0; i < dataFile.getSave(number).getBlocks(); i++) {
        Data = new byte[dataFile.getSave(number).getBlockData(i).getLength()];//�f�[�^���m��
        dataFile.getSave(number).getBlockData(i).setAddr(Addr);//�A�h���X�̐ݒ�
        Addr += Data.length;
        in.read(Data);//�ǂݍ���
        dataFile.getSave(number).getBlockData(i).setByteArray(Data);//��������
      }
    }catch(java.io.IOException e){
      Frame.setStatus("IO�G���[�F"+e.getMessage());
    }
  }
}
