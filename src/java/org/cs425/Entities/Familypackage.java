/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cs425.Entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kevingomes17
 */
@Entity
@Table(name = "FAMILYPACKAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Familypackage.findAll", query = "SELECT f FROM Familypackage f"),
    @NamedQuery(name = "Familypackage.findById", query = "SELECT f FROM Familypackage f WHERE f.id = :id"),
    @NamedQuery(name = "Familypackage.findByAddress", query = "SELECT f FROM Familypackage f WHERE f.address = :address"),
    @NamedQuery(name = "Familypackage.findByPhone", query = "SELECT f FROM Familypackage f WHERE f.phone = :phone")})
public class Familypackage implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ADDRESS")
    private String address;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PHONE")
    private String phone;
    @OneToMany(mappedBy = "familyId", fetch = FetchType.LAZY)
    private Collection<Reccentermember> reccentermemberCollection;

    public Familypackage() {
    }

    public Familypackage(BigDecimal id) {
        this.id = id;
    }

    public Familypackage(BigDecimal id, String address, String phone) {
        this.id = id;
        this.address = address;
        this.phone = phone;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @XmlTransient
    public Collection<Reccentermember> getReccentermemberCollection() {
        return reccentermemberCollection;
    }

    public void setReccentermemberCollection(Collection<Reccentermember> reccentermemberCollection) {
        this.reccentermemberCollection = reccentermemberCollection;
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
        if (!(object instanceof Familypackage)) {
            return false;
        }
        Familypackage other = (Familypackage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Familypackage[ id=" + id + " ]";
    }
    
}
