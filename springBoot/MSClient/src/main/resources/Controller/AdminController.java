@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String viewAdministrationDashboard(){
        return "admin/adminDashboard";
    }

}