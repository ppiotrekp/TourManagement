package pl.ppyrczak.busschedulesystem.registration.email;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        //regex to validate email
        return true ;
    }
}
