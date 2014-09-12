package webBoltOns.server.servletUtil;

/*
 * $Id: DaemonRemoteServlet.java,v 1.1 2007/04/20 19:37:18 paujones2005 Exp $ $Name:  $
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


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * A superclass for any HTTP servlet that wishes to act as an RMI server and,
 * additionally, accept raw socket connections. Includes the functionality from
 * both RemoteHttpServlet and DaemonHttpServlet, by extending DaemonHttpServlet
 * and re-implementing RemoteHttpServlet. *
 *  
 */
public abstract class DaemonRemoteServlet extends DaemonServlet
		implements Remote {
	/**
	 * The registry for the servlet
	 */
	protected Registry registry;

	private int registryPort, socketPort;

	/**
	 * Binds the servlet to the registry. Creates the registry if necessary.
	 * Logs any errors.
	 */
	protected void bind() {
		// Try to find the appropriate registry already running
		try {
			registry = LocateRegistry.getRegistry(getRegistryPort());
			registry.list(); // Verify it's alive and well
		} catch (Exception e) {
			// Couldn't get a valid registry
			registry = null;
		}

		// If we couldn't find it, we need to create it.
		// (Equivalent to running "rmiregistry")
		if (registry == null) {
			try {
				registry = LocateRegistry.createRegistry(getRegistryPort());
			} catch (Exception e) {
				log("Could not get or create RMI registry on port "
						+ getRegistryPort() + ": " + e.getMessage());
				return;
			}
		}

		// If we get here, we must have a valid registry.
		// Now register this servlet instance with that registry.
		try {
			registry.rebind(getRegistryName(), this);
			String list[] = registry.list();
			log("Starting RMI Services on Port: " + getRegistryPort());
			for (int l = 0; l < list.length; l++)
				log("Binding class: " + list[l]);
		} catch (Exception e) {
			log("humbug Could not bind to RMI registry: " + e.getMessage());
			return;
		}
	}

	/**
	 * Halts the servlet's RMI operations and halts the thread listening for
	 * socket connections. Subclasses that override this method must be sure to
	 * first call <tt>super.destroy()</tt>.
	 */
	public void destroy() {
		super.destroy();
		unbind();
	}

	/**
	 * Returns the name under which the servlet should be bound in the registry.
	 * By default the name is the servlet's class name. This can be overridden
	 * with the <tt>registryName</tt> init parameter.
	 * 
	 * @return the name under which the servlet should be bound in the registry
	 */
	protected String getRegistryName() {
		// First name choice is the "registryName" init parameter
		String name = getInitParameter("registryName");
		if (name != null) {
			return name;
		}

		// Fallback choice is the name of this class
		return this.getClass().getName();
	}

	/**
	 * Returns the port where the registry should be running. By default the
	 * port is the default registry port (1099). This can be overridden with the
	 * <tt>registryPort</tt> init parameter.
	 * 
	 * @return the port for the registry
	 */
	protected int getRegistryPort() {
		// First port choice is the "registryPort" init parameter
		try {
			return Integer.parseInt(getInitParameter("registryPort"));
		}
		// Fallback choice
		catch (NumberFormatException e) {
			return registryPort;
		}
	}

	/**
	 * Begins the servlet's RMI operations and begins a thread listening for
	 * socket connections. Subclasses that override this method must be sure to
	 * first call <tt>super.init(config)</tt>.
	 * 
	 * @param config
	 *            the servlet config
	 * @exception ServletException
	 *                if a servlet exception occurs
	 */
	public void init(ServletConfig config) throws ServletException {

		this.registryPort = Registry.REGISTRY_PORT;
		this.socketPort = super.DEFAULT_PORT;

		super.init(config);
		try {
			UnicastRemoteObject.exportObject(this);
			bind();
		} catch (RemoteException e) {
			log("Problem binding to RMI registry: " + e.getMessage());
		}
	}

	public void init(ServletConfig config, int registryPort, int socketPort)
			throws ServletException {

		this.registryPort = registryPort;
		this.socketPort = socketPort;

		super.init(config, this.socketPort);
		try {
			UnicastRemoteObject.exportObject(this);
			bind();
		} catch (RemoteException e) {
			log("Problem binding to RMI registry: " + e.getMessage());
		}
	}

	/**
	 * Unbinds the servlet from the registry. Logs any errors.
	 */
	protected void unbind() {
		try {
			if (registry != null) {
				registry.unbind(getRegistryName());
			}
		} catch (Exception e) {
			log("Problem unbinding from RMI registry: " + e.getMessage());
		}
	}

}