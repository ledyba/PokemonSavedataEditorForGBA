package PokemonSaveDataEditorForGBA;

import java.awt.*;
import javax.swing.*;
import PokemonSaveDataEditorForGBA.data.SaveFileData;
import PokemonSaveDataEditorForGBA.data.cheat.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.datatransfer.Clipboard;

/**
 * <p>タイトル: ポケモンセーブデータエディタ for GBA</p>
 *
 * <p>説明: </p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author PSI
 * @version 1.0
 */
public class CreateCheatDialog
    extends JDialog {
  public CreateCheatDialog() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
//  private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  SaveFileData Data;
  PokemonSaveDataEditorForGBA.Frame owner;
  JPanel CodePanel = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JScrollPane CodeScrollPane = new JScrollPane();
  JTextArea CodeArea = new JTextArea();
  JPanel MenuPanel = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel ModePanel = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JLabel ModeLabel = new JLabel();
  ButtonGroup ModeRadioGroup = new ButtonGroup();
  JRadioButton ModeRadioNO = new JRadioButton();
  JRadioButton ModeRadioON = new JRadioButton();
  TitledBorder titledBorder1 = new TitledBorder("");
  JPanel BytesPanel = new JPanel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JLabel TextLabel2 = new JLabel();
  JTextField BytesTextField = new JTextField();
  JLabel Byte_Label = new JLabel();
  JPanel ButtonsPanel = new JPanel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JButton MakeCodeButton = new JButton();
  JButton CleateCodeButton = new JButton();
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  JLabel CodeStyleLabel = new JLabel();
   ButtonGroup StyleRadioGroup = new ButtonGroup();
  JRadioButton StyleBothRadio = new JRadioButton();
  JRadioButton StyleOldRadio = new JRadioButton();
  JRadioButton StyleNewRadio = new JRadioButton();
  public CreateCheatDialog(PokemonSaveDataEditorForGBA.Frame owner, String title, boolean modal,SaveFileData Data) {
    super(owner, title, modal);
    this.Data = Data;
    this.owner = owner;
    try {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      jbInit();
      pack();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public CreateCheatDialog(PokemonSaveDataEditorForGBA.Frame Frame,SaveFileData Data) {
    this(Frame, "チートコードの作成", false,Data);
  }

  public void setVisible(boolean flag){
    if(flag){
      this.setPreferredSize(new Dimension(400,350));
      this.setSize(this.getPreferredSize());
      Dimension dlgSize = this.getPreferredSize();
      Dimension frmSize = owner.getSize();
//      System.out.println(dlgSize.toString());
//      System.out.println(frmSize.toString());
      Point loc = owner.getLocation();
      this.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                       (frmSize.height - dlgSize.height) / 2 + loc.y);
      this.setModal(true);
      super.setVisible(true);
    }else{
      super.setVisible(false);
    }
  }

  private void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    CodePanel.setLayout(borderLayout2);
    CodeArea.setText(";作成されたコード");
    MenuPanel.setLayout(gridBagLayout1);
    MenuPanel.setInputVerifier(null);
    ModePanel.setLayout(gridBagLayout2);
    ModePanel.setBorder(titledBorder1);
    ModePanel.setToolTipText("");
    ModePanel.setInputVerifier(null);
    ModeLabel.setText("作成モード指定");
    ModeRadioNO.setActionCommand("NO");
    ModeRadioNO.setText("新→旧の差分作成");
    ModeRadioON.setActionCommand("ON");
    ModeRadioON.setText("旧→新の差分作成");
    CleateCodeButton.addActionListener(new
        CreateCheatDialog_CleateCodeButton_actionAdapter(this));
    jPanel1.setBorder(titledBorder1);
    jPanel1.setLayout(gridBagLayout5);
    CodeStyleLabel.setText("コードの形式の指定");
    StyleBothRadio.setActionCommand("both");
    StyleBothRadio.setSelected(true);
    StyleBothRadio.setText("\"both\"：新旧両方");
    StyleOldRadio.setActionCommand("old");
    StyleOldRadio.setText("\"old\"：旧セーブのみ");
    StyleNewRadio.setActionCommand("new");
    StyleNewRadio.setText("\"new\"：新セーブのみ");
    MakeCodeButton.addActionListener(new
        CreateCheatDialog_MakeCodeButton_actionAdapter(this));
    StyleRadioGroup.add(StyleBothRadio);
    StyleRadioGroup.add(StyleOldRadio);
    StyleRadioGroup.add(StyleNewRadio);
    ModeRadioGroup.add(ModeRadioNO);
    ModeRadioGroup.add(ModeRadioON);
    ModeRadioON.setSelected(true);
    BytesPanel.setBorder(titledBorder1);
    BytesPanel.setLayout(gridBagLayout3);
    TextLabel2.setText("一行のバイト数");
    BytesTextField.setText("4");
    Byte_Label.setText("バイト");
    ButtonsPanel.setBorder(titledBorder1);
    ButtonsPanel.setLayout(gridBagLayout4);
    MakeCodeButton.setText("コード作成");
    CleateCodeButton.setText("クリア");
    getContentPane().add(panel1);
    CodePanel.add(CodeScrollPane, java.awt.BorderLayout.CENTER);
    panel1.add(MenuPanel, java.awt.BorderLayout.WEST);
    CodeScrollPane.getViewport().add(CodeArea);
    CodeArea.addMouseListener(new psi.lib.Swing.PopupRightClick(CodeArea));
    ModePanel.add(ModeLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(CodePanel, java.awt.BorderLayout.CENTER);
    MenuPanel.add(BytesPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.NORTH, GridBagConstraints.BOTH,
        new Insets(0, 5, 5, 5), 0, 0));
    MenuPanel.add(ButtonsPanel, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, 5, 5, 5), 0, 0));
    MenuPanel.add(ModePanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.NORTH, GridBagConstraints.BOTH,
        new Insets(5, 5, 5, 5), 0, 0));
    MenuPanel.add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.BOTH,
                                                  new Insets(0, 5, 5, 5), 0, 0));
    ModePanel.add(ModeRadioNO, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    ModePanel.add(ModeRadioON, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(CodeStyleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(StyleBothRadio, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(StyleOldRadio, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(StyleNewRadio, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    BytesPanel.add(TextLabel2, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    BytesPanel.add(BytesTextField, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(0, 0, 0, 0), 0, 0));
    BytesPanel.add(Byte_Label, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 5, 0, 0), 0, 0));
    ButtonsPanel.add(MakeCodeButton,
                     new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(5, 5, 5, 5), 0, 0));
    ButtonsPanel.add(CleateCodeButton,
                     new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 5, 5, 5), 0, 0));
  }
  //[クリア]
  public void CleateCodeButton_actionPerformed(ActionEvent e) {
    this.CodeArea.setText("");
  }
  //[コード作成]
  public void MakeCodeButton_actionPerformed(ActionEvent e) {
    String ModeString = this.ModeRadioGroup.getSelection().getActionCommand();
    int Mode = 0;
    if(ModeString.equals("ON")){
      Mode = CreateCheat.MODE_OLD_NEW;
    }else if(ModeString.equals("NO")){
      Mode = CreateCheat.MODE_NEW_OLD;
    }
    int Style = CheatCode.getModeCode(this.StyleRadioGroup.getSelection().getActionCommand());
    int line = Integer.parseInt(BytesTextField.getText());
    CreateCheat cheat = new CreateCheat(Data,Mode,Style,line,line);
    CheatCode[] CodeList = cheat.CreateCheatCode();
    this.CodeArea.setText("");
    for(int i=0;i<CodeList.length;i++){
      this.CodeArea.append(CodeList[i].toString()+"\n");
    }
  }
  //[コピー]
  public void CopyButton_actionPerformed(ActionEvent e) {

  }
}

class CreateCheatDialog_CopyButton_actionAdapter
    implements ActionListener {
  private CreateCheatDialog adaptee;
  CreateCheatDialog_CopyButton_actionAdapter(CreateCheatDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.CopyButton_actionPerformed(e);
  }
}

class CreateCheatDialog_MakeCodeButton_actionAdapter
    implements ActionListener {
  private CreateCheatDialog adaptee;
  CreateCheatDialog_MakeCodeButton_actionAdapter(CreateCheatDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.MakeCodeButton_actionPerformed(e);
  }
}

class CreateCheatDialog_CleateCodeButton_actionAdapter
    implements ActionListener {
  private CreateCheatDialog adaptee;
  CreateCheatDialog_CleateCodeButton_actionAdapter(CreateCheatDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {

    adaptee.CleateCodeButton_actionPerformed(e);
  }
}
