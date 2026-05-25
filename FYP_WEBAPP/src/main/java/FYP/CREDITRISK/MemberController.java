/* Zhi Ting */

package FYP.CREDITRISK;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberController {

	@Autowired
	private MemberRepository memberRepository;

	@GetMapping("/members")
	public String viewMembers(Model model) {
		List<Member> listMembers = memberRepository.findAll();
		model.addAttribute("listMembers", listMembers);
		return "members";
	}

	@GetMapping("/members/add")
	public String addMember(Model model) {
		model.addAttribute("member", new Member());
		return "sign_up";
	}

	@PostMapping("/members/save")
	public String saveMember(RedirectAttributes redirectAttribute, @Valid Member member, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			System.out.println("error");
			return "sign_up";
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(member.getPassword());
		member.setPassword(encodedPassword);
		member.setRole("ROLE_USER");
		memberRepository.save(member);
		redirectAttribute.addFlashAttribute("success", "Member registered!");
		return "redirect:/";
	}

	@GetMapping("/members/edit/{id}")
	public String editMember(@PathVariable("id") Integer id, Model model) {
		Member member = memberRepository.getReferenceById(id);
		model.addAttribute("member", member);
		return "edit_member";
	}

	@PostMapping("/members/edit/{id}")
	public String saveUpdatedMember(@PathVariable("id") Integer id, @Valid Member member, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
	    if (bindingResult.hasErrors()) {
	        return "edit_member";
	    }
	    Member existingMember = memberRepository.getReferenceById(id);
	    if (!existingMember.getPassword().equals(member.getPassword())) {
	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        String encodedPassword = passwordEncoder.encode(member.getPassword());
	        member.setPassword(encodedPassword);
	    } else {
	        member.setPassword(existingMember.getPassword());
	    }
	    memberRepository.save(member);
	    redirectAttributes.addFlashAttribute("success", "Member updated successfully!");
	    return "redirect:/members";
	}

	@GetMapping("/members/delete/{id}")
	public String deleteMember(@PathVariable("id") Integer id) {
		memberRepository.deleteById(id);
		return "redirect:/members";
	}

}