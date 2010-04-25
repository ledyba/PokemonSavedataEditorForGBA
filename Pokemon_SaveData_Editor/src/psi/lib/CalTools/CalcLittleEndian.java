package psi.lib.CalTools;

/**
 * <p>�^�C�g��: �|�P�����@�Z�[�u�f�[�^�G�f�B�^</p>
 *
 * <p>����: </p>
 *
 * <p>���쌠: Copyright (c) 2005 PSI</p>
 *
 * <p>��Ж�: </p>
 *
 * @author ������
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
     public static byte[] addWord(byte[] one, byte[] two, int size) { //����('A`)���޸�
         //one:(�z��Fsize)
         //two:(�z��Fsize)
         //return�F�����Z
         int[] tmp = new int[size];
         byte[] sum = new byte[size];
         for (int i = 0; i < size; i++) {
             tmp[i] = tmp[i] + PokeTools.ByteToInt(one[i]) +
                      PokeTools.ByteToInt(two[i]); //�Ƃ肠��������
             if (tmp[i] >= 256) { //�P�^��������
                 tmp[i] = tmp[i] - 256;
                 if (i < size - 1) { //���肠����P�^������Ȃ�
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
