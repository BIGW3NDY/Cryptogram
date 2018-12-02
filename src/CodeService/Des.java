package CodeService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Des implements cryptogram {

	public void Generate_key(String key) throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance("DESede");
		kg.init(168);
		SecretKey k = kg.generateKey();
		FileOutputStream f = new FileOutputStream(key);
		ObjectOutputStream b = new ObjectOutputStream(f);
		b.writeObject(k);
	}

	// key_file is like "key1.dat"
	public String encode(String str, String key, int mode) throws Exception {
		String file_str = str;
		if (mode == 2) {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(str)));
			String t;
			str = "";
			while ((t = in.readLine()) != null)
				str += t;
		}

		FileInputStream f = new FileInputStream(key);
		ObjectInputStream b = new ObjectInputStream(f);
		Key k = (Key) b.readObject();
		Cipher cp;
		String code = "";

		cp = Cipher.getInstance("DESede");
		cp.init(Cipher.ENCRYPT_MODE, k);
		byte ptext[] = str.getBytes("UTF8");
		for (int i = 0; i < ptext.length; i++)
			System.out.print(ptext[i] + ",");
		System.out.println("");
		byte ctext[] = cp.doFinal(ptext);

		for (int i = 0; i < ctext.length; i++) {
			code += ctext[i] + ",";
		}

		System.out.println(code);
		
		if(mode == 2) {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("encode_"+file_str)));
			out.write(code,0,code.length());
			out.close();
			return new String("加密文件为encode_"+file_str);
		}		
		
		return code;
	}

	public String decode(String str, String key, int mode) throws Exception {
		String file_str = str;
		if (mode == 2) {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(str)));
			String t;
			str = "";
			while ((t = in.readLine()) != null)
				str += t;
		}
		
		FileInputStream f = new FileInputStream(key);
		ObjectInputStream b = new ObjectInputStream(f);
		Key k = (Key) b.readObject();

		// FileInputStream f2 = new FileInputStream("SEnc.dat");
		// int num = f2.available();
		// byte[] ctext = new byte[num];
		// f2.read(ctext);

		String stext[] = str.split(",");
		byte[] ctext = new byte[stext.length];
		for (int i = 0; i < stext.length; i++) {
			ctext[i] = (byte) Integer.parseInt(stext[i]);
		}

		String p = null;

		Cipher cp = Cipher.getInstance("DESede");
		cp.init(Cipher.DECRYPT_MODE, k);
		byte[] ptext = cp.doFinal(ctext);
		p = new String(ptext, "UTF8");
		System.out.println(p);
		
		if(mode == 2) {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("decode_"+file_str)));
			out.write(p,0,p.length());
			out.close();
			return new String("加密文件为decode_"+file_str);
		}		
		
		return p;
	}

	public static void main(String args[]) throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
		// Generate_key();
		// encode("12345","key1.dat");
		// decode("-6,-91,115,84,-114,48,60,-28,","key1.dat");

	}

	@Override
	public void Generate_key(String pub_key, String pri_key) throws Exception {
		// TODO Auto-generated method stub

	}

}
