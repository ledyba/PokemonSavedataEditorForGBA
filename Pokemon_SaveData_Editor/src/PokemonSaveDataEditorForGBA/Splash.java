package PokemonSaveDataEditorForGBA;

import java.awt.*;
import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;

/**
 * <p>�^�C�g��: �A�[�X�J����p���W�ϊ��v���O����</p>
 *
 * <p>����: ���W�̕\����ϊ����܂�</p>
 *
 * <p>���쌠: Copyright (c) 2005 ������</p>
 *
 * <p>��Ж�: </p>
 *
 * @author ������
 * @version 1.0
 */
public class Splash
    extends JWindow{
  JLabel Image1 = new JLabel();
  ImageIcon icon = new ImageIcon(PokemonSaveDataEditorForGBA.Splash.class.getResource("icon.png"));
  //Image winIcon = Toolkit.getDefaultToolkit().createImage(EAC.MainFrame.class.getResource("icon.png"));
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel Title = new JLabel();
  JLabel NowLoadingLabel = new JLabel();
  JPanel ContentPane = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();

  public Splash() {
    try {
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
//    this.setAlwaysOnTop(true); //JBuilder�͂܂�5.0����Ȃ��̂�^^;
    Image1.setIcon(icon);
    Image1.setText("Icon");
    //setIconImage(winIcon);
    getContentPane().setLayout(gridBagLayout1);
    Image1.setText("");
    //this.setResizable(false);
    Title.setFont(new java.awt.Font("Dialog", Font.BOLD, 15));
    Title.setText("Pokemon Save Data Editor for GBA");
    NowLoadingLabel.setText("Now Loading...");
    ContentPane.setLayout(gridBagLayout2);
    ContentPane.add(Image1, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    ContentPane.add(Title, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0, 10, 0, 0), 0, 0));
    ContentPane.add(NowLoadingLabel,
                    new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.NONE,
                                           new Insets(0, 10, 0, 0), 0, 0));
    this.getContentPane().add(ContentPane,
                              new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(20, 20, 20, 20), 0, 0));

  }
  public void wshow(){
      //�E�B���h�E�𒆉��ɔz�u
      this.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension thisSize = this.getSize();
      if (thisSize.height > screenSize.height) {
        thisSize.height = screenSize.height;
      }
      if (thisSize.width > screenSize.width) {
        thisSize.width = screenSize.width;
      }
      this.setLocation((screenSize.width - thisSize.width) / 2, (screenSize.height - thisSize.height) / 2);
      this.setVisible(true);
  }
}
