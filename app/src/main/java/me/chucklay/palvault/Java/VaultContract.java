package me.chucklay.palvault.Java;

/**
 * Created by Charlie on 1/8/2016.
 */
public class VaultContract {
    public static abstract class NewVaultInfo {
        public static final String USER_NAME = "user_name";
        public static final String PUBLIC_KEY = "public_key";
        public static final String LATEST_INTERACTION = "latest_interaction";
        public static final String REQUIRES_ALERT = "requires_alert";

        public static final String DATABASE_NAME = "vaults_info";
    }
}
