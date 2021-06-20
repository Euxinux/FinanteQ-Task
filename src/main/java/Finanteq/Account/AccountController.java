package Finanteq.Account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.math3.util.Precision;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.round;

@Component
@RestController
public class AccountController {
    @Autowired
    AccountRepository aRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Cantor cantor;

    @GetMapping("/account/{id}")
    public ResponseEntity displayUserAccount(@PathVariable("id") int id) throws JsonProcessingException {
        Optional<Account> userAccount = aRepository.findById(id);
        if (userAccount.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
            return ResponseEntity.ok(objectMapper.writeValueAsString(userAccount));
    }

    @PutMapping("/sell/PLN/{currency}/{amount}")
    public ResponseEntity sellPLN(@RequestHeader("accountId") int accountId, @PathVariable("amount") double amount,
                                        @PathVariable("currency") String currency) throws JSONException, JsonProcessingException {
        Optional<Account> account = aRepository.findById(accountId);

        if (!currencyList.contains(currency))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if (account.isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (account.get().getPln() < amount)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        else{
            Account newValuedAccount;
            newValuedAccount = account.get();


            switch (currency){
                case "EUR":
                    double course = cantor.getCourse(EUR);
                    double newValue = account.get().getEur() + ((amount/course)*0.98);
                    newValuedAccount.setEur(newValue);

                    break;
                case "USD":
                    course = cantor.getCourse(USD);
                    newValue = account.get().getUsd() + ((amount/course)*0.98);
                    newValuedAccount.setUsd(newValue);
                    break;
                case "GBP":
                    course = cantor.getCourse(GBP);
                    newValue = account.get().getUsd() + ((amount/course)*0.98);
                    newValuedAccount.setGbp(newValue);
                    break;
        }
            newValuedAccount.setPln((account.get().getPln()-amount));
            aRepository.save(newValuedAccount);
            return ResponseEntity.ok(objectMapper.writeValueAsString(newValuedAccount));
            }
        }

    @PutMapping("/buy/PLN/{currency}/{amount}")
    public ResponseEntity buyPLN(@RequestHeader("accountId") int accountId, @PathVariable("amount") double amount,
                                        @PathVariable("currency") String currency) throws JSONException, JsonProcessingException {
        Optional<Account> account = aRepository.findById(accountId);
        if (!currencyList.contains(currency))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if (account.isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Account newValuedAccount;
        newValuedAccount = account.get();

        double course = 1;
        double newValue;
            switch (currency){
                case "EUR":
                    if (amount > account.get().getEur())
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
                    else {
                         course = cantor.getCourse(EUR);
                         newValue = account.get().getEur() - amount;
                         newValuedAccount.setEur(newValue);
                        break;
                    }
                case "USD":
                    if (amount > account.get().getUsd())
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
                    else {
                         course = cantor.getCourse(USD);
                         newValue = account.get().getUsd() - amount;
                         newValuedAccount.setUsd(newValue);
                        break;
                    }
                case "GBP":
                    if (amount > account.get().getGbp())
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
                    else {
                         course = cantor.getCourse(GBP);
                         newValue = account.get().getGbp() - amount;
                        newValuedAccount.setGbp(newValue);
                        break;
                    }
            }
                if (course != 1) {
                    newValuedAccount.setPln(account.get().getPln() + amount * course * 0.98);
                    aRepository.save(newValuedAccount);
                }
            return ResponseEntity.ok(objectMapper.writeValueAsString(newValuedAccount));
        }

    @Value( "${URL.EUR}" )
    private String EUR;
    @Value( "${URL.GBP}" )
    private String GBP;
    @Value( "${URL.USD}" )
    private String USD;

    private String[] currencyArray = {"USD","EUR","GBP"};
    private List<String> currencyList = new ArrayList<>(Arrays.asList(currencyArray));
}
