// factory.js

var OptionTypeBlinechartChart = AWF.OptionUtil.createOptionType("datasource", {
	type: "datasource",
	parts: [{name: "header"}, {name: "layers"}],
	preferredPartitionSizes: [{parts: ["header"]}, {parts: ["layers"]}],
	layersToRequest : ["values", "flags", "annotations", "tooltips"],
});

var widgetType = "blinechart";


AWF.Bus.subscribe({
	onDecorateElement: function(elQ, type) {
		if (type === widgetType) {
			elQ.aimms_blinechart();
		}
	},
	onInitializeTags: function(elQ, type) {
	if (type === widgetType) {
		elQ.awf.tags(["multidimensional", "complex widget", "contents property", "pivotable contents property",
			"chart", "blinechart", "fullscreenable", "data-presentation", "placeable-widget-container", "placeable"], 'add');
	}
},
	onInitializeOptionTypes: function(elQ, type) {
		if (type === widgetType) {
			AWF.OptionTypeCollector.addOptionType(elQ,"contents",OptionTypeBlinechartChart);
			AWF.OptionTypeCollector.addOptionType(elQ,"contents.partition","partitionInfo");
		}
	},
	onCollectTypes: function(collectedTypes, contextElQ) {
		if (!contextElQ || contextElQ.awf.tags("data-presentation") || contextElQ.awf.tags("placeable-widget-container")) {
			collectedTypes.push(widgetType);
		}
	},
});
