package par_converter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.DropTargetAdapter;

/**
 * <p>タイトル: PAR用データコンバータ</p>
 *
 * <p>説明: </p>
 *
 * <p>著作権: Copyright (c) 2006 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author ψ（プサイ）
 * @version 1.0
 */
public class MainFrame extends JFrame {
    public static final Image ICON = Toolkit.getDefaultToolkit().createImage(
            MainFrame.class.getResource("icon.png")); //アイコン

    JPanel contentPane;
    BorderLayout borderLayout1 = new BorderLayout();
    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileExit = new JMenuItem();
    JMenu jMenuHelp = new JMenu();
    JMenuItem jMenuHelpAbout = new JMenuItem();
    JLabel statusBar = new JLabel();
    JPanel DropPanel = new JPanel();
    JPanel PARtoDefault_Drag_Panel = new JPanel();
    JPanel DefaultToPAR_Drag_Panel = new JPanel();
    TitledBorder titledBorder_DEF_PAR = new TitledBorder("標準データからPAR用データへ");
    TitledBorder titledBorder_PAR_DEF = new TitledBorder("PAR用データから標準データへ");
    GridLayout gridLayout1 = new GridLayout();

    public MainFrame() {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
            setDragTarget();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * setDragTarget
     */
    private void setDragTarget() {
        DropTargetListener dtl[] = new DropTargetListener[2];
        dtl[0] = (DropTargetAdapter)new CustomizedDropTargetAdapter(
                CustomizedDropTargetAdapter.MODE_DEF_TO_PAR,this);
        dtl[1] = (DropTargetAdapter)new CustomizedDropTargetAdapter(
                CustomizedDropTargetAdapter.MODE_PAR_TO_DEF,this);
        DropTarget dt[] = {new DropTarget(this.DefaultToPAR_Drag_Panel,
                                          DnDConstants.ACTION_COPY, dtl[0], true),
                          new DropTarget(this.PARtoDefault_Drag_Panel,
                                         DnDConstants.ACTION_COPY, dtl[1], true)};
    }

    /**
     * コンポーネントの初期化。
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        this.setIconImage(this.ICON);
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(borderLayout1);
        setSize(new Dimension(400, 300));
        setTitle("PAR用データコンバータ");
        statusBar.setText(" ");
        jMenuFile.setText("ファイル");
        jMenuFileExit.setText("終了");
        jMenuFileExit.addActionListener(new
                                        MainFrame_jMenuFileExit_ActionAdapter(this));
        jMenuHelp.setText("ヘルプ");
        jMenuHelpAbout.setText("バージョン情報");
        jMenuHelpAbout.addActionListener(new
                                         MainFrame_jMenuHelpAbout_ActionAdapter(this));
        DropPanel.setLayout(gridLayout1);
        PARtoDefault_Drag_Panel.setBackground(Color.green);
        PARtoDefault_Drag_Panel.setBorder(titledBorder_PAR_DEF);
        DefaultToPAR_Drag_Panel.setBackground(Color.pink);
        DefaultToPAR_Drag_Panel.setBorder(titledBorder_DEF_PAR);
        DropPanel.setInputVerifier(null);
        jMenuBar1.add(jMenuFile);
        jMenuFile.add(jMenuFileExit);
        jMenuBar1.add(jMenuHelp);
        jMenuHelp.add(jMenuHelpAbout);
        setJMenuBar(jMenuBar1);
        contentPane.add(statusBar, BorderLayout.SOUTH);
        contentPane.add(DropPanel, java.awt.BorderLayout.CENTER);
        DropPanel.add(PARtoDefault_Drag_Panel, null);
        DropPanel.add(DefaultToPAR_Drag_Panel, null);
    }

    void setStatus(String str) {
        this.statusBar.setText(str);
    }

    /**
     * [ファイル|終了] アクションが実行されました。
     *
     * @param actionEvent ActionEvent
     */
    void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * [ヘルプ|バージョン情報] アクションが実行されました。
     *
     * @param actionEvent ActionEvent
     */
    void jMenuHelpAbout_actionPerformed(ActionEvent actionEvent) {
        MainFrame_AboutBox dlg = new MainFrame_AboutBox(this);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
                        (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.pack();
        dlg.setVisible(true);
    }
}


class MainFrame_jMenuFileExit_ActionAdapter implements ActionListener {
    MainFrame adaptee;

    MainFrame_jMenuFileExit_ActionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuFileExit_actionPerformed(actionEvent);
    }
}


class MainFrame_jMenuHelpAbout_ActionAdapter implements ActionListener {
    MainFrame adaptee;

    MainFrame_jMenuHelpAbout_ActionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuHelpAbout_actionPerformed(actionEvent);
    }
}
