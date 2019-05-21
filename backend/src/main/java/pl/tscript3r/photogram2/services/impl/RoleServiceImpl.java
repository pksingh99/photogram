package pl.tscript3r.photogram2.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tscript3r.photogram2.domains.Role;
import pl.tscript3r.photogram2.domains.User;
import pl.tscript3r.photogram2.exceptions.NotFoundPhotogramException;
import pl.tscript3r.photogram2.repositories.RoleRepository;
import pl.tscript3r.photogram2.services.RoleService;

import javax.validation.constraints.NotNull;
import java.security.Principal;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getDefault() {
        return getByName("USER");
    }

    @Override
    public Role getByName(final String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundPhotogramException(String.format("Role firstname=%s not found", name)));
    }

    @Override
    public Boolean isAdmin(User user) {
        return user.getRoles().contains(getByName("ADMIN"));
    }

    @Override
    public void accessValidation(@NotNull Operations operations, Principal principal, Long userId) {

    }

}
