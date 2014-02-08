package com.sbs.model.crypto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.CertificateValidity;
import sun.security.x509.CertificateVersion;
import sun.security.x509.CertificateX509Key;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

import com.asu.ss.secure_banking_system.model.OTPMailAPI;
import com.sbs.exceptions.DecryptException;
import com.sbs.exceptions.EncryptException;
import com.sbs.model.user.UserManager;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class PkiUtils {

	//private String publicKey;
	//private String privateKey;

	/*public static void main(String[] args) throws EncryptException, DecryptException, KeyPairGeneratorException, RetrievePKIException {
		PkiUtils p = new PkiUtils();
		String userId = "XX";
		p.createUserKeys(userId);
		
		byte[] crypt = p.encrypt("Hello_lush", userId);
		System.out.println(crypt);
		String plain = p.decrypt(crypt, userId);
		System.out.println(plain);
		
		byte[] crypt = p.encrypt("Hello", userId);
		System.out.println(crypt);
		String plain = p.decrypt(crypt, userId);
		System.out.println(plain);
	}*/

	/*public String encrypt(String plainText, String userId) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		//PublicKey pub = getUserPublicKey(userId);
		//return encrypt(plainText, pub);
		PrivateKey priv = getUserPrivateKey(userId);
		return encrypt(plainText, priv);
	}

	public String decrypt(String cryptText, String userId) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		//PrivateKey priv = getUserPrivateKey(userId);
		//return decrypt(cryptText, priv);
		PublicKey pub = getUserPublicKey(userId);
		return decrypt(cryptText, pub);
	}*/

	public String decrypt(byte[] plainText, String userId) {
		try {
			PublicKey pub = getUserPublicKey(userId);
			return decrypt(plainText, pub);
		} catch (DecryptException e) {
			e.printStackTrace();
			return "";
		}
	}

	public byte[] encrypt(String cryptText, String userId) {
		try {
		PrivateKey priv = getUserPrivateKey(userId);
		return encrypt(cryptText, priv);
		} catch (EncryptException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void createUserKeys(String userId) {
		KeyPairGenerator keyGen = null;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
			KeyPair key = keyGen.generateKeyPair();
			KeyFactory factory = KeyFactory.getInstance("RSA");
			BASE64Encoder encoder = new BASE64Encoder();

			X509EncodedKeySpec pubKeySpec = factory.getKeySpec(key.getPublic(),
					X509EncodedKeySpec.class);
			String publicKey = encoder.encode(pubKeySpec.getEncoded());

			PKCS8EncodedKeySpec privKeySpec = factory.getKeySpec(
					key.getPrivate(), PKCS8EncodedKeySpec.class);
			String privateKey = encoder.encode(privKeySpec.getEncoded());
			
			saveUserPki(userId, publicKey, privateKey);
			
			X509Certificate cert = null;
			try {
				String dname = "CN=GroupProject7, L=Tempe, C=USA";
				cert = generateCertificate(dname, key, 100, "SHA1withRSA");
			} catch (GeneralSecurityException | IOException e) {
				e.printStackTrace();
			}
			
			String filePath = "";
			try {
				File file = new File(userId + "_cer.cer");
				if (!file.exists()) {
					file.createNewFile();
				}
				filePath = file.getAbsolutePath();
				System.out.println("FilePath1 = "+filePath);
				FileWriter fw = new FileWriter(filePath);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("-----BEGIN CERTIFICATE-----");
				bw.newLine();
				bw.write(Base64.encode(cert.getEncoded()));
				bw.newLine();
				bw.write("-----END CERTIFICATE-----");
	
				bw.close();
				
				// Send as attachment
				try {
					
					OTPMailAPI.sendMailWithAttachment(UserManager.emailidreturn(userId),
							"Digital Certificate for HappyTT Bank",
							"Please save this Certificate at a secure location",
							filePath, file.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				// Delete file
				file.delete();
			} catch (IOException | CertificateEncodingException c){
				
			}

		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error generating Key Pair for userId: " + userId);
		} catch (InvalidKeySpecException e) {
			System.out.println("Error Saving Keys for userId: " + userId);
		}

	}

	private void saveUserPki(String userId, String publicKey, String privateKey) {
		UserPki u = new UserPki();
		u.setUserId(userId);
		u.setPrivateKey(privateKey);
		u.setPublicKey(publicKey);
		UserPkiHibernateManager.saveUserPki(u);
	}
	
	private X509Certificate generateCertificate(String dn, KeyPair pair, int days, String algorithm)
			throws GeneralSecurityException, IOException
			{
		PrivateKey privkey = pair.getPrivate();
		X509CertInfo info = new X509CertInfo();
		Date from = new Date();
		Date to = new Date(from.getTime() + days * 86400000l);
		CertificateValidity interval = new CertificateValidity(from, to);
		BigInteger sn = new BigInteger(64, new SecureRandom());
		X500Name owner = new X500Name(dn);

		info.set(X509CertInfo.VALIDITY, interval);
		info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(sn));
		info.set(X509CertInfo.SUBJECT, new CertificateSubjectName(owner));
		info.set(X509CertInfo.ISSUER, new CertificateIssuerName(owner));
		info.set(X509CertInfo.KEY, new CertificateX509Key(pair.getPublic()));
		info.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
		AlgorithmId algo = new AlgorithmId(AlgorithmId.md5WithRSAEncryption_oid);
		info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algo));

		// Sign the cert to identify the algorithm that's used.
		X509CertImpl cert = new X509CertImpl(info);
		cert.sign(privkey, algorithm);

		// Update the algorith, and resign.
		algo = (AlgorithmId)cert.get(X509CertImpl.SIG_ALG);
		info.set(CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM, algo);
		cert = new X509CertImpl(info);
		cert.sign(privkey, algorithm);
		//  System.out.println("Suceess");
		return cert;

	}
	
	public PublicKey getUserPublicKey(String userId) {
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			UserPki up = UserPkiHibernateManager.getUserPki(userId);
			String publicKey = up.getPublicKey();
			byte[] keyBytes = decoder.decodeBuffer(publicKey);
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory factory = KeyFactory.getInstance("RSA");
			return factory.generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException e) {
			System.out.println("Invalid public key format for userId :" + userId);
			return null;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid public key algorithm for userId :" + userId);
			return null;
		} catch (IOException e) {
			System.out.println("Error getting public key for userId :" + userId);
			return null;
		}
	}

	public PrivateKey getUserPrivateKey(String userId) {
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			UserPki up = UserPkiHibernateManager.getUserPki(userId);
			String privateKey = up.getPrivateKey();
			byte[] keyBytes = decoder.decodeBuffer(privateKey);
			PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory factory = KeyFactory.getInstance("RSA");
			return factory.generatePrivate(privKeySpec);
		} catch (InvalidKeySpecException e) {
			System.out.println("Invalid public key format for userId :" + userId);
			return null;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid public key algorithm for userId :" + userId);
			return null;
		} catch (IOException e) {
			System.out.println("Error getting public key for userId :" + userId);
			return null;
		}
	}
	

	/*private String encrypt(String plainText, PrivateKey privKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, privKey);
		byte[] cryptBytes = cipher.doFinal(plainText.getBytes("ISO-8859-1"));
		return new String(cryptBytes, "ISO-8859-1");
	}

	private String decrypt(String cryptText, PublicKey pubKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, pubKey);
		byte[] plainBytes = cipher.doFinal(cryptText.getBytes("ISO-8859-1"));
		return new String(plainBytes, "ISO-8859-1");
	}*/
	
	private String decrypt(byte[] plainText, PublicKey pubKey) throws DecryptException{
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, pubKey);
			byte[] cryptBytes = cipher.doFinal(plainText);
			return new String(cryptBytes, "ISO-8859-1");
		} catch (NoSuchAlgorithmException e) {
			throw new DecryptException(
					"Error decrypting by public key: NoSuchAlgorithmException");
		} catch (NoSuchPaddingException e) {
			throw new DecryptException(
					"Error decrypting by public key: NoSuchPaddingException");
		} catch (InvalidKeyException e) {
			throw new DecryptException(
					"Error decrypting by public key: InvalidKeyException");
		} catch (IllegalBlockSizeException e) {
			throw new DecryptException(
					"Error decrypting by public key: DecryptException");
		} catch (BadPaddingException e) {
			throw new DecryptException(
					"Error decrypting by public key: BadPaddingException");
		} catch (UnsupportedEncodingException e) {
			throw new DecryptException(
					"Error decrypting by public key: UnsupportedEncodingException");
		}
	}

	private byte[] encrypt(String cryptText, PrivateKey privKey) throws EncryptException{
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, privKey);
			// byte[] plainBytes = cipher.doFinal(cryptText.getBytes("UTF8"));
			// return new String(plainBytes, "UTF8");
			return cipher.doFinal(cryptText.getBytes("ISO-8859-1"));
		} catch (NoSuchAlgorithmException e) {
			throw new EncryptException(
					"Error decrypting by public key: NoSuchAlgorithmException");
		} catch (NoSuchPaddingException e) {
			throw new EncryptException(
					"Error decrypting by public key: NoSuchPaddingException");
		} catch (InvalidKeyException e) {
			throw new EncryptException(
					"Error decrypting by public key: InvalidKeyException");
		} catch (IllegalBlockSizeException e) {
			throw new EncryptException(
					"Error decrypting by public key: DecryptException");
		} catch (BadPaddingException e) {
			throw new EncryptException(
					"Error decrypting by public key: BadPaddingException");
		} catch (UnsupportedEncodingException e) {
			throw new EncryptException(
					"Error decrypting by public key: UnsupportedEncodingException");
		}
	}
}
