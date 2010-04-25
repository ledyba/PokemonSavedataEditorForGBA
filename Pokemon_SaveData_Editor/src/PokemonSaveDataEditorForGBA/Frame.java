package PokemonSaveDataEditorForGBA;

import java.io.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.tree.*;

import PokemonSaveDataEditorForGBA.data.*;
import PokemonSaveDataEditorForGBA.data.RW.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import psi.lib.CalTools.PokeTools;

/**
 * <p>タイトル: ポケモン　セーブデータエディタ</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2005 PSI</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */

public class Frame
    extends JFrame {
  /*
   自分で定義したもの
   */
  TableModel Hex_Model; //JListのモデル
  SaveFileData Data;
  BlockData SelectedBlock;
  String FileName = "";
  public static final String DefaultTitle = "ポケモンセーブデータエディタ for GBA";
  boolean isPokemonCode; //ポケモンコードで読むか否か
  boolean isFileOpen = false; //ファイルを開いているか否か
//  boolean isChanged = false; //ファイルに手が加えられたか否か
  final static int column_size = 58; //オフセット・アドレス用の行サイズ
  public static final String default_column[] = {
      "Address", "Offset", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
      "a", "b", "c", "d", "e", "f"};
  DefaultTreeModel TreeModel;
  JPanel contentPane;
  JMenuBar MainMenuBar = new JMenuBar();
  JMenu MainMenu_File = new JMenu();
  JMenuItem MainMenu_File_Exit = new JMenuItem();
  JMenu MainMenu_Help = new JMenu();
  JMenuItem MainMenu_Help_About = new JMenuItem();
  public static final Image WinIcon = Toolkit.getDefaultToolkit().createImage(
      PokemonSaveDataEditorForGBA.
      Frame.class.getResource("icon.png")); //アイコン

  BorderLayout borderLayout1 = new BorderLayout();
  JSplitPane Main_Panel = new JSplitPane();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel List_Panel = new JPanel();
  JPanel EditPanels = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JScrollPane EditScrollPane = new JScrollPane();
  JTable Hex_Area; //中身は別メソッドで表示
  JScrollPane BlockListScrollPane = new JScrollPane();
  JPanel Block_Imfomation = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  TitledBorder titledBorder1;
  TitledBorder titledBorder2;
  TitledBorder titledBorder3;
  TitledBorder titledBorder4;
  JPanel Size_and_Add_Panel = new JPanel();
  JLabel BlockSize_Label = new JLabel();
  JPanel BlockSize_Panel = new JPanel();
  JTextField BlockSize_Field = new JTextField();
  JLabel StartAdd_Label = new JLabel();
  BorderLayout borderLayout6 = new BorderLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  JTextField StartAdd_Field = new JTextField();
  JPanel StartAdd_Panel = new JPanel();
  JPanel BlockNamePanel = new JPanel();
  JLabel Block_Name = new JLabel();
  BorderLayout borderLayout7 = new BorderLayout();
  JTextField Block_Name_Field = new JTextField();
  JPanel Block_Name_Panel = new JPanel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JPanel CheckSum_Report = new JPanel();
  JLabel CheckSum_Label = new JLabel();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  BorderLayout borderLayout8 = new BorderLayout();
  JPanel Checksum_Panel = new JPanel();
  JTextField CheckSum_Field = new JTextField();
  JLabel Report_Label = new JLabel();
  BorderLayout borderLayout9 = new BorderLayout();
  JTextField Report_Field = new JTextField();
  JPanel Report_Panel = new JPanel();
  JMenuItem MainMenu_File_Open = new JMenuItem();
  JMenu Save = new JMenu();
  JMenuItem save = new JMenuItem();
  JMenuItem save_as = new JMenuItem();
  static JLabel statusBar = new JLabel();
  JTree BlockTree;
  javax.swing.JMenu ViewMenu = new JMenu();
  javax.swing.JCheckBoxMenuItem isPokemonCode_Menu = new JCheckBoxMenuItem();
  javax.swing.JMenu AnalysisMenu = new JMenu();
  javax.swing.JMenuItem CheatMenu = new JMenuItem();
  javax.swing.JMenuItem CalChecksumMenu = new JMenuItem();
  javax.swing.JMenuItem CreateCheatMenu = new JMenuItem();

  public Frame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
      setDropTarget();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * setDropTarget
   * ドロップのための．
   */
  private void setDropTarget() {
    DropTargetListener dtl = new CustomizedDropTargetAdapter();
    DropTarget dt = new DropTarget(this.Hex_Area, DnDConstants.ACTION_COPY, dtl, true);
    DropTarget dt2 = new DropTarget(this.BlockTree, DnDConstants.ACTION_COPY, dtl, true);
  }
  //ドロップ
  class CustomizedDropTargetAdapter extends DropTargetAdapter {
    public void dragOver(DropTargetDragEvent dtde) {
      if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
        dtde.acceptDrag(DnDConstants.ACTION_COPY);
        return;
      }
      dtde.rejectDrag();
    }

    public void drop(DropTargetDropEvent dtde) {
      try {
        if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
          dtde.acceptDrop(DnDConstants.ACTION_COPY);
          Transferable t = dtde.getTransferable();
          java.util.List list = (java.util.List) t.getTransferData(
              DataFlavor.javaFileListFlavor);
          java.util.Iterator i = list.iterator();
//          while (i.hasNext()) {
            File file = (File) i.next();
            OpenFile(file.getPath());
//          }
          dtde.dropComplete(true);
          return;
        }
      }
      catch (UnsupportedFlavorException ufe) {
        ufe.printStackTrace();
      }
      catch (IOException ioe) {
        ioe.printStackTrace();
      }
      dtde.rejectDrop();
    }
  }

  //コンポーネントの初期化
  private void jbInit() throws Exception {
    /*デフォルトデータの流し込み*/
    Data = new SaveFileData();
    SelectedBlock = Data.getSave(0).getBlockData(0);
    /*アイコン設定*/
    this.setIconImage(WinIcon);
    contentPane = (JPanel)this.getContentPane();
    /*EditAreaとTreeの初期化*/
    EditArea_Start();
    TreeStart();
    Hex_Area.getTableHeader().setReorderingAllowed(false); // ドラッグは無効
    titledBorder1 = new TitledBorder("");
    titledBorder2 = new TitledBorder("");
    titledBorder3 = new TitledBorder("");
    titledBorder4 = new TitledBorder("");
    contentPane.setLayout(borderLayout1);
    this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    this.setLocale(java.util.Locale.getDefault());
    this.setSize(new Dimension(591, 330));
    this.setState(Frame.NORMAL);
    this.setTitle(DefaultTitle);
    MainMenu_File.setText("ファイル");
    MainMenu_File_Exit.setText("終了");
    MainMenu_File_Exit.addActionListener(new
                                         Frame_MainMenu_File_Exit_ActionAdapter(this));
    MainMenu_Help_About.setText("バージョン情報");
    MainMenu_Help_About.addActionListener(new
                                          Frame_MainMenu_Help_About_ActionAdapter(this));
    List_Panel.setLayout(borderLayout4);
    EditPanels.setMaximumSize(new Dimension(32767, 32767));
    EditPanels.setLayout(borderLayout3);
    Block_Imfomation.setLayout(gridBagLayout1);
    BlockSize_Label.setText("ブロックサイズ");
    BlockSize_Panel.setLayout(borderLayout6);
    BlockSize_Field.setEditable(false);
//    BlockSize_Field.setText("0x00000000");
    StartAdd_Label.setVerifyInputWhenFocusTarget(true);
    StartAdd_Label.setText("開始アドレス");
    Size_and_Add_Panel.setLayout(gridBagLayout3);
    StartAdd_Field.setToolTipText("");
    StartAdd_Field.setEditable(false);
//    StartAdd_Field.setText("0x00000000");
    StartAdd_Panel.setLayout(borderLayout2);
    Block_Name.setVerifyInputWhenFocusTarget(true);
    Block_Name.setText("ブロック名");
    Block_Name_Field.setEditable(false);
//    Block_Name_Field.setText("ブロック名");
    Block_Name_Panel.setLayout(borderLayout7);
    BlockNamePanel.setLayout(gridBagLayout4);
    CheckSum_Label.setText("チェックサム");
    CheckSum_Report.setOpaque(true);
    CheckSum_Report.setRequestFocusEnabled(true);
    CheckSum_Report.setLayout(gridBagLayout5);
    Checksum_Panel.setLayout(borderLayout8);
    CheckSum_Field.setEnabled(true);
    CheckSum_Field.setEditable(false);
    CheckSum_Field.setSelectionStart(10);
//    CheckSum_Field.setText("0x0000");
    Report_Label.setRequestFocusEnabled(true);
    Report_Label.setText("レポート回数");
    Report_Field.setEditable(false);
//    Report_Field.setText("0x0000 (= 0 )");
    Report_Panel.setLayout(borderLayout9);
    MainMenu_File_Open.setText("セーブデータを開く");
    MainMenu_File_Open.addActionListener(new
                                         Frame_MainMenu_File_Open_actionAdapter(this));
    Save.setText("保存");
    save.setText("上書き保存");
    save.addActionListener(new Frame_save_actionAdapter(this));
    save_as.setText("新規保存");
    save_as.addActionListener(new Frame_save_as_actionAdapter(this));
    statusBar.setText("status");
    ViewMenu.setText("表示設定");
    MainMenu_Help.setText("ヘルプ");
    isPokemonCode_Menu.setText("ポケモンの文字コードで表示");
    isPokemonCode_Menu.addActionListener(new
                                         Frame_isPokemonCode_Menu_actionAdapter(this));
    AnalysisMenu.setText("解析");
    CheatMenu.setText("チート");
    CreateCheatMenu.setText("チートコード作成");
    CreateCheatMenu.addActionListener(new Frame_CreateCheatMenu_actionAdapter(this));
    CheatMenu.addActionListener(new Frame_CheatMenu_actionAdapter(this));
    CalChecksumMenu.setText("チェックサムの再計算");
    CalChecksumMenu.addActionListener(new Frame_CalChecksumMenu_actionAdapter(this));
    MainMenu_File.add(MainMenu_File_Open);
    MainMenu_File.add(Save);
    MainMenu_File.addSeparator();
    MainMenu_File.add(MainMenu_File_Exit);
    MainMenu_Help.add(MainMenu_Help_About);
    MainMenuBar.add(MainMenu_File);
    MainMenuBar.add(ViewMenu);
    MainMenuBar.add(AnalysisMenu);
    MainMenuBar.add(MainMenu_Help);
    this.setJMenuBar(MainMenuBar);
    contentPane.add(Main_Panel, BorderLayout.CENTER);
    Main_Panel.add(EditPanels, JSplitPane.RIGHT, 0);
    Main_Panel.add(List_Panel, JSplitPane.LEFT, 0);
    Main_Panel.setDividerSize(3);
//    Main_Panel.setOneTouchExpandable(true);
    EditPanels.add(EditScrollPane, BorderLayout.CENTER);
    EditPanels.add(Block_Imfomation, BorderLayout.NORTH);
    EditScrollPane.getViewport().add(Hex_Area, null);
    List_Panel.add(BlockListScrollPane, BorderLayout.CENTER);
    BlockListScrollPane.getViewport().add(BlockTree);
    Block_Imfomation.add(Size_and_Add_Panel,
                         new GridBagConstraints(0, 1, 1, 2, 1.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 0), 0, 0));
    Size_and_Add_Panel.add(BlockSize_Panel,
                           new GridBagConstraints(3, 1, 1, 1, 1.0, 1.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.BOTH,
                                                  new Insets(0, 0, 0, 0), 0, 0));
    BlockSize_Panel.add(BlockSize_Field, BorderLayout.CENTER);
    Size_and_Add_Panel.add(BlockSize_Label,
                           new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0
                                                  ,
                                                  GridBagConstraints.SOUTHWEST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(5, 5, 5, 5), 0, 0));
    Size_and_Add_Panel.add(StartAdd_Label,
                           new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.SOUTH,
                                                  GridBagConstraints.NONE,
                                                  new Insets(5, 5, 5, 5), 0, 0));
    Size_and_Add_Panel.add(StartAdd_Panel,
                           new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.BOTH,
                                                  new Insets(0, 0, 0, 0), 0, 0));
    StartAdd_Panel.add(StartAdd_Field, BorderLayout.CENTER);
    Block_Imfomation.add(BlockNamePanel,
                         new GridBagConstraints(0, 0, 3, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 0), 0, 0));
    BlockNamePanel.add(Block_Name,
                       new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.WEST,
                                              GridBagConstraints.NONE,
                                              new Insets(5, 5, 5, 5), 22, 0));
    BlockNamePanel.add(Block_Name_Panel,
                       new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.BOTH,
                                              new Insets(0, 0, 0, 0), 0, 0));
    Block_Name_Panel.add(Block_Name_Field, BorderLayout.CENTER);
    Block_Imfomation.add(CheckSum_Report,
                         new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 0), 0, 0));
    CheckSum_Report.add(CheckSum_Label,
                        new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST,
                                               GridBagConstraints.NONE,
                                               new Insets(5, 5, 5, 5), 0, 0));
    CheckSum_Report.add(Checksum_Panel,
                        new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.BOTH,
                                               new Insets(0, 0, 0, 0), 0, 0));
    Checksum_Panel.add(CheckSum_Field, BorderLayout.CENTER);
    CheckSum_Report.add(Report_Label,
                        new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.NONE,
                                               new Insets(5, 5, 5, 5), 0, 0));
    CheckSum_Report.add(Report_Panel,
                        new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.BOTH,
                                               new Insets(0, 0, 0, 0), 0, 0));
    Report_Panel.add(Report_Field, BorderLayout.CENTER);
    Save.add(save);
    Save.add(save_as);
    contentPane.add(statusBar, BorderLayout.SOUTH);
    ViewMenu.add(isPokemonCode_Menu);
    AnalysisMenu.add(CheatMenu);
    AnalysisMenu.add(CreateCheatMenu);
    AnalysisMenu.add(CalChecksumMenu);
    //Splitの切れ目を設定
    Main_Panel.setDividerLocation( (int) (List_Panel.getPreferredSize().
                                          getWidth() + 10));
    this.doLayout();
  }

  public static void setStatus(String str) { //ステータスバーへの書込み
    statusBar.setText(str);
  }

  /**
   * ブロックを選択する
   * @param SelectedBlock BlockData
   */

  public void setSelectedBlock(BlockData SelectedBlock) {
    this.SelectedBlock = SelectedBlock;
    this.imformation_set();
    this.EditArea_Rewrite();
  }

  /**
   * 再描写
   */
  public void rewrite() {
    this.EditArea_Rewrite();
    this.TreeRewrite();
    this.repaint();
  }

  public void TreeStart() {
    CustomizedTreeNode root = new CustomizedTreeNode("ファイル");
    TreeModel = new CustomizedTreeModel(root);
    for (int i = 0; i < SaveFileData.SAVES; i++) {
      CustomizedTreeNode tmp = Data.getSave(i).getNode();
      root.add(tmp);
      for (int j = 0; j < Data.getSave(i).getBlocks(); j++) {
        CustomizedTreeNode tmp2 = Data.getSave(i).getBlockData(j).getNode();
        tmp.add(tmp2);
      }
    }
    BlockTree = new JTree(TreeModel);
    BlockTree.addMouseListener(new PopupMouseListener(this));
    BlockTree.setRootVisible(false);
    BlockTree.addTreeSelectionListener(new CustomizedTreeSelectionListener(this));
  }

  private void TreeRewrite() {
    CustomizedTreeNode root = (CustomizedTreeNode) BlockTree.getModel().
        getRoot();
//    this.setTitle(DefaultTitle + " - " + (new File(FileName)).getName());
    root.removeAllChildren();
    for (int i = 0; i < SaveFileData.SAVES; i++) {
      CustomizedTreeNode tmp = Data.getSave(i).getNode();
      root.add(tmp);
      for (int j = 0; j < Data.getSave(i).getBlocks(); j++) {
        CustomizedTreeNode tmp2 = Data.getSave(i).getBlockData(j).getNode();
        tmp.add(tmp2);
      }
    }
    TreeModel.reload();
    BlockTree.repaint();
  }

  public void EditArea_Start() { //エディットエリアの初期化
    Hex_Model = new Hex_Model(this);
    Hex_Area = new JTable(Hex_Model);
    Hex_Area.setDefaultRenderer(Object.class, new CellRenderer(this));
    Hex_Area.setRowSelectionAllowed(true);
    Hex_Area.setColumnSelectionAllowed(true);
    /*Hex_Area.setRowSelectionAllowed(false);
         Hex_Area.setColumnSelectionAllowed(false);*/
    Hex_Area.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    //Hex_Area = new JTable(default_data,default_column);
    EditArea_ColumnSet();
    this.imformation_set(); //情報設定
  }

  /**
   * EditAreaの際描写
   */
  private void EditArea_Rewrite() {
    //作り直さないと，最終ブロックをうまく表示できない
    Hex_Model = new Hex_Model(this);
    Hex_Area.setModel(Hex_Model);
    Hex_Area.repaint();
    EditArea_ColumnSet();
    imformation_set();
  }

  void EditArea_ColumnSet() {
    //「アドレス」，「オフセット」カラムはサイズを広げる
    TableColumn Add_Column = Hex_Area.getColumn("Address");
    Add_Column.setMaxWidth(column_size);
    Add_Column.setMinWidth(column_size);
    Add_Column.setPreferredWidth(column_size);
    Hex_Area.sizeColumnsToFit(0); //サイズを変えたことを認識させ最描写

    TableColumn Off_Column = Hex_Area.getColumn("Offset");
    Off_Column.setMaxWidth(column_size);
    Off_Column.setMinWidth(column_size);
    Off_Column.setPreferredWidth(column_size);
    Hex_Area.sizeColumnsToFit(0); //サイズを変えたことを認識させ最描写
  }

  //ブロックデータの表示
  void imformation_set() {
    //ブロック名
    if (SelectedBlock.getSaveCount() >= 0) {
      Block_Name_Field.setText("第" + SelectedBlock.getSaveCount() +
                               "回セーブデータ　第" +
                               PokeTools.space(Integer.toHexString(SelectedBlock.
          getBlockNumber()), 2, "0") + "ブロック");
    }
    else {
      Block_Name_Field.setText("最終データ　第" + SelectedBlock.getBlockNumber() +
                               "ブロック");
    }
    //ブロックサイズ
    BlockSize_Field.setText
        ("0x" +
         PokeTools.space(Integer.toHexString(SelectedBlock.getLength()), 8, "0"));
    //スタートアドレス
    StartAdd_Field.setText
        ("0x" +
         PokeTools.space(Long.toHexString(SelectedBlock.getAddr()), 8, "0"));
    //チェックサム
    CheckSum_Field.setText("0x" + SelectedBlock.getChecksum());
    //レポート回数
    if (SelectedBlock.getSaveCount() >= 0) {
      Report_Field.setText("0x" + SelectedBlock.getSaveCountHex() + " = (" +
                           SelectedBlock.getSaveCount() + ")");
    }
    else {
      Report_Field.setText("0x" + SelectedBlock.getSaveCountHex() +
                           " = (****)");
    }
  }

  //[ファイル|終了]　終了時に呼ばれる
  public void MainMenu_File_Exit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  //[ヘルプ|バージョン情報]
  public void MainMenu_Help_About_actionPerformed(ActionEvent e) {
    Frame_AboutBox dlg = new Frame_AboutBox(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                    (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.pack();
    dlg.setVisible(true);
  }

  //ウィンドウが閉じられたときに終了するようにオーバーライド
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      /*if(this.isChanged){
        SaveDlg SaveDlg = new SaveDlg(this.Data, FileName, this,
                                      "変更されています．上書き保存しますか？");
             }*/
      MainMenu_File_Exit_actionPerformed(null);
    }
  }

  void MainMenu_File_Open_actionPerformed(ActionEvent e) {
    /*if(isChanged){
      SaveDlg SaveDlg = new SaveDlg(this.Data, FileName, this,
                                    "変更されています．上書き保存しますか？");
         }*/
    //[ファイル/セーブデータを開く]
    JFileChooser open_chooser = new JFileChooser(new File(this.FileName).
                                                 getPath());
    int selected = open_chooser.showOpenDialog(this);
    if (selected == JFileChooser.APPROVE_OPTION) { //「開く」が押されていたら
      OpenFile(open_chooser.getSelectedFile().getPath());
    }
  }

  public void OpenFile(String FileName) {
    FileRead FR = new FileRead(FileName);
    SaveFileData tmpData = FR.read();
    if (tmpData != null) { //読み込みに成功した
      this.FileName = FileName; //関連付けの時のため
      isFileOpen = true;
      this.Data = tmpData;
//      this.isChanged = false; //チェンジフラッグも戻す
      //新しいほうの0ブロックを自動的に開く
      Save tmp = Data.getSave(true);
      this.SelectedBlock = tmp.getBlockData(tmp.getBlockNumberInOrder(0));
      //画面再描写
      this.setTitle(DefaultTitle + " - " + (new File(FileName)).getName());
      this.TreeRewrite();
      this.EditArea_Rewrite();
    }
  }

  /*  public void dataChanged(){
      //チェックが面倒なので何もしないことにしました
      if(!this.isChanged && this.isFileOpen){
        this.isChanged = true;
        this.setTitle(this.getTitle() + " *");
      }
    }*/
  class Hex_Model
      extends AbstractTableModel {
    PokemonSaveDataEditorForGBA.Frame Frame;
    public Hex_Model(PokemonSaveDataEditorForGBA.Frame Frame) {
      this.Frame = Frame;
    }

    public int getColumnCount() {
      return 16 + 2;
    }

    public int getRowCount() {
      return SelectedBlock.getLength() / 16;
    }

    public Object getValueAt(int row, int column) {
      if (column == 0) { //アドレス
        return PokeTools.space(Long.toHexString(SelectedBlock.getAddr() +
                                            0x10 * row), 8, "0");
      }
      else if (column == 1) { //オフセット
        return PokeTools.space(Integer.toHexString(0x10 * row), 8, "0");
      }
      else {
        //値
        if (isPokemonCode) {
          return PokeTools.toPokemonCode(SelectedBlock.getByteArray()[row * 0x10 +
                                     column - 2]);
        }
        else {
          return PokeTools.space(Integer.toHexString(PokeTools.ByteToInt(
              SelectedBlock.
              getByteArray()[row * 0x10 + column - 2])), 2, "0");
        }
      }
    }

    public String getColumnName(int column_count) {
      return default_column[column_count];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
      if (Frame.isPokemonCode || !Frame.isFileOpen) { //ポケモンコード時・開いていないときは編集させない
        return false;
      }
      String tmp = Frame.SelectedBlock.getCellText(rowIndex, columnIndex);
      if (tmp != null) { //nullでない＝フッタである
        Frame.setStatus(tmp);
        return false;
      }
//      statusSet("　");//リセット
      //オフセットとアドレスは編集不可能
      if (columnIndex <= 1) {
        return (false);
      }
      else {
        return (true);
      }
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      //ここに「16進でなければ書き込めない」処理
      try {
        if ( ( (String) aValue).length() == 2) { //2文字ならば
          Integer.parseInt( (String) aValue, 16); //変換させてみて・・・
          byte older = SelectedBlock.getByte(rowIndex * 0x10 + columnIndex -
                                             2);
          byte newer = PokeTools.IntToByte(Integer.parseInt( (String) aValue, 16));
          if (older != newer) {
//            dataChanged();
            SelectedBlock.setByte(newer, rowIndex * 0x10 + columnIndex - 2);
            Frame.setStatus("編集完了");
          }
        }
        else {
          Frame.setStatus("エラー！：2ケタの16進数を入力してください");
        }
      }
      catch (NumberFormatException e) { //16進数に変換できない！
        Frame.setStatus("エラー！：その数は16進数ではありません");
      }
      fireTableDataChanged();
    }
  }

  /**
   * 上書き保存
   * @param e ActionEvent
   */
  void save_actionPerformed(ActionEvent e) {
    if (isFileOpen) { //ファイルを開いているか
      SaveDlg_Show(FileName);
    }
    else {
      setStatus("エラー！：まずはファイルを開いて下さい");
    }
  }

  void SaveDlg_Show(String filename) {
    SaveDlg SaveDlg = new SaveDlg(Data, filename, this, "上書きしてしまいます．よろしいですか？");
    SaveDlg.setVisible(true);
  }

  void save_as_actionPerformed(ActionEvent e) { //「再構築して新規保存」
    //[ファイル/保存/再構築して新規保存]
    if (isFileOpen) { //ファイルを開いているか
      JFileChooser save_chooser = new JFileChooser(new File(this.FileName).
          getPath());
      int selected = save_chooser.showSaveDialog(this);
      if (selected == JFileChooser.APPROVE_OPTION) { //「保存」が押されていたら
        String tmpFileName = save_chooser.getSelectedFile().getPath(); //書き込みファイル名の取得
        File File = new File(tmpFileName);
        if (File.canRead()) { //ファイルが読み込める＝存在するなら，上書きになってしまう
          SaveDlg_Show(tmpFileName);
        }
        else { //でなければ普通に書き込み
          this.FileName = tmpFileName;
          FileWrite FW = new FileWrite(tmpFileName, Data);
          FW.write();
        }
        this.TreeRewrite();
        this.EditArea_Rewrite();
      }
    }
    else {
      setStatus("エラー！：まずはファイルを開いて下さい");
    }
  }

  public void setFileName(String name) {
    this.FileName = name;
  }

  public TableModel getTableModel() {
    return this.Hex_Model;
  }

  public JTable getTable() {
    return this.Hex_Area;
  }

  //ポケモンコードで表示
  public void isPokemonCode_Menu_actionPerformed(ActionEvent e) {
    this.isPokemonCode = isPokemonCode_Menu.getState();
    this.EditArea_Rewrite(); //再描写
  }

  /**
   * チェックサムの再計算
   * @param e ActionEvent
   */
  public void CalChecksumMenu_actionPerformed(ActionEvent e) {
    if (isFileOpen) { //ファイルを開いているか
      this.Data.calcCheckSum();
      this.setStatus("チェックサムの再計算を行いました．");
      this.EditArea_Rewrite();
    }
    else {
      setStatus("エラー！：まずはファイルを開いて下さい");
    }
  }

  /**
   * チートメニューを開く
   * @param e ActionEvent
   */
  public void CheatMenu_actionPerformed(ActionEvent e) {
    if (isFileOpen) { //ファイルを開いているか
      CheatDialog Dlg = new CheatDialog(this, this.Data);
      Dlg.setVisible(true);
    }
    else {
      setStatus("エラー！：まずはファイルを開いて下さい");
    }
  }

  //チートコード作成
  public void CreateCheatMenu_actionPerformed(ActionEvent e) {
    if (isFileOpen) { //ファイルを開いているか
      CreateCheatDialog Dlg = new CreateCheatDialog(this, this.Data);
      Dlg.setVisible(true);
    }
    else {
      setStatus("エラー！：まずはファイルを開いて下さい");
    }
  }
}

class Frame_CreateCheatMenu_actionAdapter
    implements ActionListener {
  private Frame adaptee;
  Frame_CreateCheatMenu_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.CreateCheatMenu_actionPerformed(e);
  }
}

class Frame_CalChecksumMenu_actionAdapter
    implements ActionListener {
  private Frame adaptee;
  Frame_CalChecksumMenu_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.CalChecksumMenu_actionPerformed(e);
  }
}

class Frame_CheatMenu_actionAdapter
    implements ActionListener {
  private Frame adaptee;
  Frame_CheatMenu_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.CheatMenu_actionPerformed(e);
  }
}

class Frame_isPokemonCode_Menu_actionAdapter
    implements ActionListener {
  private Frame adaptee;
  Frame_isPokemonCode_Menu_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.isPokemonCode_Menu_actionPerformed(e);
  }
}

/**
 *
 * <p>タイトル: セルレンダラ</p>
 *
 * <p>説明: レンダラですってばさ</p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author PSI
 * @version 1.0
 */
class CellRenderer
    extends DefaultTableCellRenderer {
  PokemonSaveDataEditorForGBA.Frame Frame;
  Color backGround;
  public CellRenderer(PokemonSaveDataEditorForGBA.Frame frame) {
    super();
    this.Frame = frame;
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 boolean hasFocus, int row,
                                                 int column) {
    backGround = CellColorRenderer.getBackColor(Frame, row, column,
                                                isSelected);
    /*
         スーパークラスで上書きされるのでこちらも．
         しかし，こちらを入れると再描写でばぐる．
         table.setSelectionBackground(backGround);
     */
//    super.addMouseListener(new PopupRightClick((JLabel)this));//JLabelには無理・・・

    return super.getTableCellRendererComponent(table, value, isSelected,
                                               hasFocus, row, column);
  }

  /**
   * 上記の件には
   * こちらを乗っ取る事で対応
   * @return Color
   */
  public Color getBackground() {
    return backGround;
  }
}

class Frame_MainMenu_File_Exit_ActionAdapter
    implements ActionListener {
  Frame adaptee;

  Frame_MainMenu_File_Exit_ActionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.MainMenu_File_Exit_actionPerformed(e);
  }
}

class Frame_MainMenu_Help_About_ActionAdapter
    implements ActionListener {
  Frame adaptee;

  Frame_MainMenu_Help_About_ActionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.MainMenu_Help_About_actionPerformed(e);
  }
}

class Frame_MainMenu_File_Open_actionAdapter
    implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_MainMenu_File_Open_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.MainMenu_File_Open_actionPerformed(e);
  }
}

class Frame_save_actionAdapter
    implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_save_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.save_actionPerformed(e);
  }
}

class Frame_save_as_actionAdapter
    implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_save_as_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.save_as_actionPerformed(e);
  }
}
