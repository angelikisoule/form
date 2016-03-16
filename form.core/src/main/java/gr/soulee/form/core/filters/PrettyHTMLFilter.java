package gr.media24.mSites.core.filters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.SourceFormatter;

/**
 * @author nk
 */
public class PrettyHTMLFilter implements Filter {

   //The filter configuration object we are associated with. If this value is null, this filter instance is not currently configured. 
   private FilterConfig filterConfig = null;

   public void doFilter(ServletRequest request, ServletResponse fResponse, FilterChain chain) throws IOException, ServletException {
       
	   HttpServletResponse response = (HttpServletResponse) fResponse;
       BufferedServletResponse wrapped = new BufferedServletResponse(response);
       
       try {
           chain.doFilter(request, wrapped);
       }
       finally {
           wrapped.finishResponse();
       }
   }

   public void log(String msg) {
       filterConfig.getServletContext().log(msg);
   }

   public void init(FilterConfig filterConfig) throws ServletException {
       this.filterConfig = filterConfig;
   }

   public void destroy() {
	   
   }

   public class BufferedServletResponse extends HttpServletResponseWrapper {

       private HttpServletResponse response;
       protected BufferedServletOutputStream stream = null;
       protected PrintWriter writer = null;
       protected boolean isHTML = false;
       private int originalContentLength = -1;

       public BufferedServletResponse(HttpServletResponse httpServletResponse) {
           super(httpServletResponse);
           this.response = httpServletResponse;
       }

       public BufferedServletOutputStream createOutputStream() throws IOException {
           BufferedServletOutputStream lStream;
           if(this.isHTML) {
               lStream = new BufferedServletOutputStream(this.response);
           }
           else {
               lStream = new BufferedServletOutputStream(this.response, this.response.getOutputStream());
           }
           lStream.setBinary(this.isHTML);
           lStream.setOriginalContentLength(this.originalContentLength);
           return lStream;
       }

       @Override
       public ServletOutputStream getOutputStream() throws IOException {
           if(this.writer != null) {
               throw new IllegalStateException("getWriter() has already been called for this response");
           }
           if(this.stream == null) {
        	   this.stream = createOutputStream();
           }
           return this.stream;
       }

       @Override
       public void setContentType(String type) {
           super.setContentType(type);
           if(type.startsWith("text/html")) {
               this.isHTML = true;
           }
       }

       @Override
       public void setContentLength(int len) {
           if(!this.isHTML) {
               super.setContentLength(len);
           }
           else if(this.stream != null) {
               this.stream.setOriginalContentLength(len);
           }
           else {
               this.originalContentLength = len;
           }
       }

       @Override
       public PrintWriter getWriter() throws IOException {
           if(this.writer != null) {
               return this.writer;
           }
           if(this.stream != null) {
               throw new IllegalStateException("getOutputStream() has already been called for this response");
           }
           this.stream = createOutputStream();
           String charEnc = this.response.getCharacterEncoding();
           if(charEnc != null) {
               this.writer = new PrintWriter(new OutputStreamWriter(this.stream, charEnc));
           }
           else {
               this.writer = new PrintWriter(this.stream);
           }
           return this.writer;
       }

       public void finishResponse() throws IOException {
           if(this.writer != null) {
               this.writer.close();
           }
           else if(this.stream != null) {
               this.stream.close();
           }
       }
   }

   public class BufferedServletOutputStream extends ServletOutputStream {

       protected ByteArrayOutputStream buffer;
       protected HttpServletResponse response;
       protected boolean isHTML;
       private int originalContentLength = -1;
       protected boolean closed;
       private ServletOutputStream origOutputStream;

       BufferedServletOutputStream(HttpServletResponse httpServletResponse) {
           this.buffer = new ByteArrayOutputStream();
           this.response = httpServletResponse;
           this.origOutputStream = null;
       }

       BufferedServletOutputStream(HttpServletResponse httpServletResponse, ServletOutputStream origOutputStream) {
           this.buffer = new ByteArrayOutputStream();
           this.response = httpServletResponse;
           this.origOutputStream = origOutputStream;
       }

       @Override
       public void write(int b) throws IOException {
           if(this.origOutputStream == null) {
               this.buffer.write(b);
           }
           else {
               this.origOutputStream.write(b);
           }
       }

       @Override
       public void close() throws IOException {
           if(this.closed) {
               return; //Do Nothing
           }
           if(this.isHTML && this.origOutputStream == null) {
               Source s = new Source(this.buffer.toString(response.getCharacterEncoding()));
               SourceFormatter sf = new SourceFormatter(s);
               String prettyfiedHTML = sf.toString();

               PrintWriter htmlOut = this.response.getWriter();
               this.response.setContentLength(prettyfiedHTML.getBytes().length);
               htmlOut.write(prettyfiedHTML);
               htmlOut.close();
           }
           else {
               if(this.originalContentLength >= 0) {
            	   this.response.setContentLength(this.originalContentLength);
               }
               if(this.origOutputStream != null) {
            	   this.origOutputStream.write(this.buffer.toByteArray());
               }
           }
           super.close();
           this.closed = true;
       }

       public void setBinary(boolean isHTML) {
           this.isHTML = isHTML;
       }

       protected void setOriginalContentLength(int len) {
           this.originalContentLength = len;
       }

       public boolean isClosed() {
           return this.closed;
       }
   }
}