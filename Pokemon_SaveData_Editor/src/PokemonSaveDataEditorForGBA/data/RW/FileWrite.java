package PokemonSaveDataEditorForGBA.data.RW;

import PokemonSaveDataEditorForGBA.Frame;
import PokemonSaveDataEditorForGBA.data.*;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;


/**
 * <p>�^�C�g��: �Z�[�u�f�[�^��������</p>
 *
 * <p>����: �Z�[�u�f�[�^���������݂܂��D</p>
 *
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 *
 * <p>��Ж�: </p>
 *
 * @author ������
 * @version 1.0
 */
public class FileWrite {
  private File OutputFile;
  private FileOutputStream out;
  private SaveFileData data;
  private Save saveData;
  private boolean isSaveDataMode;//�Z�[�u�f�[�^�݂̂̏�������
  public FileWrite(String filename,Object data) {
    OutputFile = new File(filename);
    if(data instanceof SaveFileData){
      this.data = (SaveFileData)data;
      isSaveDataMode = false;
    }else if(data instanceof Save){
      this.saveData = (Save)data;
      isSaveDataMode=true;
    }
    try{
      out = new FileOutputStream(OutputFile);
    }catch(java.io.FileNotFoundException e){
      Frame.setStatus("FileNotFound:"+OutputFile.getPath()+OutputFile.getName());
    }
  }
  public void write(){
    if (isSaveDataMode) {//�Z�[�u�������������
      SaveWrite sw = new SaveWrite(out, saveData);
      sw.write();
    }else{
      for (int i = 0; i < data.SAVES; i++) { //�Z�[�u�P�ʂŏ�������
        SaveWrite sw = new SaveWrite(out, data.getSave(i));
        sw.write();
      }
      Frame.setStatus("�t�@�C���������݊����I");
    }
    //�X�g���[�������
    try{
      out.close();
    }catch(IOException e){
      //�������Ȃ��O�O
      }
  }
}
