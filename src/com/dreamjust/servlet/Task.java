package com.dreamjust.servlet;

import java.util.TimerTask;

import javax.servlet.ServletContext;

import com.dreamjust.dao.impl.UserDAOImpl;

public class Task extends TimerTask{

	private ServletContext context;   
    
    private static boolean isRunning = true;  
    
       
    public Task(ServletContext context){   
        this.context = context;   
    }   
       
           
    @Override  
    public void run() {   
        if(isRunning){   
            UserDAOImpl.UpdateUserSort();
//            System.out.println("-----------------------");
//            System.out.println("ÈÙÓþ°ñÒÑ¸üÐÂ!!!");
        }   
    }   
}
