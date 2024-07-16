package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.service.MealService;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping()
    public String getAll(Model model) {
        log.info("meals");
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @RequestMapping("delete")
    public String remove(@RequestParam int id) {
        super.delete(id);
        return "redirect:/meals";
    }
}
