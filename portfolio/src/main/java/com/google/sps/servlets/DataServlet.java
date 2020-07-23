// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    /*String[] greetings = new String[]{"Hello Apoorva!", "¡Hola Apoorva!", "Bonjour le Apoorva!"};
    
    String greeting = greetings[(int)(Math.floor(Math.random() * greetings.length))];*/

    //response.sendRedirect("index.html");
    /*
    //hard-coded messages to be converted to JSON data for test

    String[] TestMessages = {"Apoorva says hello world!", "Apoorva dice hola mundo!", "Apoorva dit bonjour au monde!"};
    // Convert the test messages to JSON
    String json = convertToJson(TestMessages);

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
    */
  }

  @Override  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String name = getParameter(request, "name", "");
    String email = getParameter(request, "email", "");
    String message = getParameter(request, "message", "");

    Entity taskEntity = new Entity("Task");
    taskEntity.setProperty("name", name);
    taskEntity.setProperty("email", email);
    taskEntity.setProperty("message", message);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(taskEntity);

    // Respond with the result.
    response.setContentType("text/html;");
    response.getWriter().println("Thank you for your message. You will hear from me soon!");
    response.sendRedirect("index.html");
  }

  /**
   * @return the request parameter, or the default value if the parameter
   *         was not specified by the client
   */
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

  
  /*
  private String convertToJson(String[] TestMessages) {
    String json = "{";
    json += "\"message1\": ";
    json += "\"" + TestMessages[0] + "\"";
    json += ", ";
    json += "\"message2\": ";
    json += "\"" + TestMessages[1] + "\"";
    json += ", ";
    json += "\"message3\": ";
    json += "\"" + TestMessages[2] + "\"";
    json += "}";
    return json;
  }
  */

 
}