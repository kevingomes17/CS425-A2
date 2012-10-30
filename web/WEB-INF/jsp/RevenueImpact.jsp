<%-- 
    Document   : RevenueImpact
    Created on : Oct 9, 2012, 3:43:51 AM
    Author     : kevingomes17
--%>

<form id="revenue-impact" method="POST">
    <div>
        Discount (in %): <input type="text" id="discount" name="discount" value="${discount}"></input> (Example: 50)
    </div>
    <div>
        Age Group: 
        <select id="age-group" name="age_group">
            <option value="30">greater than 30 years old</option>
            <option value="40">greater than 40 years old</option>
            <option value="50">greater than 50 years old</option>
            <option value="60">greater than 60 years old</option>
            <option value="70">greater than 70 years old</option>
        </select>
    </div>
    <div>
        <input type="submit" value="Submit"></input>        
    </div>
    
    <br/><br/>
    <div>
        ${message}        
    </div>
</form>
<script type="text/javascript">
        $(function() {
            $('#age-group').val('${age_group}');
        });
        
</script>
