/*
 * MIT License
 *
 * Copyright (c) 2020 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.iceyleagons.icicle.web.server;

import lombok.SneakyThrows;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;

/**
 * An Easy to use implementation of Jetty
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since  1.3.0-SNAPSHOT"
 */
public class EasyWebServer extends HttpServlet{

    private final Server server;
    private final ServletHandler servletHandler;
    protected static final Map<String,ServletListener> listenerMap;

    static {
        listenerMap = new HashMap<>();
    }

    /**
     * Creates an unsecure server, please refer to {@link #EasyWebServer(int, SslContextFactory.Server)} for https protocol
     *
     * @param port the port
     */
    public EasyWebServer(int port) {
        server = new Server();
        servletHandler = new ServletHandler();

        ServerConnector serverConnector = new ServerConnector(server);
        serverConnector.setPort(port);
        server.setConnectors(new Connector[]{getConnector(port)});
        server.setHandler(servletHandler);
        server.setStopAtShutdown(true);

    }

    /**
     * Creates an SSL enabled server
     *
     * @param port the port
     * @param sslContextFactory the {@link SslContextFactory.Server}
     */
    public EasyWebServer(int port, SslContextFactory.Server sslContextFactory) {
        server = new Server();
        servletHandler = new ServletHandler();

        ServerConnector serverConnector = new ServerConnector(server);
        serverConnector.setPort(port);
        server.setConnectors(new Connector[]{getSSLConnector(port,sslContextFactory)});
        server.setHandler(servletHandler);
        server.setStopAtShutdown(true);
    }

    private ServerConnector getConnector(int port) {
        ServerConnector serverConnector = new ServerConnector(server);
        serverConnector.setPort(port);
        return serverConnector;
    }

    private ServerConnector getSSLConnector(int port, SslContextFactory.Server sslContextFactory) {
        SslConnectionFactory connector = new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString());
        ServerConnector serverConnector = new ServerConnector(server,connector);
        serverConnector.setPort(port);
        return serverConnector;
    }

    /**
     * Registers a new {@link ServletListener}
     *
     * @param servletListener the servletListener
     */
    public void registerListener(ServletListener servletListener) {
        for (String supportedEndpoint : servletListener.getSupportedEndpoints()) {
            ListenerExecutor listenerExecutor = new ListenerExecutor(); //We need to do this, because multiple servlets cannot be assigned to one class
            listenerMap.put(supportedEndpoint,servletListener);
            addServlet(listenerExecutor.getClass(),"/"+supportedEndpoint);
        }
    }

    /**
     * Adds a servlet to the server
     * {@link #registerListener(ServletListener)} is preferred
     *
     * @param servlet the servlet
     * @param mapping the mapping
     */
    public void addServlet(Class<? extends Servlet> servlet, String mapping) {
        servletHandler.addServletWithMapping(servlet,mapping);
    }

    /**
     * Starts the server
     */
    @SneakyThrows
    public void start() {
        server.start();
    }
}



