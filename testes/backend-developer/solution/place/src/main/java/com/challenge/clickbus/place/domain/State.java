package com.challenge.clickbus.place.domain;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ComparisonChain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.google.common.base.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class State implements Comparable<State> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String abbreviation;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State state = (State) o;
        return Objects.equal(getId(), state.getId()) &&
                Objects.equal(getName(), state.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getName());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("name", abbreviation)
                .toString();
    }

    @Override
    public int compareTo(State that) {
        return ComparisonChain.start()
                .compare(this.name, that.name)
                .compare(this.id, that.id)
                .result();
    }

}
