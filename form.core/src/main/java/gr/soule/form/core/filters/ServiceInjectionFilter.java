package gr.soule.form.core.filters;

import gr.media24.mSites.core.Settings;
import gr.media24.mSites.core.service.ArticleService;

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

   @Autowired ArticleService articleService;
   @Autowired Settings settings;

   public void init(FilterConfig filterConfig) throws ServletException {
   
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       request.setAttribute("articleService", articleService);
       request.setAttribute("settings", settings);
       chain.doFilter(request, response);
   }

   public void destroy() {
	   
   }
}