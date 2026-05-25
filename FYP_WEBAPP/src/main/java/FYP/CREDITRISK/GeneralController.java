/* Slency - main  ,  Zhi Ting - home() & ourservice() */

package FYP.CREDITRISK;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/aboutus")
	public String aboutus() {
		return "about_us";
	}

	@GetMapping("/contactus")
	public String contactus() {
		return "contact_us";
	}

	@GetMapping("/login")
	public String login() {
		return "log_in";
	}

	@GetMapping("/403")
	public String e403() {
		return "403";
	}

	@GetMapping("/ourservice")
	public String ourservice() {
		return "our_service";
	}
	
	@GetMapping("/results")
	public String results() {
		return "results";
	}

}