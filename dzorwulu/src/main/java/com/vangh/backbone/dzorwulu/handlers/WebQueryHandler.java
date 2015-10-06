package com.vangh.backbone.dzorwulu.handlers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.logging.Logger;

/**
 * Created by janet on 10/6/15.
 */
@WebServlet(value="/WebQueryHandler/*", loadOnStartup = 1)
public class WebQueryHandler extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(WebQueryHandler.class.getName());
    private String propertyFile =  "/WEB-INF/classes/troutmonitoring.properties";

    public void init(){

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            handleGet(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
            handlePost(request, response);
    }

    private void handleGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
        String[] query = request.getPathInfo().split("/");
    }

    private void handlePost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = request.getPathInfo();
    }
}
