package pl.ppyrczak.busschedulesystem.registration.token;

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
