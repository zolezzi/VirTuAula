package ar.edu.unq.virtuaula.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="account_type_role")
public class Account implements Serializable{

	private static final long serialVersionUID = -4749006084233219581L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_type_id", referencedColumnName = "id")
    @JsonIgnoreProperties("accounts")
    private AccountType accountType;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    @ManyToMany
    @JoinTable(
        name = "accounts_classrooms", 
        joinColumns = @JoinColumn(
          name = "account_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "classroom_id", referencedColumnName = "id"))
    private List<Classroom> classrooms;
}
