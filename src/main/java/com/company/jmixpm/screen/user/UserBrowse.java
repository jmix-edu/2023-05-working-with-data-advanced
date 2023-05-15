package com.company.jmixpm.screen.user;

import com.company.jmixpm.app.UsersService;
import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.ui.component.Filter;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("User.browse")
@UiDescriptor("user-browse.xml")
@LookupComponent("usersTable")
@Route("users")
public class UserBrowse extends StandardLookup<User> {

    private Project filterProject;

    @Autowired
    private Filter filter;
    @Autowired
    private UsersService usersService;
    @Autowired
    private DataManager dataManager;

    @Install(to = "usersDl", target = Target.DATA_LOADER)
    private List<User> usersDlLoadDelegate(LoadContext<User> loadContext) {
        LoadContext.Query query = loadContext.getQuery();
        if (filterProject != null && query != null) {
            return usersService.getUsersNotInProject(filterProject, query.getFirstResult(), query.getMaxResults());
        }

        return dataManager.loadList(loadContext);
    }

    public UserBrowse setFilterProject(Project filterProject) {
        this.filterProject = filterProject;
        filter.setVisible(false);
        return this;
    }
}