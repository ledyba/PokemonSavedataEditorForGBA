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
 * <p>�^�C�g��: �|�P�����@�Z�[�u�f�[�^�G�f�B�^</p>
 * <p>����: </p>
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 * <p>��Ж�: </p>
 * @author ������
 * @version 1.0
 */

public class Frame
    extends JFrame {
  /*
   �����Œ�`��������
   */
  TableModel Hex_Model; //JList�̃��f��
  SaveFileData Data;
  BlockData SelectedBlock;
  String FileName = "";
  public static final String DefaultTitle = "�|�P�����Z�[�u�f�[�^�G�f�B�^ for GBA";
  boolean isPokemonCode; //�|�P�����R�[�h�œǂނ��ۂ�
  boolean isFileOpen = false; //�t�@�C�����J���Ă��邩�ۂ�
//  boolean isChanged = false; //�t�@�C���Ɏ肪������ꂽ���ۂ�
  final static int column_size = 58; //�I�t�Z�b�g�E�A�h���X�p�̍s�T�C�Y
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
      Frame.class.getResource("icon.png")); //�A�C�R��

  BorderLayout borderLayout1 = new BorderLayout();
  JSplitPane Main_Panel = new JSplitPane();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel List_Panel = new JPanel();
  JPanel EditPanels = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JScrollPane EditScrollPane = new JScrollPane();
  JTable Hex_Area; //���g�͕ʃ��\�b�h�ŕ\��
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
   * �h���b�v�̂��߂́D
   */
  private void setDropTarget() {
    DropTargetListener dtl = new CustomizedDropTargetAdapter();
    DropTarget dt = new DropTarget(this.Hex_Area, DnDConstants.ACTION_COPY, dtl, true);
    DropTarget dt2 = new DropTarget(this.BlockTree, DnDConstants.ACTION_COPY, dtl, true);
  }
  //�h���b�v
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

  //�R���|�[�l���g�̏�����
  private void jbInit() throws Exception {
    /*�f�t�H���g�f�[�^�̗�������*/
    Data = new SaveFileData();
    SelectedBlock = Data.getSave(0).getBlockData(0);
    /*�A�C�R���ݒ�*/
    this.setIconImage(WinIcon);
    contentPane = (JPanel)this.getContentPane();
    /*EditArea��Tree�̏�����*/
    EditArea_Start();
    TreeStart();
    Hex_Area.getTableHeader().setReorderingAllowed(false); // �h���b�O�͖���
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
    MainMenu_File.setText("�t�@�C��");
    MainMenu_File_Exit.setText("�I��");
    MainMenu_File_Exit.addActionListener(new
                                         Frame_MainMenu_File_Exit_ActionAdapter(this));
    MainMenu_Help_About.setText("�o�[�W�������");
    MainMenu_Help_About.addActionListener(new
                                          Frame_MainMenu_Help_About_ActionAdapter(this));
    List_Panel.setLayout(borderLayout4);
    EditPanels.setMaximumSize(new Dimension(32767, 32767));
    EditPanels.setLayout(borderLayout3);
    Block_Imfomation.setLayout(gridBagLayout1);
    BlockSize_Label.setText("�u���b�N�T�C�Y");
    BlockSize_Panel.setLayout(borderLayout6);
    BlockSize_Field.setEditable(false);
//    BlockSize_Field.setText("0x00000000");
    StartAdd_Label.setVerifyInputWhenFocusTarget(true);
    StartAdd_Label.setText("�J�n�A�h���X");
    Size_and_Add_Panel.setLayout(gridBagLayout3);
    StartAdd_Field.setToolTipText("");
    StartAdd_Field.setEditable(false);
//    StartAdd_Field.setText("0x00000000");
    StartAdd_Panel.setLayout(borderLayout2);
    Block_Name.setVerifyInputWhenFocusTarget(true);
    Block_Name.setText("�u���b�N��");
    Block_Name_Field.setEditable(false);
//    Block_Name_Field.setText("�u���b�N��");
    Block_Name_Panel.setLayout(borderLayout7);
    BlockNamePanel.setLayout(gridBagLayout4);
    CheckSum_Label.setText("�`�F�b�N�T��");
    CheckSum_Report.setOpaque(true);
    CheckSum_Report.setRequestFocusEnabled(true);
    CheckSum_Report.setLayout(gridBagLayout5);
    Checksum_Panel.setLayout(borderLayout8);
    CheckSum_Field.setEnabled(true);
    CheckSum_Field.setEditable(false);
    CheckSum_Field.setSelectionStart(10);
//    CheckSum_Field.setText("0x0000");
    Report_Label.setRequestFocusEnabled(true);
    Report_Label.setText("���|�[�g��");
    Report_Field.setEditable(false);
//    Report_Field.setText("0x0000 (= 0 )");
    Report_Panel.setLayout(borderLayout9);
    MainMenu_File_Open.setText("�Z�[�u�f�[�^���J��");
    MainMenu_File_Open.addActionListener(new
                                         Frame_MainMenu_File_Open_actionAdapter(this));
    Save.setText("�ۑ�");
    save.setText("�㏑���ۑ�");
    save.addActionListener(new Frame_save_actionAdapter(this));
    save_as.setText("�V�K�ۑ�");
    save_as.addActionListener(new Frame_save_as_actionAdapter(this));
    statusBar.setText("status");
    ViewMenu.setText("�\���ݒ�");
    MainMenu_Help.setText("�w���v");
    isPokemonCode_Menu.setText("�|�P�����̕����R�[�h�ŕ\��");
    isPokemonCode_Menu.addActionListener(new
                                         Frame_isPokemonCode_Menu_actionAdapter(this));
    AnalysisMenu.setText("���");
    CheatMenu.setText("�`�[�g");
    CreateCheatMenu.setText("�`�[�g�R�[�h�쐬");
    CreateCheatMenu.addActionListener(new Frame_CreateCheatMenu_actionAdapter(this));
    CheatMenu.addActionListener(new Frame_CheatMenu_actionAdapter(this));
    CalChecksumMenu.setText("�`�F�b�N�T���̍Čv�Z");
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
    //Split�̐؂�ڂ�ݒ�
    Main_Panel.setDividerLocation( (int) (List_Panel.getPreferredSize().
                                          getWidth() + 10));
    this.doLayout();
  }

  public static void setStatus(String str) { //�X�e�[�^�X�o�[�ւ̏�����
    statusBar.setText(str);
  }

  /**
   * �u���b�N��I������
   * @param SelectedBlock BlockData
   */

  public void setSelectedBlock(BlockData SelectedBlock) {
    this.SelectedBlock = SelectedBlock;
    this.imformation_set();
    this.EditArea_Rewrite();
  }

  /**
   * �ĕ`��
   */
  public void rewrite() {
    this.EditArea_Rewrite();
    this.TreeRewrite();
    this.repaint();
  }

  public void TreeStart() {
    CustomizedTreeNode root = new CustomizedTreeNode("�t�@�C��");
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

  public void EditArea_Start() { //�G�f�B�b�g�G���A�̏�����
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
    this.imformation_set(); //���ݒ�
  }

  /**
   * EditArea�̍ە`��
   */
  private void EditArea_Rewrite() {
    //��蒼���Ȃ��ƁC�ŏI�u���b�N�����܂��\���ł��Ȃ�
    Hex_Model = new Hex_Model(this);
    Hex_Area.setModel(Hex_Model);
    Hex_Area.repaint();
    EditArea_ColumnSet();
    imformation_set();
  }

  void EditArea_ColumnSet() {
    //�u�A�h���X�v�C�u�I�t�Z�b�g�v�J�����̓T�C�Y���L����
    TableColumn Add_Column = Hex_Area.getColumn("Address");
    Add_Column.setMaxWidth(column_size);
    Add_Column.setMinWidth(column_size);
    Add_Column.setPreferredWidth(column_size);
    Hex_Area.sizeColumnsToFit(0); //�T�C�Y��ς������Ƃ�F�������ŕ`��

    TableColumn Off_Column = Hex_Area.getColumn("Offset");
    Off_Column.setMaxWidth(column_size);
    Off_Column.setMinWidth(column_size);
    Off_Column.setPreferredWidth(column_size);
    Hex_Area.sizeColumnsToFit(0); //�T�C�Y��ς������Ƃ�F�������ŕ`��
  }

  //�u���b�N�f�[�^�̕\��
  void imformation_set() {
    //�u���b�N��
    if (SelectedBlock.getSaveCount() >= 0) {
      Block_Name_Field.setText("��" + SelectedBlock.getSaveCount() +
                               "��Z�[�u�f�[�^�@��" +
                               PokeTools.space(Integer.toHexString(SelectedBlock.
          getBlockNumber()), 2, "0") + "�u���b�N");
    }
    else {
      Block_Name_Field.setText("�ŏI�f�[�^�@��" + SelectedBlock.getBlockNumber() +
                               "�u���b�N");
    }
    //�u���b�N�T�C�Y
    BlockSize_Field.setText
        ("0x" +
         PokeTools.space(Integer.toHexString(SelectedBlock.getLength()), 8, "0"));
    //�X�^�[�g�A�h���X
    StartAdd_Field.setText
        ("0x" +
         PokeTools.space(Long.toHexString(SelectedBlock.getAddr()), 8, "0"));
    //�`�F�b�N�T��
    CheckSum_Field.setText("0x" + SelectedBlock.getChecksum());
    //���|�[�g��
    if (SelectedBlock.getSaveCount() >= 0) {
      Report_Field.setText("0x" + SelectedBlock.getSaveCountHex() + " = (" +
                           SelectedBlock.getSaveCount() + ")");
    }
    else {
      Report_Field.setText("0x" + SelectedBlock.getSaveCountHex() +
                           " = (****)");
    }
  }

  //[�t�@�C��|�I��]�@�I�����ɌĂ΂��
  public void MainMenu_File_Exit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  //[�w���v|�o�[�W�������]
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

  //�E�B���h�E������ꂽ�Ƃ��ɏI������悤�ɃI�[�o�[���C�h
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      /*if(this.isChanged){
        SaveDlg SaveDlg = new SaveDlg(this.Data, FileName, this,
                                      "�ύX����Ă��܂��D�㏑���ۑ����܂����H");
             }*/
      MainMenu_File_Exit_actionPerformed(null);
    }
  }

  void MainMenu_File_Open_actionPerformed(ActionEvent e) {
    /*if(isChanged){
      SaveDlg SaveDlg = new SaveDlg(this.Data, FileName, this,
                                    "�ύX����Ă��܂��D�㏑���ۑ����܂����H");
         }*/
    //[�t�@�C��/�Z�[�u�f�[�^���J��]
    JFileChooser open_chooser = new JFileChooser(new File(this.FileName).
                                                 getPath());
    int selected = open_chooser.showOpenDialog(this);
    if (selected == JFileChooser.APPROVE_OPTION) { //�u�J���v��������Ă�����
      OpenFile(open_chooser.getSelectedFile().getPath());
    }
  }

  public void OpenFile(String FileName) {
    FileRead FR = new FileRead(FileName);
    SaveFileData tmpData = FR.read();
    if (tmpData != null) { //�ǂݍ��݂ɐ�������
      this.FileName = FileName; //�֘A�t���̎��̂���
      isFileOpen = true;
      this.Data = tmpData;
//      this.isChanged = false; //�`�F���W�t���b�O���߂�
      //�V�����ق���0�u���b�N�������I�ɊJ��
      Save tmp = Data.getSave(true);
      this.SelectedBlock = tmp.getBlockData(tmp.getBlockNumberInOrder(0));
      //��ʍĕ`��
      this.setTitle(DefaultTitle + " - " + (new File(FileName)).getName());
      this.TreeRewrite();
      this.EditArea_Rewrite();
    }
  }

  /*  public void dataChanged(){
      //�`�F�b�N���ʓ|�Ȃ̂ŉ������Ȃ����Ƃɂ��܂���
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
      if (column == 0) { //�A�h���X
        return PokeTools.space(Long.toHexString(SelectedBlock.getAddr() +
                                            0x10 * row), 8, "0");
      }
      else if (column == 1) { //�I�t�Z�b�g
        return PokeTools.space(Integer.toHexString(0x10 * row), 8, "0");
      }
      else {
        //�l
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
      if (Frame.isPokemonCode || !Frame.isFileOpen) { //�|�P�����R�[�h���E�J���Ă��Ȃ��Ƃ��͕ҏW�����Ȃ�
        return false;
      }
      String tmp = Frame.SelectedBlock.getCellText(rowIndex, columnIndex);
      if (tmp != null) { //null�łȂ����t�b�^�ł���
        Frame.setStatus(tmp);
        return false;
      }
//      statusSet("�@");//���Z�b�g
      //�I�t�Z�b�g�ƃA�h���X�͕ҏW�s�\
      if (columnIndex <= 1) {
        return (false);
      }
      else {
        return (true);
      }
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      //�����Ɂu16�i�łȂ���Ώ������߂Ȃ��v����
      try {
        if ( ( (String) aValue).length() == 2) { //2�����Ȃ��
          Integer.parseInt( (String) aValue, 16); //�ϊ������Ă݂āE�E�E
          byte older = SelectedBlock.getByte(rowIndex * 0x10 + columnIndex -
                                             2);
          byte newer = PokeTools.IntToByte(Integer.parseInt( (String) aValue, 16));
          if (older != newer) {
//            dataChanged();
            SelectedBlock.setByte(newer, rowIndex * 0x10 + columnIndex - 2);
            Frame.setStatus("�ҏW����");
          }
        }
        else {
          Frame.setStatus("�G���[�I�F2�P�^��16�i������͂��Ă�������");
        }
      }
      catch (NumberFormatException e) { //16�i���ɕϊ��ł��Ȃ��I
        Frame.setStatus("�G���[�I�F���̐���16�i���ł͂���܂���");
      }
      fireTableDataChanged();
    }
  }

  /**
   * �㏑���ۑ�
   * @param e ActionEvent
   */
  void save_actionPerformed(ActionEvent e) {
    if (isFileOpen) { //�t�@�C�����J���Ă��邩
      SaveDlg_Show(FileName);
    }
    else {
      setStatus("�G���[�I�F�܂��̓t�@�C�����J���ĉ�����");
    }
  }

  void SaveDlg_Show(String filename) {
    SaveDlg SaveDlg = new SaveDlg(Data, filename, this, "�㏑�����Ă��܂��܂��D��낵���ł����H");
    SaveDlg.setVisible(true);
  }

  void save_as_actionPerformed(ActionEvent e) { //�u�č\�z���ĐV�K�ۑ��v
    //[�t�@�C��/�ۑ�/�č\�z���ĐV�K�ۑ�]
    if (isFileOpen) { //�t�@�C�����J���Ă��邩
      JFileChooser save_chooser = new JFileChooser(new File(this.FileName).
          getPath());
      int selected = save_chooser.showSaveDialog(this);
      if (selected == JFileChooser.APPROVE_OPTION) { //�u�ۑ��v��������Ă�����
        String tmpFileName = save_chooser.getSelectedFile().getPath(); //�������݃t�@�C�����̎擾
        File File = new File(tmpFileName);
        if (File.canRead()) { //�t�@�C�����ǂݍ��߂遁���݂���Ȃ�C�㏑���ɂȂ��Ă��܂�
          SaveDlg_Show(tmpFileName);
        }
        else { //�łȂ���Ε��ʂɏ�������
          this.FileName = tmpFileName;
          FileWrite FW = new FileWrite(tmpFileName, Data);
          FW.write();
        }
        this.TreeRewrite();
        this.EditArea_Rewrite();
      }
    }
    else {
      setStatus("�G���[�I�F�܂��̓t�@�C�����J���ĉ�����");
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

  //�|�P�����R�[�h�ŕ\��
  public void isPokemonCode_Menu_actionPerformed(ActionEvent e) {
    this.isPokemonCode = isPokemonCode_Menu.getState();
    this.EditArea_Rewrite(); //�ĕ`��
  }

  /**
   * �`�F�b�N�T���̍Čv�Z
   * @param e ActionEvent
   */
  public void CalChecksumMenu_actionPerformed(ActionEvent e) {
    if (isFileOpen) { //�t�@�C�����J���Ă��邩
      this.Data.calcCheckSum();
      this.setStatus("�`�F�b�N�T���̍Čv�Z���s���܂����D");
      this.EditArea_Rewrite();
    }
    else {
      setStatus("�G���[�I�F�܂��̓t�@�C�����J���ĉ�����");
    }
  }

  /**
   * �`�[�g���j���[���J��
   * @param e ActionEvent
   */
  public void CheatMenu_actionPerformed(ActionEvent e) {
    if (isFileOpen) { //�t�@�C�����J���Ă��邩
      CheatDialog Dlg = new CheatDialog(this, this.Data);
      Dlg.setVisible(true);
    }
    else {
      setStatus("�G���[�I�F�܂��̓t�@�C�����J���ĉ�����");
    }
  }

  //�`�[�g�R�[�h�쐬
  public void CreateCheatMenu_actionPerformed(ActionEvent e) {
    if (isFileOpen) { //�t�@�C�����J���Ă��邩
      CreateCheatDialog Dlg = new CreateCheatDialog(this, this.Data);
      Dlg.setVisible(true);
    }
    else {
      setStatus("�G���[�I�F�܂��̓t�@�C�����J���ĉ�����");
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
 * <p>�^�C�g��: �Z�������_��</p>
 *
 * <p>����: �����_���ł����Ă΂�</p>
 *
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 *
 * <p>��Ж�: �Ձi�v�T�C�j�̋����֐S���</p>
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
         �X�[�p�[�N���X�ŏ㏑�������̂ł�������D
         �������C�����������ƍĕ`�ʂł΂���D
         table.setSelectionBackground(backGround);
     */
//    super.addMouseListener(new PopupRightClick((JLabel)this));//JLabel�ɂ͖����E�E�E

    return super.getTableCellRendererComponent(table, value, isSelected,
                                               hasFocus, row, column);
  }

  /**
   * ��L�̌��ɂ�
   * ������������鎖�őΉ�
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
