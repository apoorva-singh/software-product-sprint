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

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String[] greetings = new String[]{"Hello Apoorva!", "¡Hola Apoorva!", "Bonjour le Apoorva!"};
    
    String greeting = greetings[(int)(Math.floor(Math.random() * greetings.length))];

    response.setContentType("text/html;");
    response.getWriter().println(greeting);

    //hard-coded messages to be converted to JSON data for test

    /*
    ArrayList<String> TestMessages= new ArrayList<String>(); 
                TestMessages.add("Apoorva says hello world!"); 
                TestMessages.add("Apoorva dice hola mundo!"); 
                TestMessages.add("Apoorva dit bonjour au monde!"); 
    */

    //ArrayList<String> TestMessages = new ArrayList<>(Arrays.asList("Apoorva says hello world!", "Apoorva dice hola mundo!", "Apoorva dit bonjour au monde!"));
    String[] TestMessages = {"Apoorva says hello world!", "Apoorva dice hola mundo!", "Apoorva dit bonjour au monde!"};
    // Convert the test messages to JSON
    String json = convertToJson(TestMessages);

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

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
}
