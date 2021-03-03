// TODO: 
// 1. get max date offset from data (granite:data) variable - max-date-offset
// 2. get Error message from data variable - max-date-validation-message
// 3. get id constant

(function ($, window, document, Granite) {
	var currentDate = new Date();
    var maxDate = new Date();
    maxDate.setDate(maxDate.getDate() + 5);

    $(document).on("dialog-ready", function() {
        const datepicker = document.querySelector('#activation-date');
        setMaxDate(datepicker, maxDate);
    });
	
	// TODO:
    function setMaxDate(el, date) {
    	el.max = date;
    }
	
	// TODO: depricated - Update with foundation-validation
    $.validator.register({
        selector: '#activation-date',
        validate: function(el) {
			let enteredDate= new Date(el.val());
            if (enteredDate > maxDate) {
                return "Error: Message";
            }
        }
    });
}(jQuery, window, document, Granite));