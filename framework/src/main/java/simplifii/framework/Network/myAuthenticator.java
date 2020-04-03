/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplifii.framework.Network;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * 
 * @author GauravKhanna
 */
public class myAuthenticator extends Authenticator {
	public String userName, password;

	public myAuthenticator(String userName, String password) {
		this.userName = userName;
		this.password = password;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password.toCharArray());
	}
}
