(function ($, window, document, Granite) {
    const DATE_PICKER_ID = 'pathb-val';
    const DATE_PICKER_SELECTOR = '#' + DATE_PICKER_ID;
    const MAX_DAYS_ATTRIBUTE = 'max-days';
    const ERROR_MESSAGE_ATTRIBUTE = 'error-message';
    const DEFAULT_MAX_DAYS = 180;
    const DEFAULT_ERROR_MESSAGE = 'Error: Date selected is out of range';

	const currentDate = new Date();
	let maxDate = currentDate;

    $(document).on("dialog-ready", function() {
        const $datepicker = $(DATE_PICKER_SELECTOR);
        const MAX_ALLOWED_DAYS = $datepicker.data(MAX_DAYS_ATTRIBUTE) || DEFAULT_MAX_DAYS;
		maxDate = getMaxDate(MAX_ALLOWED_DAYS);
        $datepicker[0].max = maxDate;
    });
	
    // depricated: 
    $.validator.register({
    //$(window).adaptTo('foundation-registry').register("foundation.validation.validator", {
        selector: "[name='./path']",
        validate: function(el) {
            if (!el.is(':focus')) {
				console.log('Hiii iiiiii')
                let value = el.val();
                console.log('Testing: ', value.startsWith(el.closest('.coral-Form-field').data('root-path')))
                status = (function(){
                    return $.ajax(
                        {
                            type: 'GET',
                            url: value + '.json',
                            async: false
                        }).status;
                	})();
                if (value.startsWith(el.closest('.coral-Form-field').data('root-path')) && status === "200") {
                    return null;
                } 
                else {
					return Granite.I18n.get('The field must use a path that exists.');
                }
            }


        }
    });

    function getMaxDate(days) {
        return new Date(Date.now() + days * 24 * 60 * 60 * 1000);
    }

    function data(el, attr) {
        return el.getAttribute('data-'+attr);
    }

}(jQuery, window, document, Granite));