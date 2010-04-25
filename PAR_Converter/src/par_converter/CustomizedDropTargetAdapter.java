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
                    all++; //全ファイル
                    File file = (File) i.next();
                    FileInputStream fis = new FileInputStream(file); //ストリームの作成
                    byte[] data = new byte[fis.available()]; //読み込みのための変数確保
                    fis.read(data); //読み込み
                    fis.close(); //閉じる．
                    byte[] convData = null; //変換後のデータ

                    switch (Mode) {
                    case MODE_PAR_TO_DEF: //PARから標準データへ
                        convData = DataConverter.convertPARtoDEF(data); //データの変換
                        if (convData != null) { //正しいデータであるなら
                            if(writeFile(convData, "sav", file)){
                                success++;
                            }
                        }
                        break;
                    case MODE_DEF_TO_PAR: //標準データからPARデータへ
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
                        if (convData != null) { //正しいデータであるなら
                            if(this.writeFile(convData, "xps", file)){
                                success++;
                            }
                        }
                        break;
                    }
                }
                dtde.dropComplete(true);
                Owner.setStatus("全" + all + "ファイル中" + success + "ファイル変換完了");
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
            String filePath = this.getNewFileName(fileType, file.getPath()); //パスの取得＆拡張子変更
            File convFile = new File(filePath);
            if (convFile.canRead()) {
                MsgDialog MsgDlg = new MsgDialog(this.Owner,
                                                 "上書きしますか？",
                                                 "\"" + convFile.getName() +
                                                 "\"は既に存在します．",
                                                 "上書きしても良いですか？");
                MsgDlg.setVisible(true);
                if (MsgDlg.isOK() == false) { //上書きしないなら抜ける
                    return false;
                }
            }
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(convData);
            fos.close(); //閉じます
            return true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 拡張子を変更します．
     * @param type String　新しい拡張子
     * @param filePath String　ファイル名
     * @return String
     */
    private String getNewFileName(String type, String filePath) {
        filePath = filePath.substring(0,
                                      filePath.lastIndexOf(".")); //拡張子を取ってます
        filePath = filePath + "." + type; //拡張子の追加
        return filePath;
    }
}
