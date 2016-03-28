package gr.soule.form.core.filters;


import gr.soule.form.core.Settings;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

/**
* @author nk
*/
public class ServiceInjectionFilter implements Filter {

   @Autowired Settings settings;

   public void init(FilterConfig filterConfig) throws ServletException {
   
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       request.setAttribute("settings", settings);
       chain.doFilter(request, response);
   }

   public void destroy() {
	   
   }
}