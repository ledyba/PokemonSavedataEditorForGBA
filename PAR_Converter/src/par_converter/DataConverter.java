package par_converter;

import java.io.ByteArrayInputStream;
import java.io.*;
import psi.lib.CalTools.*;

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
public class DataConverter {
    public static final int PAR_DATA_SIZE = 0x10000; //PAR�f�[�^�̃T�C�Y
    public static final int DEF_DATA_SIZE = 0x20000; //���K�f�[�^�̃T�C�Y
    //�f�[�^�w�b�_�D����Ɣ�r����
    //0x0000000d + "SharkPortSave" + 0x000f0000
    public static final byte[] PAR_SAVE_HEADER = {0x0D, 0x00, 0x00, 0x00, 0x53,
                                                 0x68, 0x61, 0x72, 0x6B, 0x50,
                                                 0x6F, 0x72, 0x74, 0x53, 0x61,
                                                 0x76, 0x65, 0x00, 0x00, 0x0F,
                                                 0x00, };
    public static final byte[][] POKEMON_ROM_HEADER = { //ROM���+�萔 28(=0x1c)�o�C�g
            //POKEMON RUBY
            {0x50, 0x4F, 0x4B, 0x45, 0x4D, 0x4F,
            0x4E, 0x20, 0x52, 0x55, 0x42, 0x59,
            0x41, 0x58, 0x56, 0x4A, 0x00, 0x00,
            0x3C, 0x30, 0x01, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00},
            //POKEMON SAPP
            {0x50, 0x4F, 0x4B, 0x45, 0x4D, 0x4F,
            0x4E, 0x20, 0x53, 0x41, 0x50, 0x50,
            0x41, 0x58, 0x50, 0x4A, 0x00, 0x00,
            0x50, 0x30, 0x01, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00},
            //POKEMON LEAF
            {0x50, 0x4F, 0x4B, 0x45, 0x4D, 0x4F,
            0x4E, 0x20, 0x4C, 0x45, 0x41, 0x46,
            0x42, 0x50, 0x47, 0x4A, 0x00, 0x00,
            0x7C, 0x30, 0x01, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00},
            //POKEMON FIRE
            {0x50, 0x4F, 0x4B, 0x45, 0x4D, 0x4F,
            0x4E, 0x20, 0x46, 0x49, 0x52, 0x45,
            0x42, 0x50, 0x52, 0x4A, 0x00, 0x00,
            0x63, 0x30, 0x01, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00},
            //POKEMON MEMER
            {0x50, 0x4F, 0x4B, 0x45, 0x4D, 0x4F,
            0x4E, 0x20, 0x45, 0x4D, 0x45, 0x52,
            0x42, 0x50, 0x45, 0x4A, 0x00, 0x00,
            0x6D, 0x30, 0x01, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00},
    };
    public static final byte[] ROM_INFO_HEADER_SIZE = {0x1C, 0x00, 0x01, 0x00};
    public static final int RUBY = 0;
    public static final int SAPP = 1;
    public static final int LEAF = 2;
    public static final int FIRE = 3;
    public static final int EMER = 4;

    public DataConverter() {
        super();
    }

    /**
     * �f�[�^�𕪐͂��ăw�b�_�����������m�F���܂��D
     * @param data byte[]
     * @return boolean
     */
    private static boolean checkPAR_DataHeader(byte[] data) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            byte[] header = new byte[PAR_SAVE_HEADER.length];
            bais.read(header, 0, PAR_SAVE_HEADER.length);
            boolean check = false;
            for (int i = 0; i < header.length; i++) {
                check = check || (header[i] != PAR_SAVE_HEADER[i]);
            }
            bais.close();
            return!check;
        } catch (IOException ex) {
            return false;
        }
    }

    public static byte[] convertPARtoDEF(byte[] data) {
        try {
            if (!checkPAR_DataHeader(data)) { //�������Ȃ����null��Ԃ�
                return null;
            }
            /*
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            bais.skip(bais.available() - PAR_DATA_SIZE - 4); //CRC32�̕��������܂�
            byte[] PARdata = new byte[PAR_DATA_SIZE * 2];
            bais.read(PARdata, 0, PAR_DATA_SIZE);
            bais.close();
            return PARdata;*/
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            bais.skip(PAR_SAVE_HEADER.length);//�w�b�_���Ƃ΂�
            byte[] length = new byte[4];
            bais.read(length,0,4);//�^�C�g���̒�����ǂݍ���
            bais.skip(PokeTools.toLong(length));
            bais.read(length,0,4);//�����̒�����ǂݍ���
            bais.skip(PokeTools.toLong(length));
            bais.read(length,0,4);//���̒�����ǂݍ���
            bais.skip(PokeTools.toLong(length));
            bais.read(length,0,4);//�t�@�C���T�C�Y�ǂݍ���
            int dataSize = (int)PokeTools.toLong(length) - 0x1c;
            bais.skip(0x1c);//���̃w�b�_�̕������X�L�b�v���܂��D
            byte[] PARdata = new byte[DEF_DATA_SIZE];
            bais.read(PARdata,0,dataSize);
            bais.close();
            return PARdata;
        } catch (IOException ex) {
            return null;
        }
    }

    public static byte[] convertDEFtoPAR(byte[] data, int version, String title,
                                         String desc, String note) {
        if (data.length != PAR_DATA_SIZE * 2) {
            return null;
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ByteArrayOutputStream checksum_baos = new ByteArrayOutputStream();
            baos.write(PAR_SAVE_HEADER); //�w�b�_
            byte[] titleArray = title.getBytes();
            byte[] descArray = desc.getBytes();
            byte[] noteArray = note.getBytes();
            baos.write(PokeTools.toByteArray(titleArray.length, 4)); //�^�C�g���F����
            baos.write(titleArray); //�^�C�g���F���ۂ̃f�[�^
            baos.write(PokeTools.toByteArray(descArray.length, 4)); //�����F����
            baos.write(descArray); //�����F���ۂ̃f�[�^
            baos.write(PokeTools.toByteArray(noteArray.length, 4)); //���F����
            baos.write(noteArray); //���F���ۂ̃f�[�^
            baos.write(ROM_INFO_HEADER_SIZE); //ROM�f�[�^�w�b�_�{�Z�[�u�f�[�^�̒���
            baos.write(POKEMON_ROM_HEADER[version]); //ROM���w�b�_
            checksum_baos.write(POKEMON_ROM_HEADER[version]); //�`�F�b�N�T���p
            baos.write(data, 0, PAR_DATA_SIZE); //�f�[�^��������
            checksum_baos.write(data, 0, PAR_DATA_SIZE); //�`�F�b�N�T���p
            baos.write(calChecksum(checksum_baos.toByteArray())); //CRC��������
            return baos.toByteArray();
        } catch (IOException ex) {
        }
        return null;
    }

    /**
     * �`�F�b�N�T���i���Ă�CRC�j���v�Z���܂��j
     * @param data byte[]
     * @return byte[]
     */
    private static byte[] calChecksum(byte[] data) {
        byte[] checksum = new byte[4];
        for (int i = 0; i < data.length; i++) {
            int mod = psi.lib.CalTools.CalcLittleEndian.modByteArray(checksum,
                    0x18);
//            long mod = psi.lib.PokeTools.PokeTools.toLong(checksum) % 24;
            int add = PokeTools.ByteToInt(data[i]) << mod;
            byte[] addArray = PokeTools.toByteArray(add,4);
            checksum = psi.lib.CalTools.CalcLittleEndian.addWord(checksum,
                    addArray, 4);
        }
        return checksum;
    }
}
