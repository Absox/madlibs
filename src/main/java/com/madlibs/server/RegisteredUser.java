package com.madlibs.server;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;

/**
 * Registered user class. For users who have registered accounts.
 * Created by Ran on 12/20/2015.
 */
public class RegisteredUser extends User {

    /**
     * For SHA-256 encrypted, salted password.
     */
    private String saltedHashedPassword;

    /**
     * Salt for password.
     */
    private String salt;

    /**
     *
     * @param username Username.
     * @param password Plaintext password.
     */
    public RegisteredUser(String username, String password) {
        // Call super constructor with username.
        super(username);

        SecureRandom rng = new SecureRandom();
        byte[] passwordBytes = password.getBytes();

        byte[] salt = new byte[passwordBytes.length];
        rng.nextBytes(salt);

        // Add salt to password bytes.
        for (int c = 0; c < passwordBytes.length; c++) {
            passwordBytes[c] += salt[c];
        }

        // Hash password and store.
        this.saltedHashedPassword = DigestUtils.sha256Hex(passwordBytes);
        this.salt = Hex.encodeHexString(salt);

    }

    /**
     *
     * @param password Plaintext password.
     * @return True if this is the same as the original password.
     */
    public boolean validatePassword(String password) throws DecoderException {
        byte[] saltBytes = Hex.decodeHex(this.salt.toCharArray());
        byte[] passwordBytes = password.getBytes();

        // Salt candidate password.
        for (int c = 0; c < passwordBytes.length; c++) {
            passwordBytes[c] += saltBytes[c];
        }

        // Check if hashes are equivalent.
        String saltedHashedCandidatePassword = DigestUtils.sha256Hex(passwordBytes);
        return (this.saltedHashedPassword.equals(saltedHashedCandidatePassword));
    }

}
