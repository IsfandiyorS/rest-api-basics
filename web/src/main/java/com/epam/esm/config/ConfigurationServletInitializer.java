package com.epam.esm.config;

import org.springframework.lang.NonNull;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Class {@code ConfigurationServletInitializer} contains spring configuration for web subproject.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public class ConfigurationServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfiguration.class};
    }

    @NonNull
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @NonNull
    @Override
    protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        DispatcherServlet ds = new DispatcherServlet(servletAppContext);
        ds.setThrowExceptionIfNoHandlerFound(true);
        return ds;
    }
}
