
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormInsert")
public class SimpleFormInsert extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormInsert() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String name = request.getParameter("name");
      String author = request.getParameter("author");
      String genre = request.getParameter("genre");
      String reviewer = request.getParameter("reviewer");
      String rating = request.getParameter("rating");
      String review = request.getParameter("review");

      Connection connection = null;
      String insertSql = " INSERT INTO BookReview (Name, Author, Genre, Reviewer, Rating, Review) values (?, ?, ?, ?, ?, ?)";

      try {
         DBConnection.getDBConnection(getServletContext());
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, name);
         preparedStmt.setString(2, author);
         preparedStmt.setString(3, genre);
         preparedStmt.setString(4, reviewer);
         preparedStmt.setString(5, rating);
         preparedStmt.setString(6, review);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Name of Book</b>: " + name + "\n" + //
            "  <li><b>Author</b>: " + author + "\n" + //
            "  <li><b>Genre</b>: " + genre + "\n" + //
            "  <li><b>Name of Reviewer</b>: " + reviewer + "\n" + //
            "  <li><b>Rating (? out of 5)</b>: " + rating + "\n" + //
            "  <li><b>Review/Opinion</b>: " + review + "\n" + //

            "</ul>\n");

      out.println("<a href=/TechExercise/simpleFormSearch.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
