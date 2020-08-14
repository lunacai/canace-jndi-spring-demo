package com.canace.jndi;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.sql.DataSource;


@Component
public class JndiConfig {

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);

            }

            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();
                resource.setName("jdbcMydb");
                resource.setType(DataSource.class.getName());
                resource.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver");
                resource.setProperty("url", "jdbc:mysql://192.168.1.243:3307/test");
                resource.setProperty("username", "root");
                resource.setProperty("password","123456");
                context.getNamingResources().addResource(resource);
            }
        };
        return tomcatServletWebServerFactory;
    }
    @Bean
    public DataSource jndiDataSource() throws IllegalArgumentException,
            NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();           // create JNDI data source
        bean.setJndiName("java:/comp/env/jdbcMydb");  // jndiDataSource is name of JNDI data source
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(true);
        bean.afterPropertiesSet();
        return (DataSource) bean.getObject();
    }

}
