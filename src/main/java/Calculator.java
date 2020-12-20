import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Calculator", urlPatterns = "/calculate")
public class Calculator extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            printWriter.print(new JSONObject()
                    .put("error", String.format("Error reading request: %s", e.getMessage())));
        }
        JSONObject jsonRequest = new JSONObject(stringBuilder.toString());
        int a = jsonRequest.getInt("a");
        int b = jsonRequest.getInt("b");
        String operator = jsonRequest.getString("operator");
        switch (operator) {
            case "+":
                printWriter.print(new JSONObject().put("result", a + b));
                break;
            case "-":
                printWriter.print(new JSONObject().put("result", a - b));
                break;
            case "*":
                printWriter.print(new JSONObject().put("result", a * b));
                break;
            case "/":
                printWriter.print(new JSONObject().put("result", (double) a / b));
                break;
            case "^":
                printWriter.print(new JSONObject().put("result", Math.pow(a, b)));
                break;
            default:
                printWriter.print(new JSONObject()
                        .put("error", String.format("%s is not an operator or it is not implemented", operator)));
                break;
        }
    }
}