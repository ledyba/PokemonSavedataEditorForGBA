package PokemonSaveDataEditorForGBA.data.RW;

import PokemonSaveDataEditorForGBA.Frame;
import PokemonSaveDataEditorForGBA.data.Save;
import java.io.File;
import java.io.FileOutputStream;

/**
 * <p>�^�C�g��: �|�P�����@�Z�[�u�f�[�^�G�f�B�^</p>
 *
 * <p>����: �Z�[�u�f�[�^�݂̂���������</p>
 *
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 *
 * <p>��Ж�: </p>
 *
 * @author ������
 * @version 1.0
 */
public class SaveWrite {
  private FileOutputStream out;
  private Save data;
  public SaveWrite(FileOutputStream out,Save data) {
    this.data = data;
    this.out = out;
  }
  public void write(){
    data.calcCheckSum();//�`�F�b�N�T���v�Z
    try{
        for (int j = 0; j < data.getBlocks(); j++) {
          out.write(data.getBlockData(j).getByteArray());
      }
    }catch(java.io.IOException e){
      Frame.setStatus("IO�G���[�F"+e.getMessage());
    }
    Frame.setStatus("�t�@�C���������݊����I");
  }
}
