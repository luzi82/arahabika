package arahabika.server.user;

import java.io.Serializable;
import java.util.HashMap;

public class UserAccountManager {

	private final HashMap<String, HashMap<String, Serializable>> userIdToDataMap;

	static private final UserAccountManager instance = new UserAccountManager();

	private UserAccountManager() {
		userIdToDataMap = new HashMap<String, HashMap<String, Serializable>>();

		// temp code
		try {
			addUser("admin", "aaa");
			addUser("user", "uuu");
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	public void addUser(String userId, String password)
			throws UserExistException {
		if (userIdToDataMap.containsKey(userId))
			throw new UserExistException();
		HashMap<String, Serializable> data = new HashMap<String, Serializable>();
		data.put(ID_USERDATAKEY, userId);
		setPassword(data, password);
		userIdToDataMap.put(userId, data);
	}

	public HashMap<String, Serializable> getUser(String userId)
			throws UserNotExistException {
		HashMap<String, Serializable> ret = userIdToDataMap.get(userId);
		if (ret == null)
			throw new UserNotExistException();
		return ret;
	}

	public boolean userExist(String userId) {
		return userIdToDataMap.containsKey(userId);
	}

	public static boolean checkPassword(HashMap<String, Serializable> userData,
			String password) {
		return password.equals(userData.get(PASSWORD_USERDATAKEY));
	}

	public static void setPassword(HashMap<String, Serializable> userData,
			String password) {
		userData.put(PASSWORD_USERDATAKEY, password);
	}

	public static UserAccountManager instance() {
		return instance;
	}

	class UserExistException extends Exception {
		private static final long serialVersionUID = -1718594740908476999L;

	}

	class UserNotExistException extends Exception {
		private static final long serialVersionUID = -8886825739050747880L;
	}

	public static final String PASSWORD_USERDATAKEY = "UserAccountManager.password";
	public static final String ID_USERDATAKEY = "UserAccountManager.user_id";

}
