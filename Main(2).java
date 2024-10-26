// RequestServlet.java
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RequestServlet")
public class RequestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        String softwareName = request.getParameter("softwareName");
        String accessType = request.getParameter("accessType");
        String reason = request.getParameter("reason");

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "INSERT INTO requests (username, software_name, access_type, reason) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, softwareName);
            statement.setString(3, accessType);
            statement.setString(4, reason);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                response.sendRedirect("success.jsp"); // Redirect to a success page
            } else {
                response.getWriter().println("Error: Unable to submit access request.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}


