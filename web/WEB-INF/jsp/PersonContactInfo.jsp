<%-- 
    Document   : PersonType
    Created on : Oct 5, 2012, 11:29:22 AM
    Author     : kevingomes17
--%>
<form id="person-contact-info" method="POST">
    First name: <input type="text" id="fname" name="fname" value="${fname}" />
    Last name: <input type="text" id="lname" name="lname" value="${lname}" />
    <br/> (or) <br/>
    ID: <input type="text" id="ID" name="ID" value="${ID}" /> <br/>
    <input type="submit" value="Submit" />
</form>
    
    <br/><br/>
    <div>${message}</div>
    
    <div id="add-contact-info-wrapper" style="display: none;">
        <br/><br/>
        <fieldset>
            <legend>Enter Contact Information</legend>
        <form id="add-contact-info" name="add-contact-info" method="POST" action="/CS425-A2/search/add-contact-info.htm">
            <input type="hidden" name="member_id" id="member_id"></input>
            Phone: <input type="text" name="add_phone" id="add_phone"></input> <br/>
            Address: <input type="text" name="add_address" id="add_address"></input> <br/>
            <input type="submit" value="Save"></input>            
        </form> 
        </fieldset>
    </div>


<script type="text/javascript">
    $(function() {
        App.initPersonContact();        
    });    
</script>