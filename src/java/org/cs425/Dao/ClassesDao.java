/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cs425.Dao;

import com.sun.tools.internal.xjc.api.S2JJAXBModel;
import java.util.List;
//import javax.management.j2ee.statistics.JDBCConnectionPoolStats;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.cs425.Entities.*;
import java.util.HashMap;

/**
 *
 * @author kevingomes17
 */
@Service
public class ClassesDao {
    // Injected database connection:
    @PersistenceContext private EntityManager em;
    
    // Stores a new Class:
    @Transactional
    public void persist(Classes classObj) {
        em.persist(classObj);
        
    }
 
    // Retrieves all the classes:
    public List<Classes> getAllClasses() {        
        TypedQuery<Classes> query = em.createNamedQuery("Classes.findAll", Classes.class);
        List<Classes> classes = query.getResultList();
        return classes;
    }
    
    public List<Classes> getClassesByType(String classType) {        
        TypedQuery<Classes> query = em.createNamedQuery("Classes.findByType", Classes.class).setParameter("type", classType);                       
        List<Classes> classes = query.getResultList();
        return classes;
    }
    
    public List<Classes> getClassesBySemester(String season, Integer year)  {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c FROM Classes c ");
        
        StringBuilder whereClause = new StringBuilder();        
        
        if(!"".equals(season)) {            
            whereClause.append(String.format(" WHERE c.season LIKE '%s'", season));
        }
        
        if(year > 0) {
            if(whereClause.length() > 0) {
                whereClause.append(String.format(" AND c.year = %d", year));
            } else {
                whereClause.append(String.format(" WHERE c.year = %d", year));
            }
        }
                
        sb.append(whereClause.toString());
        Query query = em.createQuery(sb.toString());
        List<Classes> classes = query.getResultList();
        return classes;
    }

    public int getClassTypeRevenue(String type) {
        String sql = String.format("select sum(cost) as total from enrollment where class_id in ( select id from classes where type = '%s')", type);
        Query query = em.createNativeQuery(sql);
        Object obj = query.getSingleResult();
        if(obj != null) {
            return Integer.parseInt(obj.toString());
        } else {
            return 0;
        }
    }
    
    public List<Instructor> getInstructor(String fname, String lname) {
        TypedQuery<Instructor> query = em.createNamedQuery("Instructor.findByName", Instructor.class).setParameter("fName", fname).setParameter("lName", lname);
        List<Instructor> instructor = query.getResultList();
        return instructor;        
    }
    
    public List<Reccentermember> getRecCenterMember(String fname, String lname) {
        TypedQuery<Reccentermember> query = em.createNamedQuery("Reccentermember.findByName", Reccentermember.class).setParameter("fName", "%"+fname+"%").setParameter("lName", "%"+lname+"%");
        List<Reccentermember> member = query.getResultList();
        return member;        
    }
    
    public List<Instructor> getInstructorByID(String id) {        
        TypedQuery<Instructor> query = em.createNamedQuery("Instructor.findById", Instructor.class).setParameter("id", id);
        List<Instructor> instructor = query.getResultList();
        return instructor;
    }
    
    public List<Reccentermember> getRecCenterMemberByID(String id) {        
        TypedQuery<Reccentermember> query = em.createNamedQuery("Reccentermember.findById", Reccentermember.class).setParameter("id", Integer.parseInt(id));
        List<Reccentermember> member = query.getResultList();
        return member;
    }
    
    public float getRecCenterDiscount(Integer discount, Integer age) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT SUM(COST) sum FROM ENROLLMENT WHERE MEMBER_ID IN (");
        sb.append(String.format("select id from reccentermember where trunc(months_between(SYSDATE, DOB)/12) > %s",age));
        sb.append(")");
        
        float discountedPrice = 0;
        String sql = sb.toString();
        Query query = em.createNativeQuery(sql);
        Object obj = query.getSingleResult();
        if(obj != null) {
            int amount = Integer.parseInt(obj.toString());
            discountedPrice = (discount*amount)/100;
            return discountedPrice;
        } else {
            return discountedPrice;
        }
    }
    
    public Integer getRecCenterSum() {   
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT SUM(COST) sum FROM ENROLLMENT");
        
        String sql = sb.toString();
        Query query = em.createNativeQuery(sql);
        Object obj = query.getSingleResult();
        if(obj != null) {
            int amount = Integer.parseInt(obj.toString());
            return amount;
        } else {
            return 0;
        }
    }
    
    public HashMap<String,Object> saveContactInformation(String phone, String address) {
        Familypackage fobj = new Familypackage();
        fobj.setAddress(address);
        fobj.setPhone(phone);
        
        HashMap go = new HashMap();
        
        Boolean flag = false;
        
        try {
            //em.getTransaction().begin();
            em.persist(fobj);
            //em.getTransaction().commit();
            
            flag = true;
        } catch(Exception e) {
            flag = false;
            System.out.println("Error saving contact information: "+e.getMessage());
            e.printStackTrace();
        }
        
        go.put("flag", flag);
        go.put("familyPkgObj", fobj);
        return go;
    }
    
    public Boolean updateMemberFamilyId(Integer memberId, Familypackage familyId) {
        Reccentermember mobj = em.find(Reccentermember.class, memberId);
        
        try {
            //em.getTransaction().begin();
            mobj.setFamilyId(familyId);
            em.merge(mobj);
            //em.getTransaction().commit();
            
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
