/* Slency */

package FYP.CREDITRISK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MemberDetailsService implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public MemberDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByUsername(username);
		if (member == null) {
			throw new UsernameNotFoundException("Could not find user");
		}
		return new MemberDetails(member);
	}

}