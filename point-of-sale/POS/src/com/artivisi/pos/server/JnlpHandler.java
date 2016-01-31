/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ifnu
 */
public class JnlpHandler implements HttpHandler{

    public void handle(HttpExchange xchg) throws IOException {
        try{
            String path  = xchg.getRequestURI().getPath();
            if(path!=null && path.toLowerCase().endsWith("jnlp")){
                Headers headers = xchg.getRequestHeaders();
                List<String> contentDisposition = new ArrayList<String>();
                contentDisposition.add("attachment; filename=arsip.jnlp");
                headers.put("Content-Disposition", contentDisposition);
                List<String> contentType = new ArrayList<String>();
                contentType.add("application/x-java-jnlp-file");
                headers.put("Content-Type", contentType);

                OutputStream outputStream = xchg.getResponseBody();
                InputStream inputStream = getClass().getResourceAsStream("/pos.jnlp");
                //baca dari jar
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArray.write(buffer,0,bytesRead);
                }
                xchg.sendResponseHeaders(200, byteArray.size());
                outputStream.write(byteArray.toByteArray(),0,byteArray.size());
                outputStream.close();
            } else if(path!=null && path.toLowerCase().endsWith("jar")){
                Headers headers = xchg.getRequestHeaders();
                String fileName = path.substring(path.lastIndexOf("/") + 1,path.length());
                List<String> contentDisposition = new ArrayList<String>();
                contentDisposition.add("attachment; filename=" + fileName);
                headers.put("Content-Disposition", contentDisposition);
                List<String> contentType = new ArrayList<String>();
                contentType.add("application/java-archive");
                headers.put("Content-Type", contentType);

                OutputStream outputStream = xchg.getResponseBody();
                xchg.setStreams(xchg.getRequestBody(), outputStream);
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                String baseFolder = "dist/lib/";
                File jar = new File(baseFolder + fileName);
                if(!jar.exists()){
                    jar = new File ("dist/" + fileName); //cek ke folder lain
                }
                if(jar.exists()){
                    //baca dari jar
                    InputStream inputStream = new FileInputStream(jar);
                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        byteArray.write(buffer, 0, bytesRead);
                    }
                    xchg.sendResponseHeaders(200, byteArray.size());
                    outputStream.write(byteArray.toByteArray(),0,byteArray.size());
                } else {
                    xchg.sendResponseHeaders(404, 0);
                }
                outputStream.close();
            }
        } catch(Exception ex ){
            ex.printStackTrace();
            if(ex instanceof IOException){
                throw (IOException)ex;
            }
        }
    }

}
