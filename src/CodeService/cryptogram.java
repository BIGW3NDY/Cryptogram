package CodeService;

public interface cryptogram {
	public abstract void Generate_key(String key) throws Exception;
	public abstract void Generate_key(String pub_key, String pri_key) throws Exception;
	public abstract String encode(String str, String key, int mode) throws Exception;
	public abstract String decode(String str, String key, int mode) throws Exception;
	// mode 1 means that encode/decode from string
	// mode 2 means that encode/decode from file
}


