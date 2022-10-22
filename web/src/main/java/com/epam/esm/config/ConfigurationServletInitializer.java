package com.epam.esm.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ConfigurationServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfiguration.class};
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

//    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
//        DispatcherServlet ds = new DispatcherServlet(servletAppContext);
//        ds.setThrowExceptionIfNoHandlerFound(true);
//        return ds;
//    }
}
