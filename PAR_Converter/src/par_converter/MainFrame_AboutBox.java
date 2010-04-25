package par_converter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

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
public class MainFrame_AboutBox extends JDialog implements ActionListener {
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel insetsPanel1 = new JPanel();
    JPanel insetsPanel2 = new JPanel();
    JPanel insetsPanel3 = new JPanel();
    JButton button1 = new JButton();
    JLabel imageLabel = new JLabel();
    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();
    JLabel label3 = new JLabel();
    JLabel label4 = new JLabel();
    ImageIcon image1 = new ImageIcon();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    FlowLayout flowLayout1 = new FlowLayout();
    GridLayout gridLayout1 = new GridLayout();
    String product = "PAR用データコンバータ";
    String version = "1.0";
    String copyright = "Copyright (c) 2006 PSI";
    String comments = "";

    public MainFrame_AboutBox(Frame parent) {
        super(parent);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public MainFrame_AboutBox() {
        this(null);
    }

    /**
     * コンポーネントの初期化。
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        image1 = new ImageIcon(par_converter.MainFrame.class.getResource(
                "icon.png"));
        imageLabel.setIcon(image1);
        setTitle("バージョン情報");
        panel1.setLayout(borderLayout1);
        panel2.setLayout(borderLayout2);
        insetsPanel1.setLayout(flowLayout1);
        insetsPanel2.setLayout(flowLayout1);
        insetsPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gridLayout1.setRows(4);
        gridLayout1.setColumns(1);
        label1.setText(product);
        label2.setText("1.11(2006/03/29)");
        label3.setText(copyright);
        label4.setText(comments);
        insetsPanel3.setLayout(gridLayout1);
        insetsPanel3.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 10));
        button1.setText("OK");
        button1.addActionListener(this);
        insetsPanel2.add(imageLabel, null);
        panel2.add(insetsPanel2, BorderLayout.WEST);
        getContentPane().add(panel1, null);
        insetsPanel3.add(label1, null);
        insetsPanel3.add(label2, null);
        insetsPanel3.add(label3, null);
        insetsPanel3.add(label4, null);
        panel2.add(insetsPanel3, BorderLayout.CENTER);
        insetsPanel1.add(button1, null);
        panel1.add(insetsPanel1, BorderLayout.SOUTH);
        panel1.add(panel2, BorderLayout.NORTH);
        setResizable(true);
    }

    /**
     * ボタンイベントでダイアログを閉じる
     *
     * @param actionEvent ActionEvent
     */
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == button1) {
            dispose();
        }
    }
}
