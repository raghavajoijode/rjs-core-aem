// TODO: 
// 1. get max date offset from data (granite:data) variable - max-date-offset
// 2. get Error message from data variable - max-date-validation-message
// 3. get id constant

(function ($, window, document, Granite) {
	const currentDate = new Date();
    const MAX_ALLOWED_DAYS = 180;
	const DATE_PICKER_ID = 'activation-date';
	const maxDate = getMaxDate(MAX_ALLOWED_DAYS);

    $(document).on("dialog-ready", function() {
        const datepicker = document.getElementById(DATE_PICKER_ID);
        datepicker.max = maxDate;
    });
	
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
	
	getMaxDate = (days) => {
		return new Date(Date.now() + days * 24 * 60 * 60 * 1000);
	}
	
}(jQuery, window, document, Granite));