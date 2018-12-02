package CodeService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ceasar implements cryptogram{
	public String encode(String str, String key, int mode)throws Exception{
		String file_str = str;
		if(mode == 2) {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(str)));
			String t;
			str = "";
			while((t = in.readLine()) != null)
				str += t;
		}
		int Key = Integer.parseInt(key);
		Key = Key%26;
		String EncodeStr = "";
		int len = str.length();
		for(int i=0;i<len;i++){
			char c = str.charAt(i);
			if(c == ' ')
				continue;
			c += Key;
			if(c > 'z'|| (c>'Z' && c-Key <= 'Z'))
				c -= 26;
			EncodeStr += c;
		}
		if(mode == 2) {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("encode_"+file_str)));
			out.write(EncodeStr,0,EncodeStr.length());
			out.close();
			return new String("加密文件为encode_"+file_str);
		}		
		return EncodeStr;
	}
	
	public String decode(String str, String key, int mode)throws Exception{
		String file_str = str;
		if(mode == 2) {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(str)));
			String t;
			str = "";
			while((t = in.readLine()) != null)
				str += t;
		}
		int Key = Integer.parseInt(key);
		Key = Key%26;
		String DecodeStr = "";
		int len = str.length();
		for(int i=0;i<len;i++){
			char c = str.charAt(i);
			if(c == ' ')
				continue;
			c -= Key;
			if((c < 'a' && c+Key>='a') || c <'A')
				c += 26;
			DecodeStr += c;
		}
		
		if(mode == 2) {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("decode_"+file_str)));
			out.write(DecodeStr,0,DecodeStr.length());
			out.close();
			return new String("加密文件为decode_"+file_str);
		}		
		
		return DecodeStr;
	}
//	public static void main(String args[]){
//		String EncodeStr = encode(1,"liangyuchenshidashabi");
//		System.out.println(EncodeStr);
//		String DecodeStr = decode(1,EncodeStr);
//		System.out.println(DecodeStr);
//	}

	@Override
	public void Generate_key(String key) {
		// TODO Auto-generated method stub
	}

	@Override
	public void Generate_key(String pub_key, String pri_key) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
