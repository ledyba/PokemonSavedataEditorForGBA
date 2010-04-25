package psi.lib.CalTools;

/**
 * <p>タイトル: ポケモン　セーブデータエディタ</p>
 *
 * <p>説明: </p>
 *
 * <p>著作権: Copyright (c) 2005 PSI</p>
 *
 * <p>会社名: </p>
 *
 * @author 未入力
 * @version 1.0
 */
public class CalcLittleEndian {
    public CalcLittleEndian() {
    }

    /*
       public static long addWord(long a,long b){
      long result = a+b;
      /*if(result > (0x2 << 8)){
           return result - (0x2 << 8);
         }else{
           return result;
         }
         return result & 0xffffffff;
          }*/
     public static byte[] addWord(byte[] one, byte[] two, int size) { //実装('A`)ﾏﾝﾄﾞｸｾ
         //one:(配列：size)
         //two:(配列：size)
         //return：足し算
         int[] tmp = new int[size];
         byte[] sum = new byte[size];
         for (int i = 0; i < size; i++) {
             tmp[i] = tmp[i] + PokeTools.ByteToInt(one[i]) +
                      PokeTools.ByteToInt(two[i]); //とりあえず足す
             if (tmp[i] >= 256) { //ケタがあがる
                 tmp[i] = tmp[i] - 256;
                 if (i < size - 1) { //くりあがるケタがあるなら
                     tmp[i + 1]++;
                 }
             }
         }
         for (int i = 0; i < size; i++) {
             sum[i] = PokeTools.IntToByte(tmp[i]);
         }
         return sum;
     }
     public static int modByteArray(byte[] arr, int num){
         int mod = 0;
         for(int i=arr.length-1;i>=0;i--){
             mod = (PokeTools.ByteToInt(arr[i]) + (mod << 8)) % num;
         }
         return mod;
     }
     /*
       public static long addHalfWord(long a, long b) {
         long result = a + b;
         if(result > 0x1000){
           return result - 0x1000;
              }else{
           return result;
              }
         return result & 0xffff;
       }

       public static long addByte(long a, long b) {
         long result = a + b;
         if(result > 0x100){
           return result - 0x100;
              }else{
           return result;
              }
         return result & 0xff;
       }
      */
}
