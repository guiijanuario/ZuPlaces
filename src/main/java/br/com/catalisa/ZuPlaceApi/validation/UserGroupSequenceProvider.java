package br.com.catalisa.ZuPlaceApi.validation;

import br.com.catalisa.ZuPlaceApi.model.UserModel;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class UserGroupSequenceProvider implements DefaultGroupSequenceProvider<UserModel> {
    @Override
    public List<Class<?>> getValidationGroups(UserModel user) {
        List<Class<?>> groups = new ArrayList<>();
        groups.add(UserModel.class);
        if (user != null && user.getPersonType() != null){
            switch (user.getPersonType()){
                case PHYSICAL_PERSON:
                    groups.add(CpfOrCnpj.class);
                    break;
                case LEGAL_PERSON:
                    groups.add(CpfOrCnpj.class);
                    break;
            }
        }
        return  groups;

    }
}
