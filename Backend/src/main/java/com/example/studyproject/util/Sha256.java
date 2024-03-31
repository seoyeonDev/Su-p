package com.example.studyproject.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256 {

	public static String encrypt(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes());
		
		return bytesToHex(md.digest());
	}
	
	public static String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for(byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		
		return sb.toString();
	}
}
