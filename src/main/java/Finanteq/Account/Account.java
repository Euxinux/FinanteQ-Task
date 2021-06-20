package Finanteq.Account;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table(name = "account")
@Getter
@Setter
public class Account {

    @Id
    private int id;
    private String name;
    private double pln;
    private double eur;
    private double usd;
    private double gbp;

}
