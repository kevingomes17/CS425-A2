/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cs425.Controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.cs425.Dao.*;
import org.cs425.Entities.*;
import org.springframework.util.StringUtils;
import java.util.HashMap;

/**
 *
 * @author kevingomes17
 */
@Controller
public class SearchController {     
    @Autowired  
    private ClassesDao classesDao;  
    
    @RequestMapping(value = "index") 
    public String Index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("Filename", "index.jsp");
        model.addAttribute("PageTitle", "Welcome");
        
        return "TemplateMain";
    }
    
    @RequestMapping(value = "/class-type")
    public String ClassByType(HttpServletRequest request, HttpServletResponse response, Model model) {
       String filename = "SearchClassByType.jsp";
       model.addAttribute("PageTitle", "Search Class by Type");
       model.addAttribute("Filename", filename);
       model.addAttribute("noResults", 0);
       
       String classType = "";
       if("POST".equals(request.getMethod())) {
        classType = request.getParameter("classType");
        if(classType != null && StringUtils.hasLength(classType)) {
            List<Classes> classList = classesDao.getClassesByType(classType);
            model.addAttribute("classes", classList);
            
            if(classList.isEmpty()) {
                model.addAttribute("noResults", 1);
            }
        } else {  
            classType = "";            
        }
       }
       model.addAttribute("classType", classType);
       
       return "TemplateMain";
    }
    
    @RequestMapping(value = "/class-semester")
    public String ClassBySemester(HttpServletRequest request, HttpServletResponse response, Model model) {
       String filename = "SearchClassBySemester.jsp";
       model.addAttribute("PageTitle", "Search Class by Semester");
       model.addAttribute("Filename", filename);
       model.addAttribute("noResults", 0);
             
       int year = 0;       
       String season = "";
       String yearStr = "";
       
       if("POST".equals(request.getMethod())) {       
        season = request.getParameter("season");      
        yearStr = request.getParameter("year");
               
        if(season == null) {
            season = "";
        }
       
        if(yearStr == null || yearStr.isEmpty()) {
            year = 0;
            yearStr = "";
        } else {
            year = Integer.parseInt(yearStr);
        }
        
        List<Classes> classList = classesDao.getClassesBySemester(season, year);
        model.addAttribute("classes", classList);
        if(classList.isEmpty()) {
            model.addAttribute("noResults", 1);
        }
       }       
       
       model.addAttribute("season", season);
       model.addAttribute("year", yearStr);
       return "TemplateMain";
    }

    @RequestMapping(value = "/classtype-revenue")
    public String ClassTypeRevenue(HttpServletRequest request, HttpServletResponse response, Model model) {
        String filename = "SearchClassByType.jsp";
        model.addAttribute("PageTitle", "Search Class Type Revenue");
        model.addAttribute("Filename", filename);   
        model.addAttribute("noResults", 0);
        
        String classType = "";
        if("POST".equals(request.getMethod())) {
         classType = request.getParameter("classType");
         if(classType == null) {
             classType = "";
         }
         int revenue = classesDao.getClassTypeRevenue(classType);
         model.addAttribute("revenueMessage", String.format("Revenue: $%s",revenue));
        }
       
        model.addAttribute("classType", classType);        
        return "TemplateMain";
    } 
    
    @RequestMapping(value = "/person-type")
    public String PersonInstructorOrMember(HttpServletRequest request, HttpServletResponse response, Model model) {
        String filename = "PersonType.jsp";
        model.addAttribute("PageTitle", "Search Instructor/Center Member");
        model.addAttribute("Filename", filename);   
        model.addAttribute("noResults", 0);
        
        String fname = "", lname = "";
        if("POST".equals(request.getMethod())) {
            fname = (request.getParameter("fname") == null)?"":request.getParameter("fname");
            lname = (request.getParameter("lname") == null)?"":request.getParameter("lname");
            
            if(fname.isEmpty() && lname.isEmpty()) {
                model.addAttribute("message", "No name specified!");                
            } else {
                List<Instructor> instructor = classesDao.getInstructor(fname, lname);                
                List<Reccentermember> member = classesDao.getRecCenterMember(fname, lname);
                
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("The person [%s, %s]: ",fname, lname));
                if(instructor.size() > 0) {
                    sb.append(" IS INSTRUCTOR; ").append(" Found ("+instructor.size()+")");
                }
                if(member.size() > 0) {
                    sb.append(" IS MEMBER OF CENTER. ").append(" Found ("+member.size()+")");
                }
                if(instructor.size() == 0 && member.size() == 0) {
                    sb.append(" is not in database!");
                }
                
                model.addAttribute("message", sb.toString());
            }
        }
        
        model.addAttribute("fname", fname);
        model.addAttribute("lname", lname);
        return "TemplateMain";
    }
    
    @RequestMapping(value = "/person-contact")
    public String PersonContact(HttpServletRequest request, HttpServletResponse response, Model model) {
        String filename = "PersonContactInfo.jsp";
        model.addAttribute("PageTitle", "Search Person Contact Information");
        model.addAttribute("Filename", filename);   
        model.addAttribute("noResults", 0);
        
        String message = "";
        String fname = "", lname = "", ID = "";
        if("POST".equals(request.getMethod())) {
            fname = (request.getParameter("fname") == null)?"":request.getParameter("fname");
            lname = (request.getParameter("lname") == null)?"":request.getParameter("lname");
            ID = (request.getParameter("ID") == null)?"":request.getParameter("ID");
            
            if(fname.isEmpty() && lname.isEmpty() && ID.isEmpty()) {
                message =  "First name, Last name and ID fields can't be left blank!";
            } else {                
                if(!fname.isEmpty() || !lname.isEmpty()) {
                    //List<Instructor> instructors = classesDao.getInstructor(fname, lname);
                    List<Reccentermember> members = classesDao.getRecCenterMember(fname, lname);
                    
                    if(members.isEmpty()) {
                        message = "No Members found with the given First Name and Last Name.";
                    } else {
                        String address = "", phone = "";
                        Familypackage fpkgObj;
                        for(int i =0;i < members.size() ; i++) {
                            fpkgObj = members.get(i).getFamilyId();
                            phone = (fpkgObj == null)?"-":fpkgObj.getPhone();
                            //address = (fpkgObj == null)?"-":fpkgObj.getAddress();
                            address = (fpkgObj == null)?"<span class='pointer' onclick='App.addContactInfo(\""+members.get(i).getId()+"\")'>Add</span>":fpkgObj.getAddress();
                            message += String.format("<div>Name: %s, %s [Phone: %s] [Address: %s] </div>", members.get(i).getFName(), members.get(i).getLName(), phone, address);
                        }
                    }
                } else {
                    //List<Instructor> instructors = classesDao.getInstructorByID(ID);
                    List<Reccentermember> members = classesDao.getRecCenterMemberByID(ID);
                    
                    if(members.isEmpty()) {                        
                        message = "No Members found with the given ID.";
                    } else {
                        String address = "", phone = "";
                        Familypackage fpkgObj;
                        for(int i =0;i < members.size() ; i++) {
                            fpkgObj = members.get(i).getFamilyId();
                            phone = (fpkgObj == null)?"-":fpkgObj.getPhone();
                            //address = (fpkgObj == null)?"<span class='pointer' onclick='App.addContactInfo(\""+fpkgObj.getId()+"\")'>Add</span>":fpkgObj.getAddress();
                            address = (fpkgObj == null)?"<span class='pointer' onclick='App.addContactInfo(\""+members.get(i).getId()+"\")'>Add</span>":fpkgObj.getAddress();
                            message += String.format("<div>Name: %s, %s [Phone: %s] [Address: %s] </div>", members.get(i).getFName(), members.get(i).getLName(), phone, address);
                        }
                    }
                    
                }
            }
        }
        
        model.addAttribute("fname", fname);
        model.addAttribute("lname", lname);
        model.addAttribute("ID", ID);
        model.addAttribute("message", message);
        return "TemplateMain";
    }
    
    @RequestMapping(value = "/add-contact-info")
    public String SaveContactInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        String filename = "ContactSaveMessage.jsp";
        model.addAttribute("PageTitle", "Search Instructor/Center Member");
        model.addAttribute("Filename", filename);   
        
        String member_id = (request.getParameter("member_id") == null)?"":request.getParameter("member_id");
        String phone = (request.getParameter("add_phone") == null)?"":request.getParameter("add_phone");
        String address = (request.getParameter("add_address") == null)?"":request.getParameter("add_address");
        
        String message = "";
        Boolean flag = false;
        HashMap go;
        Familypackage familyId;
        
        if(!member_id.isEmpty() && !phone.isEmpty() && !address.isEmpty()) {
            go = classesDao.saveContactInformation(phone, address);
            flag = (Boolean) go.get("flag");
            familyId = (Familypackage) go.get("familyPkgObj");
            
            if(flag == true) {
                message = "Successfully saved contact information.";                        
            
                classesDao.updateMemberFamilyId(Integer.parseInt(member_id), familyId);
            } else {
                message = "Unable to save contact information.";
            }
        }
        
        
        model.addAttribute("message", message);
        return "TemplateMain";
    }
    
    @RequestMapping(value = "/revenue")
    public String RevenueImpact(HttpServletRequest request, HttpServletResponse response, Model model) {
        String filename = "RevenueImpact.jsp";
        model.addAttribute("PageTitle", "Calculate impact on Revenue due to discounts");
        model.addAttribute("Filename", filename);   
        model.addAttribute("noResults", 0);
        
        String message = "", discount = "", age_group = "";
        if("POST".equals(request.getMethod())) {  
            discount = (request.getParameter("discount") == null)?"":request.getParameter("discount");
            age_group = (request.getParameter("age_group") == null)?"":request.getParameter("age_group");
            
            if(discount.isEmpty() || age_group.isEmpty()) {
                message = "Discount/Age group field can't be left blank.";
            } else {
                int total = classesDao.getRecCenterSum();
                float discountAmount = classesDao.getRecCenterDiscount(Integer.parseInt(discount), Integer.parseInt(age_group));
                model.addAttribute("total", total);
                model.addAttribute("totalAfterDiscount", (total-discountAmount));
                message = String.format("Total without Discount: %d | Total after discount: %.2f", total, (total-discountAmount));
            }
        }
        
        model.addAttribute("message", message);
        model.addAttribute("discount", discount);
        model.addAttribute("age_group", age_group);
        return "TemplateMain";
    }
}
