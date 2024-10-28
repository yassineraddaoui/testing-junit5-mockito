package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String FIND_OWNERS = "owners/findOwners";
    private static final String OWNERS_LIST = "owners/ownersList";
    private static final String REDIRECT_OWNERS_1 = "redirect:/owners/1";
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

    @Mock
    OwnerService service;

    @InjectMocks
    OwnerController controller;

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        lenient().when(service.findAllByLastNameLike(stringArgumentCaptor.capture()))
                .thenAnswer(invocation -> {
                    String argument = stringArgumentCaptor.getValue();
                    switch (argument) {
                        case "%Doe%":
                            return List.of(new Owner(1L, "John", "Doe"));
                        case "%DontFindMe%":
                            return Collections.emptyList();
                        case "%FindMultiple%":
                            return List.of(new Owner(1L, "John", "Doe"),
                                    new Owner(2L, "John", "Doe"));
                    }
                    throw new RuntimeException("No case satisfied");
                });
    }

    @Test
    void processCreationFormHasNoErrors() {
        Owner owner = new Owner(1L, "John", "Doe");
        given(bindingResult.hasErrors()).willReturn(false);
        given(service.save(any())).willReturn(owner);

        String result = controller.processCreationForm(owner, bindingResult);

        then(service).should().save(owner);
        assertThat(result).isEqualTo(REDIRECT_OWNERS_1);
    }

    @Test
    void processCreationFormHasErrors() {
        Owner owner = new Owner(1L, "John", "Doe");
        given(bindingResult.hasErrors()).willReturn(true);

        String result = controller.processCreationForm(owner, bindingResult);

        assertThat(result).isEqualTo(VIEWS_OWNER_CREATE_OR_UPDATE_FORM);
        then(service).should(never()).save(owner);
    }

    @Test
    @DisplayName("ProcessFindForm: Change argument before passing it to the service")
    void processFindFormWithSingleOwner() {
        Owner owner = new Owner(1L, "John", "Doe");

        String result = controller.processFindForm(owner, bindingResult, model);

        assertThat(stringArgumentCaptor.getValue()).isEqualTo("%Doe%");
        assertThat(result).isEqualTo(REDIRECT_OWNERS_1);
    }

    @Test
    void processFindFormWithNoOwner() {
        Owner owner = new Owner(1L, "John", "DontFindMe");

        String result = controller.processFindForm(owner, bindingResult, model);

        assertThat(stringArgumentCaptor.getValue()).isEqualTo("%DontFindMe%");
        assertThat(result).isEqualTo(FIND_OWNERS);
    }

    @Test
    void processFindFormWithMultipleOwners() {
        Owner owner = new Owner(1L, "John", "FindMultiple");

        String result = controller.processFindForm(owner, bindingResult, model);

        assertThat(result).isEqualTo(OWNERS_LIST);
        assertThat(stringArgumentCaptor.getValue()).isEqualTo("%FindMultiple%");
    }

}
