package poke_save;

import java.io.*;
import java.math.*;
class poke_save{
		public static final int buff = 0x1000;
	public static void main(String[] args){
		if(args[0].equals("") == false || args[0] != null){
		}else{
			System.out.println("�I�v�V�������w�肵�Ă�������");
			System.exit(0);
		}
		try{
			//�f�[�^���i�[����o�C�g�z����쐬
			byte[] data = new byte[buff];
			//http://java.sun.com/j2se/1.4/ja/docs/ja/api/java/io/FileInputStream.html
			//http://msugai.fc2web.com/java/IO/file.htm
			FileInputStream  in  = new FileInputStream(args[0]);
			
			//�f�[�^�\��
			System.out.println("�t�@�C���T�C�Y�F"+in.available()+"\n");
						
			//�擪�f�[�^���o
			in.read(data,0x0,0xff0);
			System.out.println("�擪�u���b�N");
			System.out.println("�J�n�A�h���X�F0x00000000");
			System.out.println("�T�C�Y�F0x00000ff0");
			System.out.println("--------------------------------------------------------------------------------");
			print(data,0,0xff0);//�v�����g
			System.out.println("--------------------------------------------------------------------------------");
			//�u���b�N�f�[�^�W���o
			for(int i=0; in.available() > 0;i++){
				//�J�n�A�h���X�\��
				in.read(data,0,buff); //�ǂݍ���
				System.out.println("��"+data[4]+"�u���b�N");
				System.out.println("�J�n�A�h���X�F"+space(Integer.toHexString((buff * i) + 0xff0),8,"0"));
				System.out.println("�T�C�Y�F"+space(Integer.toHexString(buff),8,"0"));
				System.out.println("--------------------------------------------------------------------------------");
				print(data,buff * i+0xff0,buff);//�v�����g
				System.out.println("--------------------------------------------------------------------------------");
System.out.println(in.available()+">"+(buff * i + 0xff0));
			}
			in.close();
		}catch(IOException e){
			System.out.println("�G���[���������Ă��܂�");
			System.exit(0);
		}
	}
	//��ʕ\���p
	//data:�f�[�^
	//base:�x�[�X�A�h���X
	//bytes:�擪���o�C�g��\�����邩
	public static void print(byte[] data,int base,int bytes){
		int line_count=0;
		boolean file_end = true;
		while(file_end){
			//�A�h���X�\��
			System.out.print(space(Integer.toHexString(line_count * 16 + base),8,"0")+"�F");
			//�I�t�Z�b�g�\��
			System.out.print(space(Integer.toHexString(line_count * 16),8,"0")+"�F");
			
			//�Ōオ�����ꍇ
			if( (line_count * 16 + 16) < bytes ){
				for(int i=0;i < 15;i++){
					System.out.print(space(hex(data[line_count * 16 + i]),2,"0") + " ");
				}
					System.out.print(space(hex(data[line_count * 16 + 15]),2,"0"));
			}else{//�t�@�C�����I������Ƃ�
				file_end = false;
				int count = bytes - (16*line_count);
				for(int i=0;i < count -1;i++){
					System.out.print(space(hex(data[line_count * 16 + i]),2,"0") + " ");
				}
					System.out.print(space(hex(data[line_count * 16 + count-1]),2,"0"));
			}
			System.out.print("\n");
			line_count++;
		}
	}
	//�e�L�X�g���`�p
	//value:�����̌��̕���
	//digit�F���킹����������
	//spacer�����߂��镶��
	public static String space(String value,int digit, String spacer){
		String str="";
		int insert_space;//�󔒂����鐔
		insert_space = digit - value.length();
		for(int i=0;i<insert_space;i++){
			str += spacer;
		}
		str += value;
		return str;
	}
	//16�i���ɕϊ�
	public static String hex(byte input){
		String str="";
		int value = (int)input;
		//�}�C�i�X�̐���������C256�𑫂��ƁC�ꉞ�����Ă�D
		if(value < 0){
			value += 256;
		}
		str = Integer.toString(value,16);
		return str;
	}
}