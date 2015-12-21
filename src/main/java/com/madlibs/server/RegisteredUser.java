package com.madlibs.server;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * Registered user class. For users who have registered accounts.
 * Created by Ran on 12/20/2015.
 */
public class RegisteredUser extends User {

    /**
     * Length of salt, in bytes.
     */
    private static final int SALT_LENGTH_BYTES = 16;

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
    public RegisteredUser(String username, String password) throws IOException {
        // Call super constructor with username.
        super(username);

        // Generate salt.
        SecureRandom rng = new SecureRandom();
        byte[] passwordBytes = password.getBytes();
        byte[] saltBytes = new byte[SALT_LENGTH_BYTES];
        rng.nextBytes(saltBytes);

        // Concatenate password and salt bytes
        byte[] concatenatedBytes = concatenate(passwordBytes, saltBytes);

        // Hash password and store.
        this.saltedHashedPassword = DigestUtils.sha256Hex(concatenatedBytes);
        this.salt = Hex.encodeHexString(saltBytes);

    }

    /**
     *
     * @param password Plaintext password.
     * @return True if this is the same as the original password.
     */
    public boolean validatePassword(String password) throws DecoderException, IOException {
        // Concatenate password with salt.
        byte[] saltBytes = Hex.decodeHex(this.salt.toCharArray());
        byte[] passwordBytes = password.getBytes();
        byte[] concatenatedBytes = concatenate(passwordBytes, saltBytes);

        // Check if hashes are equivalent.
        String saltedHashedCandidatePassword = DigestUtils.sha256Hex(concatenatedBytes);
        return (this.saltedHashedPassword.equals(saltedHashedCandidatePassword));
    }

    /**
     * Concatenates two arrays of bytes.
     * @param a First array of bytes.
     * @param b Second array of bytes.
     * @return Concatenated array.
     * @throws IOException Couldn't write to stream.
     */
    private static byte[] concatenate(byte[] a, byte[] b) throws IOException {
        ByteArrayOutputStream concatenatedBytes = new ByteArrayOutputStream();
        concatenatedBytes.write(a);
        concatenatedBytes.write(b);
        return concatenatedBytes.toByteArray();
    }

}
