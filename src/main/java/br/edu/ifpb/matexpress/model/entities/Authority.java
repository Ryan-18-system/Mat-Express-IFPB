package br.edu.ifpb.matexpress.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_authorities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
    @EmbeddedId
    private AuthorityId id;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private User username;

    @Column(name = "authority", insertable = false, updatable = false)
    private String authority;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthorityId implements Serializable {
        private String username;
        private String authority;
    }
}
