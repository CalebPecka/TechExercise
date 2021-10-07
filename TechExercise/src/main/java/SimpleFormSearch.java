import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormSearch")
public class SimpleFormSearch extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormSearch() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String name = request.getParameter("name");
      String author = request.getParameter("author");
      String genre = request.getParameter("genre");
      search(name, author, genre, response);
   }

   void search(String name, String author, String genre, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection(getServletContext());
         connection = DBConnection.connection;

         if (author.isEmpty() && genre.isEmpty() && name.isEmpty()) {
            String selectSQL = "SELECT * FROM BookReview";
            preparedStatement = connection.prepareStatement(selectSQL);
         }
         else if (name.isEmpty() && genre.isEmpty()){
            String selectSQL = "SELECT * FROM BookReview WHERE Author LIKE ?";
            String BookAuthor = author + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, BookAuthor);
         }
         else if (author.isEmpty() && genre.isEmpty()){
             String selectSQL = "SELECT * FROM BookReview WHERE Name LIKE ?";
             String BookName = name + "%";
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, BookName);
          }
         else if (author.isEmpty() && name.isEmpty()){
             String selectSQL = "SELECT * FROM BookReview WHERE Genre LIKE ?";
             String BookGenre = genre + "%";
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, BookGenre);
          }
         else if (author.isEmpty()){
             String selectSQL = "SELECT * FROM BookReview WHERE Name LIKE ? AND Genre LIKE ?";
             String BookName = name + "%";
             String BookGenre = genre + "%";
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, BookName);
             preparedStatement.setString(2, BookGenre);
          }
         else if (genre.isEmpty()){
             String selectSQL = "SELECT * FROM BookReview WHERE Name LIKE ? AND Author LIKE ?";
             String BookName = name + "%";
             String BookAuthor = author + "%";
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, BookName);
             preparedStatement.setString(2, BookAuthor);
          }
         else if (name.isEmpty()){
             String selectSQL = "SELECT * FROM BookReview WHERE Author LIKE ? AND Genre LIKE ?";
             String BookAuthor = author + "%";
             String BookGenre = genre + "%";
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, BookAuthor);
             preparedStatement.setString(2, BookGenre);
          }
         else {
        	 String selectSQL = "SELECT * FROM BookReview WHERE Name LIKE ? AND Author LIKE ? AND Genre LIKE ?";
             String BookName = name + "%";
        	 String BookAuthor = author + "%";
             String BookGenre = genre + "%";
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, BookName);
             preparedStatement.setString(2, BookAuthor);
             preparedStatement.setString(3, BookGenre);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
             String BookName = rs.getString("name").trim();
             String BookAuthor = rs.getString("author").trim();
             String BookGenre = rs.getString("genre").trim();
             String BookReviewer = rs.getString("reviewer").trim();
             String BookRating = rs.getString("rating").trim();
             String BookReview = rs.getString("review").trim();
             
             out.println("Name of Book: " + BookName + ", ");
             out.println("Author: " + BookAuthor + ", ");
             out.println("Genre: " + BookGenre + ", ");
             out.println("Name of Reviewer: " + BookReviewer + ", ");
             out.println("Rating (? out of 5): " + BookRating + ", ");
             out.println("Review/Opinion: " + BookReview + "<br>");
             
             //if ( (email.isEmpty() && number.isEmpty()) || mail.contains(email) || phone.contains(number)) {
             //   out.println("ID: " + id + ", ");
             //   out.println("User: " + userName + ", ");
             //   out.println("Email: " + mail + ", ");
             //   out.println("Phone: " + phone + "<br>");
             //}
          }
         out.println("<a href=/TechExercise/simpleFormSearch.html>Search Data</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
