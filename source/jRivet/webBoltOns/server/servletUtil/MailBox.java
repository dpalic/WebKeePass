package webBoltOns.server.servletUtil;

/*
 * $Id: MailBox.java,v 1.1 2007/04/20 19:37:18 paujones2005 Exp $ $Name:  $
 *
 * Copyright  2004, 2005, 2006  www.jrivet.com
 * 
 *   @author P. Jones  
 * 	 @version 2.060719
 *
 * The contents of this file are subject to the Mozilla Public License Version 1.1
 * (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the License.
 *
 * The Original Code is 'jRivet Framework - Java Solutions for Enterprise Applications'.
 *
 * The Initial Developer of the Original Code is Paul Jones. Portions created by
 *  the Initial Developer are Copyright (C) 2004, 2005, 2006  by Paul Jones.
 *
 *  **All Rights Reserved **.
 *
 * Contributor(s): all the names of the contributors are added in the source code
 * where applicable.
 *
 * Alternatively, the contents of this file may be used under the terms of the
 * LGPL license (the "GNU LIBRARY GENERAL PUBLIC LICENSE"), in which case the
 * provisions of LGPL are applicable instead of those above.  If you wish to
 * allow use of your version of this file only under the terms of the LGPL
 * License and not to allow others to use your version of this file under
 * the MPL, indicate your decision by deleting the provisions above and
 * replace them with the notice and other provisions required by the LGPL.
 * If you do not delete the provisions above, a recipient may use your version
 * of this file under either the MPL or the GNU LIBRARY GENERAL PUBLIC LICENSE.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the MPL as stated above or under the terms of the GNU
 * Library General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Library general Public License for more
 * details.
 *
 * If you didn't download this code from the following link, you should check if
 * you aren't using an obsolete version:
 *
 * http://www.jRivet.com/download/
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import webBoltOns.dataContol.DataAccess;

public class MailBox {

	private final static String MAIL_HOST = "mail.smtp.host";

	private DataAccess dataAccess;

	private javax.mail.internet.MimeMessage newMessage;

	private boolean loginSuccessful = false, multipart = false;

	private javax.mail.Message[] messages;

	private String outMailServer, outMailTransport;

	private ByteArrayOutputStream outPDF;

	private String email, to, cc, bcc, subject, body, attachmentFilePath;

	private java.util.Properties properties;

	private javax.mail.Session session;


	public MailBox(DataAccess da) {
		dataAccess = da;
		outMailServer = da.getEmailServer();
		email = da.getEmailFromAddress();
	}
	
	public MailBox(String outEmail, String outSever, DataAccess da) {
		dataAccess = da;
		outMailServer = outSever;
		email = outEmail;
	}

	private void createSession() {
		properties = System.getProperties();
		properties.put(MAIL_HOST, outMailServer);
		session = Session.getInstance(properties, null);
	}

	private void createMessage() throws AddressException, MessagingException {
		newMessage = new MimeMessage(session);
		String from = new String(email);
		newMessage.setFrom(new InternetAddress(from));
		newMessage.setSubject(subject);

		Address[] toAddresses = InternetAddress.parse(to);
		newMessage.setRecipients(Message.RecipientType.TO, toAddresses);

		Address[] ccAddresses = InternetAddress.parse(cc);
		newMessage.setRecipients(Message.RecipientType.CC, ccAddresses);

		Address[] bccAddresses = InternetAddress.parse(bcc);
		newMessage.setRecipients(Message.RecipientType.BCC, bccAddresses);
	}

	public String getAttachmentFilePath() {
		return attachmentFilePath;
	}

	public ByteArrayOutputStream getAttachmentPDF() {
		return outPDF;
	}

	public String getBcc() {
		return bcc;
	}

	public String getBody() {
		return body;
	}

	public String getCc() {
		return cc;
	}

	public String getEmailAddress() {
		return email;
	}

	public boolean getLoginSuccessful() {
		return loginSuccessful;
	}

	public Message[] getMessages() {
		return messages;
	}

	public String getOutMailServer() {
		return outMailServer;
	}

	public String getOutMailTransport() {
		return outMailTransport;
	}

	public String getSubject() {
		return subject;
	}

	public String getTo() {
		return to;
	}

	public boolean isMultiPart() {
		return multipart;
	}

	public boolean sendFileAttachmentEmail(String toEmail, String ccEmail,
			String bccEmail, String subject, StringBuffer textBody,
			String filePath) {
		try {
			setTo(toEmail);
			setCc(ccEmail);
			setBcc(bccEmail);
			setSubject(subject);
			setBody(textBody.toString());
			setAttachmentFilePath(filePath);
			sendFileAttachmentMessage();
			return true;

		} catch (AddressException e) {
			dataAccess.logMessage("Mailbox.sendFileAttachmentEmail : " + e);
			return false;
		} catch (SendFailedException e) {
			dataAccess.logMessage("Mailbox.sendFileAttachmentEmail : " + e);
			return false;
		} catch (MessagingException e) {
			dataAccess.logMessage("Mailbox.sendFileAttachmentEmail : " + e);
			return false;
		}
	}

	public boolean sendFileAttachmentEmail(String toEmail, String ccEmail,
			String subject, StringBuffer textBody, String filePath) {
		return sendFileAttachmentEmail(toEmail, ccEmail, "", subject, textBody,
				filePath);
	}

	public boolean sendFileAttachmentEmail(String toEmail, String subject,
			StringBuffer textBody, String filePath) {
		return sendFileAttachmentEmail(toEmail, "", "", subject, textBody,
				filePath);
	}

	public void sendFileAttachmentMessage() throws AddressException,
			SendFailedException, MessagingException {

		createSession();
		createMessage();

		MimeBodyPart mbp1 = new MimeBodyPart();
		mbp1.setText(body);

		MimeBodyPart mbp2 = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(attachmentFilePath);
		mbp2.setDataHandler(new DataHandler(fds));
		mbp2.setFileName(attachmentFilePath);
		//and its parts to it
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(mbp1);
		mp.addBodyPart(mbp2);
		newMessage.setContent(mp);
		Transport.send(newMessage, newMessage.getAllRecipients());
	}

	public boolean sendPDFAttachmentEmail(String toEmail, String ccEmail,
			String bccEmail, String subject, StringBuffer textBody,
			           ByteArrayOutputStream outPDF) {

		boolean rtn = false;
		FileOutputStream outFile;
		try {

			String fileName = "AT" + new SimpleDateFormat("yyyyMMMddhhmmss")
							.format(new Date()) + ".pdf";

			outFile = new FileOutputStream(fileName);
			outFile.write(outPDF.toByteArray());
			outFile.flush();
			outFile.close();
			rtn = sendFileAttachmentEmail(toEmail, ccEmail, bccEmail, subject,
					textBody, fileName);

			File file = new File(fileName);
			file.delete();

		} catch (FileNotFoundException e) {
			dataAccess.logMessage("Mailbox.sendPDFAttachmentEmail : " + e);
			return false;
		} catch (IOException e) {
			dataAccess.logMessage("Mailbox.sendPDFAttachmentEmail : " + e);
			return false;
		}
		return rtn;
	}

	public boolean sendPDFAttachmentEmail(String toEmail, String ccEmail,
			String subject, StringBuffer textBody, ByteArrayOutputStream outPDF) {
		return sendPDFAttachmentEmail(toEmail, ccEmail, "", subject, textBody,
				outPDF);
	}

	public boolean sendPDFAttachmentEmail(String toEmail, String subject,
			StringBuffer textBody, ByteArrayOutputStream outPDF) {
		return sendPDFAttachmentEmail(toEmail, "", "", subject, textBody,
				outPDF);
	}

	public boolean sendSimpleEmail(String toEmail, String ccEmail,
			String bccEmail, String subject, StringBuffer textBody) {
		try {
			setTo(toEmail);
			setCc(ccEmail);
			setBcc(bccEmail);
			setSubject(subject);
			setBody(textBody.toString());
			sendSimpleMessage();
			return true;
		} catch (AddressException e) {
			dataAccess.logMessage("Mailbox.sendSimpleEmail : " + e);
			return false;
		} catch (SendFailedException e) {
			dataAccess.logMessage("Mailbox.sendSimpleEmail : " + e);
			return false;
		} catch (MessagingException e) {
			dataAccess.logMessage("Mailbox.sendSimpleEmail : " + e);
			return false;
		}
	}

	public boolean sendSimpleEmail(String toEmail, String ccEmail,
			String subject, StringBuffer textBody) {
		return sendSimpleEmail(toEmail, ccEmail, "", subject, textBody);
	}

	public boolean sendSimpleEmail(String toEmail, String subject,
			StringBuffer textBody) {
		return sendSimpleEmail(toEmail, "", "", subject, textBody);
	}

	public void sendSimpleMessage() throws AddressException,
			SendFailedException, MessagingException {

		createSession();
		createMessage();
		newMessage.setText(body);
		Transport.send(newMessage, newMessage.getAllRecipients());
	}

	public void setAttachmentFilePath(String aFilePath) {
		attachmentFilePath = aFilePath;
	}

	public void setAttachmentPDF(ByteArrayOutputStream oPDF) {
		outPDF = oPDF;
	}

	public void setBcc(String s) {
		bcc = s;
	}

	public void setBody(String s) {
		body = s;
	}

	public void setCc(String s) {
		cc = s;
	}

	public void setDataAccess(DataAccess da) {
		dataAccess = da;
	}

	public void setEmailAddress(String em) {
		email = em;
	}


	public void setNewMessage(MimeMessage msg) {
		newMessage = msg;
	}

	public void setOutMailServer(String s) {
		outMailServer = s;
	}

	public void setOutMailTransport(String trn) {
		outMailTransport = trn;
	}

	public void setSubject(String s) {
		subject = s;
	}

	public void setTo(String s) {
		to = s;
	}

}
