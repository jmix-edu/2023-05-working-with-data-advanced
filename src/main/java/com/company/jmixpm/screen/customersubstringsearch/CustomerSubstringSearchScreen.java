package com.company.jmixpm.screen.customersubstringsearch;

import com.company.jmixpm.entity.Customer;
import com.google.common.base.Strings;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("CustomerSubstringSearchScreen")
@UiDescriptor("customer-substring-search-screen.xml")
public class CustomerSubstringSearchScreen extends Screen {
    /*@Autowired
    private CollectionLoader<Customer> customersDl;

    @Subscribe("nameFilter")
    public void onNameFilterValueChange(HasValue.ValueChangeEvent<String> event) {
        customersDl.setParameter("name",
                Strings.isNullOrEmpty(event.getValue())
                        ? null
                        : "(?i)%" + event.getValue() + "%");
        customersDl.load();
    }*/
}