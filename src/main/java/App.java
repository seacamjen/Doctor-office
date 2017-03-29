import java.util.Map;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;


public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("doctors", Doctor.all());
      model.put("specialties", Specialty.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/doctors", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int specialtyId = Integer.parseInt(request.queryParams("specialty-id"));
      String name = request.queryParams("name");
      Doctor doctor = new Doctor(name, specialtyId);
      doctor.save();
      String url = String.format("/");
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/doctors/:doctor_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Doctor doctor = Doctor.find(Integer.parseInt(request.params(":doctor_id")));
      model.put("doctor", doctor);
      model.put("template", "templates/update-docs.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/doctors/:doctor_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Doctor doctor = Doctor.find(Integer.parseInt(request.params(":doctor_id")));
      doctor.update(name);
      String url = String.format("/");
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
