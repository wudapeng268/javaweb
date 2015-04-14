package com.dreamjust.servlet;

import java.io.IOException;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DateServlet extends HttpServlet{
	public static String path;

	 private Timer timer1 = null;   
     
	    private Task task1;   
	       
	    /**  
	     * Constructor of the object.  
	     */  
	    public DateServlet() {   
	        super();   
	    }   
	  
	    /**  
	     * Destruction of the servlet. 
	 
	     */  
	    public void destroy() {   
	        super.destroy();    
	        if(timer1!=null){   
	            timer1.cancel();   
	        }   
	    }   
	  
	       
	    public void doGet(HttpServletRequest request, HttpServletResponse response)   
	            throws ServletException, IOException {   
	           
	    }   
	  
	       
	    public void doPost(HttpServletRequest request, HttpServletResponse response)   
	            throws ServletException, IOException {   
	        doGet(request, response);          
	    }   
	  
	    // init����������ʱ��   
	    public void init() throws ServletException {   
	    	
	        ServletContext context = getServletContext();   
	         path=context.getRealPath("");  
	        // (trueΪ�ö�ʱ��ˢ�»���)   
	        String startTask = getInitParameter("startTask");   
	                   
	        // ��ʱˢ��ʱ��(����)   
	        Long delay = (long) (1000*60*60*24);   
	           
	        // ������ʱ��   
	        //if(startTask.equals("true"))
	        {   
	            timer1 = new Timer(true);   
	            task1 = new Task(context);   
	            timer1.schedule(task1, 0, delay );   
	        }   
	    }
	    
}
