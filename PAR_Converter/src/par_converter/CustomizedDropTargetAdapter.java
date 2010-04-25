package par_converter;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.FileInputStream;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.awt.dnd.DropTargetAdapter;
import java.io.FileOutputStream;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.io.*;


/**
 * <p>�^�C�g��: PAR�p�f�[�^�R���o�[�^</p>
 *
 * <p>����: </p>
 *
 * <p>���쌠: Copyright (c) 2006 PSI</p>
 *
 * <p>��Ж�: �Ձi�v�T�C�j�̋����֐S���</p>
 *
 * @author �Ձi�v�T�C�j
 * @version 1.0
 */
public class CustomizedDropTargetAdapter extends DropTargetAdapter {
    public static final int MODE_DEF_TO_PAR = 1;
    public static final int MODE_PAR_TO_DEF = 2;
    private int Mode;
    private MainFrame Owner;
    public CustomizedDropTargetAdapter(int mode, MainFrame owner) {
        super();
        this.Owner = owner;
        this.Mode = mode;
    }

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
                int success = 0;
                int all = 0;
                while (i.hasNext()) {
                    all++; //�S�t�@�C��
                    File file = (File) i.next();
                    FileInputStream fis = new FileInputStream(file); //�X�g���[���̍쐬
                    byte[] data = new byte[fis.available()]; //�ǂݍ��݂̂��߂̕ϐ��m��
                    fis.read(data); //�ǂݍ���
                    fis.close(); //����D
                    byte[] convData = null; //�ϊ���̃f�[�^

                    switch (Mode) {
                    case MODE_PAR_TO_DEF: //PAR����W���f�[�^��
                        convData = DataConverter.convertPARtoDEF(data); //�f�[�^�̕ϊ�
                        if (convData != null) { //�������f�[�^�ł���Ȃ�
                            if(writeFile(convData, "sav", file)){
                                success++;
                            }
                        }
                        break;
                    case MODE_DEF_TO_PAR: //�W���f�[�^����PAR�f�[�^��
                        if(data.length != DataConverter.PAR_DATA_SIZE * 2){
                            break;
                        }
                        PAR_DataInformationDialog dlg = new
                                PAR_DataInformationDialog(this.Owner,file);
                        dlg.setVisible(true);
                        if (dlg.isOK() == false) {
                            break;
                        }
                        convData = DataConverter.convertDEFtoPAR(data,
                                dlg.getVersion(), dlg.getTitleString(),
                                dlg.getDescString(), dlg.getNoteString());
                        if (convData != null) { //�������f�[�^�ł���Ȃ�
                            if(this.writeFile(convData, "xps", file)){
                                success++;
                            }
                        }
                        break;
                    }
                }
                dtde.dropComplete(true);
                Owner.setStatus("�S" + all + "�t�@�C����" + success + "�t�@�C���ϊ�����");
                return;
            }
        } catch (UnsupportedFlavorException ufe) {
            ufe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        dtde.rejectDrop();
    }

    private boolean writeFile(byte[] convData, String fileType, File file) {
        try {
            String filePath = this.getNewFileName(fileType, file.getPath()); //�p�X�̎擾���g���q�ύX
            File convFile = new File(filePath);
            if (convFile.canRead()) {
                MsgDialog MsgDlg = new MsgDialog(this.Owner,
                                                 "�㏑�����܂����H",
                                                 "\"" + convFile.getName() +
                                                 "\"�͊��ɑ��݂��܂��D",
                                                 "�㏑�����Ă��ǂ��ł����H");
                MsgDlg.setVisible(true);
                if (MsgDlg.isOK() == false) { //�㏑�����Ȃ��Ȃ甲����
                    return false;
                }
            }
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(convData);
            fos.close(); //���܂�
            return true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * �g���q��ύX���܂��D
     * @param type String�@�V�����g���q
     * @param filePath String�@�t�@�C����
     * @return String
     */
    private String getNewFileName(String type, String filePath) {
        filePath = filePath.substring(0,
                                      filePath.lastIndexOf(".")); //�g���q������Ă܂�
        filePath = filePath + "." + type; //�g���q�̒ǉ�
        return filePath;
    }
}
