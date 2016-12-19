package com.github.serezhka.vkdump.dao.entity;

import javax.persistence.*;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractEntity {

    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
