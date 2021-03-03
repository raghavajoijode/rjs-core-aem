(function (window, $, Granite) {
    'use strict';
    console.log('I am test')
    $(window).adaptTo('foundation-registry').register("foundation.validation.validator", {
		selector: '[data-validation="testv"]', // add parameter to dialog field validation
        validate: function(el) { 
			var value = el.value;
            if(value.length < 5) {
				return Granite.I18n.get('The field must match the pattern "{0}".', value.length);
            } else {
                return;
            }
        },
        /*show: function(element, message, ctx) {
            $(element).adaptTo("foundation-field").setInvalid(true);
            ctx.next();
        },
        clear: function(element, ctx) {
            $(element).adaptTo("foundation-field").setInvalid(false);
            ctx.next();
        }*/
    });
}(window, jQuery, Granite));

// Sample file for AEM touch dialog Validation
// adaptTo('foundation-registry')
// .register("foundation.validation.validator"
// extraClientlibs: ["category1","category2"]
