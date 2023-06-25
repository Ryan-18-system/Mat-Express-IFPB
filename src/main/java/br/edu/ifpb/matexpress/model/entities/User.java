package br.edu.ifpb.matexpress.model.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id

    private String username;
    private String password;
    private Boolean enabled = true;

    @OneToMany(mappedBy = "username", cascade = CascadeType.ALL)
    @ToString.Exclude
    List<Authority> authorities;
}