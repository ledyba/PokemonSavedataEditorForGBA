package PokemonSaveDataEditorForGBA;

import javax.swing.UIManager;
import java.awt.*;

/**
 * <p>�^�C�g��: �|�P�����@�Z�[�u�f�[�^�G�f�B�^</p>
 * <p>����: </p>
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 * <p>��Ж�: </p>
 * @author ������
 * @version 1.0
 */

public class PSDEditor {
  boolean packFrame = false;
  static Frame frame;
  //�A�v���P�[�V�����̃r���h
  public PSDEditor() {
    Splash Splash = new Splash();//�X�v���b�V��
    Splash.wshow();//�\��
    frame = new Frame();
    //validate() �̓T�C�Y�𒲐�����
    //pack() �͗L���ȃT�C�Y�������C�A�E�g�Ȃǂ���擾����
    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    //�E�B���h�E�𒆉��ɔz�u
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    //���C���E�C���h�E���J������Splash����������
    Splash.dispose();
    Splash = null;

    frame.setVisible(true);
  }
  //Main ���\�b�h
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    new PSDEditor();
    if(args.length > 0){
      frame.OpenFile(args[0]);
    }
  }
}
