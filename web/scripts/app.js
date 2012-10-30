/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var App = {
    initPersonContact: function() {
        
        $('#fname, #lname').blur(function() {
            checkName();
        });
        
        function checkName() {
            var fname = jQuery.trim($('#fname').val());
            var lname = jQuery.trim($('#lname').val());
            
            //console.log(val);
            if(fname == '' && lname == '') {
                enableID();
            } else {
                disableID();
            }
        }
        
        function disableID() {
           $('#ID').val('').attr('disabled', true); 
        }
        function enableID() {
           $('#ID').attr('disabled', false); 
        }
        
        checkName();
    },
    
    addContactInfo: function(memberId) {
        $('#member_id').val(memberId);
        $('#add-contact-info-wrapper').show();
    }
};

