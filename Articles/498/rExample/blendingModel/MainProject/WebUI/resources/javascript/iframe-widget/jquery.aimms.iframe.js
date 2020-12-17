// jquery.aimms.iframe.js

jQuery.widget('ui.aimms_iframe_widget', AWF.Widget.create({
    _create: function() {
	
/* 	    var iframe_html = '<iframe src="http://public.tableausoftware.com/views/public_exercise/Dashboard1?:showVizHome=no&:embed=true" ></iframe>'; */
		
    },
	setContents: function(iframeCode) {
        this.element
            .find('.awf-dock.center')
            .html(iframeCode)
        ;		
	},
	onResolvedOptionChanged: function(optionName, optionValue) {
        var widget = this;

        if (optionName === "code") {
			var iframeCode = optionValue || "<div>No iframe code provided</div>";

			widget.setContents(iframeCode);
        }
    },
}));
        