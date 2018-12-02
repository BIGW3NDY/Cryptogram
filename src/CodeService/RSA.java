package CodeService;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.*;

public class RSA implements cryptogram{
	public void Generate_key(String pub_key, String pri_key) throws Exception{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(1024);
		KeyPair kp = kpg.genKeyPair();
		
		PublicKey pbkey = kp.getPublic();
		PrivateKey prkey = kp.getPrivate();
		
		FileOutputStream f1 = new FileOutputStream(pub_key);
		ObjectOutputStream b1 = new ObjectOutputStream(f1);
		b1.writeObject(pbkey);
		
		FileOutputStream f2 = new FileOutputStream(pri_key);
		ObjectOutputStream b2 = new ObjectOutputStream(f2);
		b2.writeObject(prkey);
	}
	
//	pub_file is like "Skey_RSA_pub.dat"
//	priv_file is like "Skey_RSA_priv.dat"
	public String encode(String str, String key, int mode) throws Exception{//encode使用公钥
		String file_str = str;
		if(mode == 2) {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(str)));
			String t;
			str = "";
			while((t = in.readLine()) != null)
				str += t;
		}
		
		FileInputStream f = new FileInputStream(key);
		ObjectInputStream b = new ObjectInputStream(f);
		
		RSAPublicKey pbk = (RSAPublicKey)b.readObject();
		BigInteger e = pbk.getPublicExponent();
		BigInteger n = pbk.getModulus();
		System.out.println("e=" + e);
		System.out.println("n=" + n);
		
		byte ptext[] = str.getBytes("UTF8");
		BigInteger m = new BigInteger(ptext);
		BigInteger c = m.modPow(e, n);
		System.out.println("c="+c);
		String cs = c.toString();//瀵嗘枃

		if(mode == 2) {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("encode_"+file_str)));
			out.write(cs,0,cs.length());
			out.close();
			return new String("加密文件为encode_"+file_str);
		}		
		else
			return cs;
		
	}
	
	public String decode(String str, String key, int mode) throws Exception{//decode使用私钥
		BigInteger c;
		
		if(mode == 2) {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(str)));
			String ctext = "";
			String t;
			while((t = in.readLine()) != null)
				ctext += t;
			c = new BigInteger(ctext);
		}
		
		else
			c = new BigInteger(str);
		
		FileInputStream f = new FileInputStream(key);
		ObjectInputStream b = new ObjectInputStream(f);
		RSAPrivateKey prk = (RSAPrivateKey)b.readObject();
		
		BigInteger d = prk.getPrivateExponent();
		BigInteger n = prk.getModulus();
		System.out.println("d="+d);
		System.out.println("n="+n);
	
		BigInteger m = c.modPow(d, n);
		System.out.println("m="+m);
		byte[] mt = m.toByteArray();
		System.out.println("Plan Text is:");
		
		String res = "";
		for(int i=0;i<mt.length;i++){
			res += (char)mt[i];
			System.out.print((char)mt[i]);
		}
		
		if(mode == 2) {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("decode_"+str)));
			out.write(res,0,res.length());
			out.close();
			return new String("解密文件为decode_"+str);
		}
		
		else
			return res;
	}

	@Override
	public void Generate_key(String key) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
//	public static void main(String args[]){
//		try {
////			Generate_key();
////			encode("hello");
////			decode("90101356317514851111497040019371016985222391054046298953340067921957149015819005960151907058887142150488003077792722595284715085734422184183187172262306365153068821659933524805655615318260167862676306571273105422169846809295286920513406776348033688460191573108046224836180838720032759244049729304307610345559");
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
