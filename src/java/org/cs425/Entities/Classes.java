/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cs425.Entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kevingomes17
 */
@Entity
@Table(name = "CLASSES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Classes.findAll", query = "SELECT c FROM Classes c"),
    @NamedQuery(name = "Classes.findById", query = "SELECT c FROM Classes c WHERE c.id = :id"),
    @NamedQuery(name = "Classes.findByTitle", query = "SELECT c FROM Classes c WHERE c.title = :title"),
    @NamedQuery(name = "Classes.findBySeason", query = "SELECT c FROM Classes c WHERE c.season = :season"),
    @NamedQuery(name = "Classes.findByType", query = "SELECT c FROM Classes c WHERE c.type.type = :type"),
    @NamedQuery(name = "Classes.findByYear", query = "SELECT c FROM Classes c WHERE c.year = :year")})
public class Classes implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SEASON")
    private String season;
    @Basic(optional = false)
    @NotNull
    @Column(name = "YEAR")
    private BigInteger year;
    @JoinColumn(name = "TYPE", referencedColumnName = "TYPE")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Type type;
    @JoinColumn(name = "INSTRUCTOR", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Instructor instructor;

    public Classes() {
    }

    public Classes(BigDecimal id) {
        this.id = id;
    }

    public Classes(BigDecimal id, String title, String season, BigInteger year) {
        this.id = id;
        this.title = title;
        this.season = season;
        this.year = year;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public BigInteger getYear() {
        return year;
    }

    public void setYear(BigInteger year) {
        this.year = year;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Classes)) {
            return false;
        }
        Classes other = (Classes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Classes[ id=" + id + " ]";
    }
    
}
