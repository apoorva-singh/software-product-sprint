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
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/sentimentanalysis")
public class SentimentAnalysisServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      }

  @Override  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String message = getParameter(request, "message", "");

    Document doc = Document.newBuilder().setContent(message).setType(Document.Type.PLAIN_TEXT).build();
    LanguageServiceClient languageService = LanguageServiceClient.create();
    Sentiment sentiment = languageService.analyzeSentiment(doc).getDocumentSentiment();
    float score = sentiment.getScore();
    float magnitude = sentiment.getMagnitude();
     
    languageService.close();

    // Output the sentiment score as HTML.
    // A real project would probably store the score alongside the content.
    response.setContentType("text/html;");
    response.getWriter().println("<!DOCTYPE html>");
    response.getWriter().println("<html>");
    response.getWriter().println("<head>");
    response.getWriter().println("<meta charset=\"UTF-8\">");
    response.getWriter().println("<title>Sentiment Analysis Result</title>");
    response.getWriter().println("<link rel=\"stylesheet\" href=\"style.css\">");
    response.getWriter().println("<style>");
    response.getWriter().println("body{ background-image: url('/images/background.jpg'); background-repeat: no-repeat; background-attachment: fixed; background-size: cover; } </style>");
    response.getWriter().println("</head>");
    response.getWriter().println("<body>");
    response.getWriter().println("<div id=\"content\" style=\"display: inline-block; position:fixed; top:20%; bottom:0; left:0; right:0;\" class=\"scrollable\">");
    response.getWriter().println("<h1 style=\"text-align:center;\">Sentiment Analysis Result</h1>");
    response.getWriter().println("<p style=\"text-align:center;\">You entered: \"" + message + "\"</p>");
    response.getWriter().println("<p style=\"text-align:center;\">Sentiment: Score " + score + " Magnitude " + magnitude + "</p>");
    response.getWriter().println("<p><i><b>Score</b>: ranges from -1.0 (very negative) to 1.0 (very positive)<br><b>Magnitude</b>: strength of sentiment regardless of score, ranges from 0 to infinity</i><p>");
    response.getWriter().println("<p style=\"text-align:center;\"><a href=\"sentimentanalysis.html\" class=\"button button1\">Back</a></p>");
    response.getWriter().println("</div>");
    response.getWriter().println("</body>");
    response.getWriter().println("</html>");

    Entity taskEntity = new Entity("Task");
    taskEntity.setProperty("message", message);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(taskEntity);


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
}