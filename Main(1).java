// SoftwareServlet.java
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SoftwareServlet")
public class SoftwareServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String softwareName = request.getParameter("softwareName");
        String description = request.getParameter("description");

        // Get access levels (checkboxes may return null if not checked)
        StringBuilder accessLevels = new StringBuilder();
        String[] selectedLevels = request.getParameterValues("accessLevels");
        if (selectedLevels != null) {
            for (String level : selectedLevels) {
                accessLevels.append(level).append(", ");
            }
            // Remove the trailing comma and space
            if (accessLevels.length() > 0) {
                accessLevels.setLength(accessLevels.length() - 2);
            }
        }

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "INSERT INTO software (name, description, access_levels) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, softwareName);
            statement.setString(2, description);
            statement.setString(3, accessLevels.toString());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                response.sendRedirect("success.jsp"); // Redirect to a success page or confirmation page
            } else {
                response.getWriter().println("Error: Unable to create software.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

