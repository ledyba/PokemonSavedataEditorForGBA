package poke_save;

import java.io.*;
import java.math.*;
class poke_save{
		public static final int buff = 0x1000;
	public static void main(String[] args){
		if(args[0].equals("") == false || args[0] != null){
		}else{
			System.out.println("オプションを指定してください");
			System.exit(0);
		}
		try{
			//データを格納するバイト配列を作成
			byte[] data = new byte[buff];
			//http://java.sun.com/j2se/1.4/ja/docs/ja/api/java/io/FileInputStream.html
			//http://msugai.fc2web.com/java/IO/file.htm
			FileInputStream  in  = new FileInputStream(args[0]);
			
			//データ表示
			System.out.println("ファイルサイズ："+in.available()+"\n");
						
			//先頭データ抽出
			in.read(data,0x0,0xff0);
			System.out.println("先頭ブロック");
			System.out.println("開始アドレス：0x00000000");
			System.out.println("サイズ：0x00000ff0");
			System.out.println("--------------------------------------------------------------------------------");
			print(data,0,0xff0);//プリント
			System.out.println("--------------------------------------------------------------------------------");
			//ブロックデータ集抽出
			for(int i=0; in.available() > 0;i++){
				//開始アドレス表示
				in.read(data,0,buff); //読み込み
				System.out.println("第"+data[4]+"ブロック");
				System.out.println("開始アドレス："+space(Integer.toHexString((buff * i) + 0xff0),8,"0"));
				System.out.println("サイズ："+space(Integer.toHexString(buff),8,"0"));
				System.out.println("--------------------------------------------------------------------------------");
				print(data,buff * i+0xff0,buff);//プリント
				System.out.println("--------------------------------------------------------------------------------");
System.out.println(in.available()+">"+(buff * i + 0xff0));
			}
			in.close();
		}catch(IOException e){
			System.out.println("エラーが発生しています");
			System.exit(0);
		}
	}
	//画面表示用
	//data:データ
	//base:ベースアドレス
	//bytes:先頭何バイトを表示するか
	public static void print(byte[] data,int base,int bytes){
		int line_count=0;
		boolean file_end = true;
		while(file_end){
			//アドレス表示
			System.out.print(space(Integer.toHexString(line_count * 16 + base),8,"0")+"：");
			//オフセット表示
			System.out.print(space(Integer.toHexString(line_count * 16),8,"0")+"：");
			
			//最後が無い場合
			if( (line_count * 16 + 16) < bytes ){
				for(int i=0;i < 15;i++){
					System.out.print(space(hex(data[line_count * 16 + i]),2,"0") + " ");
				}
					System.out.print(space(hex(data[line_count * 16 + 15]),2,"0"));
			}else{//ファイルが終了するとき
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
	//テキスト整形用
	//value:処理の元の文字
	//digit：あわせたい文字数
	//spacer穴埋めする文字
	public static String space(String value,int digit, String spacer){
		String str="";
		int insert_space;//空白を入れる数
		insert_space = digit - value.length();
		for(int i=0;i<insert_space;i++){
			str += spacer;
		}
		str += value;
		return str;
	}
	//16進数に変換
	public static String hex(byte input){
		String str="";
		int value = (int)input;
		//マイナスの数だったら，256を足すと，一応あってる．
		if(value < 0){
			value += 256;
		}
		str = Integer.toString(value,16);
		return str;
	}
}