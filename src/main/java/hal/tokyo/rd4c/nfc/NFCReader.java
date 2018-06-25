/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hal.tokyo.rd4c.nfc;

/**
 *
 * @author tame
 */
public class NFCReader {

    /* 500kHz インスタンス生成 */
    private RaspRC522 rc522;
    /* データ文字列格納用 */
    private String strData, BGM;
    /* セクタ、ブロック */
    private byte sector = 1, block = 3;
    /* tagit -> UIDデータ */
    private byte tagid[] = new byte[5];
    /* BGMデータ取得 */
    private byte data[] = new byte[16];

    /* return :カードを読み込めなかったら-1を返す */
    public int readBGMNum() throws InterruptedException {
        int Bnum =  -1;
        rc522 = new RaspRC522();
        rc522.Select_MirareOne(tagid);
        /* データ読み込み */
        rc522.Read(sector, block, data);
        /* 文字列化 */
        strData = bytesToHex(data);
        /* データの有無検出 */
        if (strData.matches("656E" + ".+?" + "FE" + ".*")) {
            BGM = strData.substring(strData.indexOf("656E") + 4, strData.indexOf("FE"));
            Bnum = BGMNum(BGM);
        } else {
            /* Error */
            System.out.println("読み込めません・・・");
        }
        return Bnum;
    }

    private String bytesToHex(byte[] bytes) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private int BGMNum(String data) {
        String[] s = new String[10];
        String[] num = new String[1];
        num[0] = "";

        for (int cnt = 1; cnt < data.length(); cnt++) {
            s[cnt] = data.substring(cnt, cnt + 1);
            if (cnt % 2 == 1) {
                num[0] += s[cnt];
            }
        }
        return Integer.parseInt(num[0]);
    }
}
