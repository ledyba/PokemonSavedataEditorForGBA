package par_converter;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class MsgDialog extends JDialog {
    private JPanel panel1 = new JPanel();
    private String Title;
    private String Msg;
    private String Msg2;
    private Frame Owner;
    private boolean isOK = false;
    private boolean isPushed = false;
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JLabel MsgLabel = new JLabel();
    JPanel ButtonPanel = new JPanel();
    JButton NO_Button = new JButton();
    JButton YES_Button = new JButton();
    JLabel MsgLabel2 = new JLabel();
    public MsgDialog(Frame owner, String title, String msg, String msg2) {
        super(owner, title, true);
        this.Title = title;
        this.Msg = msg;
        this.Msg2 = msg2;
        this.Owner = owner;
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void setVisible(boolean flag) {
        if (flag) {
            Dimension dlgSize = this.getPreferredSize();
            Dimension frmSize = Owner.getSize();
            Point loc = Owner.getLocation();
            this.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
                             (frmSize.height - dlgSize.height) / 2 + loc.y);
            this.setModal(true);
            super.setVisible(true);
        } else {
            super.setVisible(false);
        }
    }

    private void jbInit() throws Exception {
        panel1.setLayout(gridBagLayout1);
        NO_Button.setText("いいえ");
        NO_Button.addActionListener(new MsgDialog_NO_Button_actionAdapter(this));
        YES_Button.setText("はい");
        YES_Button.addActionListener(new MsgDialog_YES_Button_actionAdapter(this));
        MsgLabel.setText(Msg);
        MsgLabel2.setText(Msg2);
        getContentPane().add(panel1);
        ButtonPanel.add(YES_Button, null);
        ButtonPanel.add(NO_Button, null);
        panel1.add(MsgLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        panel1.add(ButtonPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        panel1.add(MsgLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
    }

    public void YES_Button_actionPerformed(ActionEvent e) {
        isOK = true;
        isPushed = true;
        this.setVisible(false);
    }
    public void NO_Button_actionPerformed(ActionEvent e) {
        isOK = false;
        isPushed = true;
        this.setVisible(false);
    }
    protected boolean isOK(){
        if(isPushed){
            return isOK;
        }else{
            return false;
        }
    }
}


class MsgDialog_NO_Button_actionAdapter implements ActionListener {
    private MsgDialog adaptee;
    MsgDialog_NO_Button_actionAdapter(MsgDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.NO_Button_actionPerformed(e);
    }
}


class MsgDialog_YES_Button_actionAdapter implements ActionListener {
    private MsgDialog adaptee;
    MsgDialog_YES_Button_actionAdapter(MsgDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.YES_Button_actionPerformed(e);
    }
}
