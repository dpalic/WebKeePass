package webBoltOns.server.servletUtil;

/*
 * $Id: DaemonServlet.java,v 1.1 2007/04/20 19:37:18 paujones2005 Exp $ $Name:  $
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


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public abstract class DaemonServlet extends HttpServlet {
	private Thread daemonRunner;

	public int DEFAULT_PORT = 1313;

	private int socketPort;

	public void destroy() {
		try {
			daemonRunner = null;
		} catch (Exception e) {
			log("Problem stopping server socket daemon thread: "
					+ e.getClass().getName() + ": " + e.getMessage());
		}
	}

	protected int getSocketPort() {
		try {
			return Integer.parseInt(getInitParameter("socketPort"));
		} catch (NumberFormatException e) {
			return socketPort;
		}
	}

	abstract public void handleClient(Socket client);

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.socketPort = DEFAULT_PORT;
		try {
			log("Starting Socket Services - on Port: " + getSocketPort());
			daemonRunner = new DaemonRunner(this);
			daemonRunner.start();
		} catch (Exception e) {
			log("Problem starting socket server daemon thread"
					+ e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public void init(ServletConfig config, int socketPort)
			throws ServletException {

		super.init(config);
		this.socketPort = socketPort;
		try {
			//log("Starting Socket Services - on Port: " + getSocketPort());
			daemonRunner = new DaemonRunner(this);
			daemonRunner.start();
		} catch (Exception e) {
			log("Problem starting socket server daemon thread"
					+ e.getClass().getName() + ": " + e.getMessage());
		}
	}
}


class DaemonRunner extends Thread {

	private ServerSocket serverSocket;

	private DaemonServlet servlet;

	public DaemonRunner(DaemonServlet servlet) {
		this.servlet = servlet;
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(servlet.getSocketPort());
		} catch (Exception e) {
			servlet.log("Problem establishing server socket: "
					+ e.getClass().getName() + ": " + e.getMessage());
			return;
		}

		try {
			while (true) {
				try {
					servlet.handleClient(serverSocket.accept());
				} catch (IOException ioe) {
					servlet.log("Problem accepting client's socket connection: "
									+ ioe.getClass().getName()
									+ ": "
									+ ioe.getMessage());
				}
			}
		} catch (ThreadDeath e) {
			// When the thread is killed, close the server socket
			try {
				serverSocket.close();
			} catch (IOException ioe) {
				servlet.log("Problem closing server socket: "
						+ ioe.getClass().getName() + ": " + ioe.getMessage());
			}
		}
	}
}

