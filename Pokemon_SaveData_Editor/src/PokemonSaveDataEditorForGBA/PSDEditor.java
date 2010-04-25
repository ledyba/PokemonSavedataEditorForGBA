package PokemonSaveDataEditorForGBA;

import javax.swing.UIManager;
import java.awt.*;

/**
 * <p>タイトル: ポケモン　セーブデータエディタ</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2005 PSI</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */

public class PSDEditor {
  boolean packFrame = false;
  static Frame frame;
  //アプリケーションのビルド
  public PSDEditor() {
    Splash Splash = new Splash();//スプラッシュ
    Splash.wshow();//表示
    frame = new Frame();
    //validate() はサイズを調整する
    //pack() は有効なサイズ情報をレイアウトなどから取得する
    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    //ウィンドウを中央に配置
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    //メインウインドウを開いたらSplashも消すこと
    Splash.dispose();
    Splash = null;

    frame.setVisible(true);
  }
  //Main メソッド
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
