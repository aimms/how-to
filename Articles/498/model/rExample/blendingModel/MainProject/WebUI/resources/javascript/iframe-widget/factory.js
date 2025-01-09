AWF.Bus.subscribe({
    onCollectTypes: function(collectedTypes, contextElQ) {
        if(!contextElQ || contextElQ.awf.tags("placeable-widget-container")) {
            collectedTypes.push("iframe");
        }
    },

	onInitializeTags: function(elQ, type) {
        if (type === "iframe") {
            elQ.awf.tags(["placeable"], 'add');
        }
    },
	
    onDecorateElement: function(elQ, type) {
        if(type === "iframe") {
            elQ.aimms_iframe_widget();
        }
    },
	
	onInitializeOptionTypes: function(elQ, type) {
        if (type === "iframe") {
            elQ.awf.optionTypes("code", AWF.OptionUtil.createOptionType("string"));
        }
    },
});
     