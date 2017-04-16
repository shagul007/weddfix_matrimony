package com.weddfix.web.util;

import org.apache.velocity.app.Velocity;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;

public class ServletListener implements ServletContextListener {
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    Properties p = new Properties();
    p.setProperty("runtime.log", "velocity.log");
    p.setProperty("resource.loader", "webapp");
    p.setProperty("webapp.resource.loader.class","org.apache.velocity.tools.view.WebappResourceLoader");
    p.setProperty("webapp.resource.loader.path", "/WEB-INF/templates/");
    p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.JdkLogChute ");
    p.setProperty("runtime.log", "");

    try {
      Velocity.setApplicationAttribute("javax.servlet.ServletContext", servletContextEvent.getServletContext());
      Velocity.init(p);

    } catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }

  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    //To change body of implemented methods use File | Settings | File Templates.
  }
}
