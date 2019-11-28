// This is the combined line and barchart example. It requires two identifiers, the first one being the line data, the
// second being the bar data. Any further data is ignored.

// This version contains a single y axis

// colors are defined in javascript so not dependent on ordinals. To change the colors, update the following values:
var BAR_COLOR = '#93c262';
var LINE_COLOR = '#6096ce';
var GRID_COLOR = '#d9d9d9';

// plotly does not feature an autosizing chart depending on the side of the x-axis header, so you might need to augment this:
// l = left, r = right, t = top, b = bottom
var CHART_MARGIN = {
  l: 40,
  r: 40,
  t: 10,
  b: 45
};

function cloneInstance(o) {
	return Object.assign(Object.create(Object.getPrototypeOf(o)), o);
}

jQuery.widget('ui.aimms_blinechart', AWF.Widget.create({
    _create: function() {
      var widget = this;

      widget.container = $('<div>');
      widget.element.find('.awf-dock.center').append(widget.container);
      widget.element.on('elementresize', function() {
        widget.parts && widget.render(widget.parts);
      });
    },

    onResolvedOptionChanged: function(optionName, value) {
      var widget = this;

      if (optionName === 'contents') {
        if (value) {
          var ranges = value.values.dimension.times(function(dim) {
            return { start: 0, end: value.values.size(dim) };
          });

          value.requestDataBlocks(ranges, function onReady(layeredDataBlocks) {
            widget.parts = {
              values: _.merge(cloneInstance(value.values), layeredDataBlocks.values),
              header: _.merge(cloneInstance(value.header), layeredDataBlocks.header),
              layers: _.merge(cloneInstance(value.layers), layeredDataBlocks.layers),
            };
            widget.render(widget.parts);
          });
        }
      }
    },
    // Blinechart requires the <IDENTIFIER-SET> to be in layers.
    postParsedOptionChanged: function(optionName, value, context) {
      var self = 'BLINE_CHART';
      var identifierSet = '<IDENTIFIER-SET>';

      if (context !== self) {
        if (optionName === 'contents.partition') {
          var widget = this;

          var removeIDS = function(indexName) {
            return indexName !== identifierSet;
          };

          if (value) {
            var newParsedValue = $.extend({}, value);
            if (!newParsedValue.layers || newParsedValue.layers.last() !== identifierSet) {
              newParsedValue.header = (newParsedValue.header || []).filter(removeIDS);
              newParsedValue.layers = (newParsedValue.layers || []).filter(removeIDS).append(identifierSet);
              widget.element.awf.parsedOptions(optionName, newParsedValue, self);
            }
          }
        }
      }
    },
		// This function, makes sure that the above code is always run, even in the case of a default partition
		// (i.e. no explicit partition set by the user). Normally, in AWF, there is no parsed option set in that case.
		_assertThatThereIsAParsedPartitionOptionSet: function() {
			var widget = this;
			var parsedPartitionOption = widget.element.awf.parsedOptions('contents.partition');
			var resolvedPartitionOption = widget.element.awf.resolvedOptions('contents.partition');
			if(!parsedPartitionOption && resolvedPartitionOption) {
				widget.element.awf.parsedOptions('contents.partition', resolvedPartitionOption);
			}
		},

    getxAxisArray: function(header) {
      return d3.range(0, header.getNumRows()).map(function(item, index) {
        var content = '';
        for (var i = 0; i < header.getNumCols(); i++) {
          content += i !== 0 ? ', ' : '';
          content += header.getLayer('values').get(index, i);
        }
        return content;
      });
    },

    render: function(parts) {
      var widget = this;
      var values = parts.values.getLayer('values');
      var header = parts.header;
      var layers = parts.layers;
      var xAxis = widget.getxAxisArray(header);

      var line = {
        name: layers.getLayer('values').get(0),
        x: xAxis,
        y: xAxis.map(function(item, index) {
          return values.get(index, 0);
        }),
        type: 'scatter',
        marker: {
          color: LINE_COLOR
        }
      };

      var bar = {
        name: layers.getLayer('values').get(1),
        x: xAxis,
        y: xAxis.map(function(item, index) {
          return values.get(index, 1);
        }),
        type: 'bar',
        marker: {
          color: BAR_COLOR
        }
      };
	  
	  
      var layout = {
        margin: CHART_MARGIN,
        font: {
          size: 10,
          color: '#999',
          family: 'Roboto',
        },
        xaxis: {
          fixedrange: true,
		  type: 'category',
          tickangle: -45,
        },
        yaxis: {
          fixedrange: true,
          gridcolor: GRID_COLOR,
          anchor: 'x',
          tickfont: { color: '#999', size: 12 },
        },
        hovermode: 'closest',
        showlegend: false,
        legend: { orientation: 'h' }
      };

      var settings = {
        scrollZoom: false,
        displayModeBar: false
      };

      widget.container.empty();
      Plotly.newPlot(widget.container.get(0), [line, bar], layout, settings);

      widget._assertThatThereIsAParsedPartitionOptionSet();
    }
  })
);
