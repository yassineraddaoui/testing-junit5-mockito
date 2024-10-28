package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    public static final String FIND_OWNERS = "owners/findOwners";
    public static final String OWNERS_LIST = "owners/ownersList";
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

    @Test
    void processCreationFormBDD() {
        //given
        Owner owner = new Owner(1L, "John", "Doe");
        given(bindingResult.hasErrors()).willReturn(false);
        given(service.save(any())).willReturn(owner);
        //when
        var result = controller.processCreationForm(owner, bindingResult);
        //then
        then(service).should(atMostOnce()).save(owner);
        assertEquals(REDIRECT_OWNERS_1, result);

    }

    @Test
    void processCreationForm() {

        Owner owner = new Owner(1L, "John", "Doe");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(service.save(owner)).thenReturn(owner);
        var result = controller.processCreationForm(owner, bindingResult);
        assertEquals(REDIRECT_OWNERS_1, result);
        verify(service, atLeastOnce()).save(owner);

    }

    @Test
    void processCreationFormHasError() {
        Owner owner = new Owner(1L, "John", "Doe");
        when(bindingResult.hasErrors()).thenReturn(true);


        var result = controller.processCreationForm(owner, bindingResult);
        //then
        assertEquals(VIEWS_OWNER_CREATE_OR_UPDATE_FORM, result);
        verify(service, never()).save(owner);

    }

    @Test
    @DisplayName("ProcessFindForm :Change argument before passing it to the service")
    void processFindFormWildcard() {
        Owner owner = new Owner(1L, "John", "Doe");
        given(service.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(List.of(owner));

        String result = controller.processFindForm(owner, bindingResult, model);

        assertThat(stringArgumentCaptor.getValue()).isEqualTo("%Doe%");
    }


    @Test
    void processFindFormTest() {
        Owner owner = new Owner(1L, "John", "Doe");
        //given
        when(service.findAllByLastNameLike(anyString())).thenReturn(List.of(owner));
        //when

        String result = controller.processFindForm(owner, bindingResult, model);
        //then

        assertEquals(REDIRECT_OWNERS_1, result);
        verify(service).findAllByLastNameLike(anyString());
    }

    @Test
    void processFindFormTestOwnerLastNameNull() {
        Owner owner = new Owner(1L, "John", null);
        //given
        when(service.findAllByLastNameLike(anyString())).thenReturn(List.of());
        //when

        String result = controller.processFindForm(owner, bindingResult, model);
        //then

        assertEquals(FIND_OWNERS, result);
        verify(service).findAllByLastNameLike("%%");
    }

    @Test
    void processFindFormTestOwnerMultipleOwners() {
        Owner owner = new Owner(1L, "John", "Doe");
        Owner owner2 = new Owner(2L, "John", "Doe");

        //given
        when(service.findAllByLastNameLike(anyString())).thenReturn(List.of(owner, owner2));
        //when

        String result = controller.processFindForm(owner, bindingResult, model);
        //then

        assertEquals(OWNERS_LIST, result);
        verify(service).findAllByLastNameLike("%Doe%");
    }

}