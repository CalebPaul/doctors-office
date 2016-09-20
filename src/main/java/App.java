import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";


  get("/", (request, response) -> {
    Map<String, Object> model = new HashMap<>();
    model.put("template", "templates/index.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/doctors", (request, response) -> {
    Map<String, Object> model = new HashMap<>();
    String name = request.queryParams("doctorName");
    String focus = request.queryParams("doctorFocus");
    Doctor newDoctor = new Doctor(name, focus);
    newDoctor.save();
    model.put("doctors", Doctor.all());
    model.put("template", "templates/index.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  }
}
