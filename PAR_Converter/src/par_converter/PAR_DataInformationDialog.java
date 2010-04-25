package par_converter;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.Calendar;
import java.io.File;

import psi.lib.CalTools.*;

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
public class PAR_DataInformationDialog extends JDialog {
    private boolean isOK;
    JFrame Owner;
    File File;
    JPanel panel1 = new JPanel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JLabel FileNameLabel2 = new JLabel();
    JLabel FileNameLabel = new JLabel();
    public static final String[] POKEMON_VERSION_NAMES = {"POKEMON RUBY", //バージョン一覧
            "POKEMON SAPP", "POKEMON LEAF", "POKEMON FIRE", "POKEMON EMER", };
    JLabel VersionLabel = new JLabel();
    JComboBox VersionList = new JComboBox(POKEMON_VERSION_NAMES);
    JLabel TitleLabel = new JLabel();
    JTextField TitleField = new JTextField();
    JLabel DescLabel = new JLabel();
    JTextField DescField = new JTextField();
    JLabel NoteLabel = new JLabel();
    JScrollPane NoteScrollPane = new JScrollPane();
    JTextArea NoteArea = new JTextArea();
    JPanel jPanel1 = new JPanel();
    JButton CancelButton = new JButton();
    JButton OKButton = new JButton();
    public PAR_DataInformationDialog(JFrame owner, String title, boolean modal,File file) {
        super(owner, title, modal);
        this.Owner = owner;
        this.File = file;
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            //pack();
            setDefaultText();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * setDefaultText
     */
    private void setDefaultText() {
        this.FileNameLabel.setText(this.File.getName());
        Calendar now = Calendar.getInstance();
        String date = "";
        date += PokeTools.space(String.valueOf((now.get(Calendar.YEAR))),4,"0") + "/";
        date += PokeTools.space(String.valueOf((now.get(Calendar.MONTH)+1)),2,"0") + "/";
        date += PokeTools.space(String.valueOf((now.get(Calendar.DATE))),2,"0") + " ";
        date += now.get(Calendar.HOUR_OF_DAY) + ":";//なぜかこれだけ0が追加されない
        date += PokeTools.space(String.valueOf((now.get(Calendar.MINUTE))),2,"0") + ":";
        date += PokeTools.space(String.valueOf((now.get(Calendar.SECOND))),2,"0");
//        this.DescField.setText(date);
        this.DescField.setText("Save"+date+" 41");
        this.TitleField.setText((String)this.VersionList.getModel().getSelectedItem());
    }

    public PAR_DataInformationDialog(JFrame owner,File file) {
        this(owner, "セーブデータ情報の設定", false,file);
    }

    public void setVisible(boolean flag) {
        if (flag) {
            this.setSize(new Dimension(300, 300));
            Dimension dlgSize = this.getSize();
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
        FileNameLabel2.setText("対象ファイル名：");
        FileNameLabel.setText("");
        VersionLabel.setText("バージョン：");
        TitleLabel.setText("タイトル：");
        TitleField.setToolTipText("");
        TitleField.setText("");
        DescLabel.setText("説明：");
        DescField.setEditable(true);
        DescField.setText("");
        NoteLabel.setText("情報：");
        NoteArea.setText("");
        NoteArea.setLineWrap(true);
        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new
                                       PAR_DataInformationDialog_CancelButton_actionAdapter(this));
        OKButton.setText("OK");
        OKButton.addActionListener(new
                                   PAR_DataInformationDialog_OKButton_actionAdapter(this));
        VersionList.addActionListener(new
                PAR_DataInformationDialog_VersionList_actionAdapter(this));
        this.getContentPane().add(panel1, java.awt.BorderLayout.CENTER);
        panel1.add(VersionList, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        panel1.add(FileNameLabel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        panel1.add(TitleField, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        panel1.add(DescField, new GridBagConstraints(1, 3, 1, 2, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        panel1.add(FileNameLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 5, 0, 5), 0, 0));
        panel1.add(VersionLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 5, 0, 5), 0, 0));
        panel1.add(TitleLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 5, 0, 5), 0, 0));
        panel1.add(DescLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 5, 0, 6), 0, 0));
        NoteScrollPane.getViewport().add(NoteArea);
        panel1.add(NoteLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(0, 5, 0, 5), 0, 0));
        panel1.add(NoteScrollPane, new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 100));
        jPanel1.add(OKButton);
        jPanel1.add(CancelButton);
        panel1.add(jPanel1, new GridBagConstraints(0, 6, 2, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
    }

    public void OKButton_actionPerformed(ActionEvent e) {
        this.isOK = true;
        this.setVisible(false);
    }

    public void CancelButton_actionPerformed(ActionEvent e) {
        isOK = false;
        this.setVisible(false);
    }

    protected boolean isOK() {
        return this.isOK;
    }

    protected String getTitleString() {
        if (isOK) {
            return this.TitleField.getText();
        }
        return null;
    }

    protected String getDescString() {
        if (isOK) {
            return this.DescField.getText();
        }
        return null;
    }

    protected String getNoteString() {
        if (isOK) {
            return this.NoteArea.getText();
        }
        return null;
    }

    protected int getVersion() {
        if (isOK) {
            String str = (String)VersionList.getModel().getSelectedItem();
            if (str.equals(this.
                    POKEMON_VERSION_NAMES[0])) {
                return DataConverter.RUBY;
            } else if (str.equals(this.
                    POKEMON_VERSION_NAMES[1])) {
                return DataConverter.SAPP;
            } else if (str.equals(this.
                    POKEMON_VERSION_NAMES[2])) {
                return DataConverter.LEAF;
            } else if (str.equals(this.
                    POKEMON_VERSION_NAMES[3])) {
                return DataConverter.FIRE;
            } else if (str.equals(this.
                    POKEMON_VERSION_NAMES[4])) {
                return DataConverter.EMER;
            }
        }
        return -1;
    }

    public void VersionList_actionPerformed(ActionEvent e) {
        this.TitleField.setText((String)this.VersionList.getModel().getSelectedItem());
    }
}


class PAR_DataInformationDialog_VersionList_actionAdapter implements
        ActionListener {
    private PAR_DataInformationDialog adaptee;
    PAR_DataInformationDialog_VersionList_actionAdapter(
            PAR_DataInformationDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.VersionList_actionPerformed(e);
    }
}


class PAR_DataInformationDialog_CancelButton_actionAdapter implements
        ActionListener {
    private PAR_DataInformationDialog adaptee;
    PAR_DataInformationDialog_CancelButton_actionAdapter(
            PAR_DataInformationDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.CancelButton_actionPerformed(e);
    }
}


class PAR_DataInformationDialog_OKButton_actionAdapter implements
        ActionListener {
    private PAR_DataInformationDialog adaptee;
    PAR_DataInformationDialog_OKButton_actionAdapter(PAR_DataInformationDialog
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.OKButton_actionPerformed(e);
    }
}
