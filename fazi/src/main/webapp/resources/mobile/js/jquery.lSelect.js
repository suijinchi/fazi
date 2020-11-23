/*
* 
 * 
 * 
 * 
 * JavaScript - lSelect
 * 
 */

(function($) {
	jQuery.fn.extend({
		lSelect: function(options) {
			var settings = {
				choose: "请选择...",
				emptyValue: "",
				cssStyle: {},
				url: null,
				type: "GET",
				className:""
			};
			$.extend(settings, options);
			
			var cache = {};
			return this.each(function() {
				var $input = $(this);
				var id = $input.val();
				var nowId = $input.attr("nowId");
				var treePath = $input.attr("treePath");
				var selectName = $input.attr("name") + "_select";
				
				if (treePath != null && treePath != "") {
					var ids = (treePath + id + ",").split(",");
					var $position = $input;
					for (var i = 1; i < ids.length; i ++) {
						$position = addSelect($position, ids[i - 1], ids[i]);
					}
				} else {
					addSelect($input, null, null);
				}
				
				
				function addSelect($position, parentId, currentId) {
					$position.nextAll("select[name=" + selectName + "]").remove();
					if ($position.is("select") && (parentId == null || parentId == "")) {
						return false;
					}
					if (cache[parentId] == null) {
						$.ajax({
							url: settings.url,
							type: settings.type,
							data: parentId != null ? {parentId: parentId} : null,
							dataType: "json",
							cache: false,
							async: false,
							success: function(data) {
								cache[parentId] = data;
							}
						});
					}
					var data = cache[parentId];
					var d = JSON.parse(JSON.stringify(data));
					delete d.area;
					if ($.isEmptyObject(d)) {
						return false;
					}
					var select = '<select class="'+settings.className+'" name="' + selectName + '">';
					if (settings.emptyValue != null && settings.choose != null) {
						select += '<option class="'+settings.className+'" value="' + settings.emptyValue + '">' + settings.choose + '</option>';
					}
					
					
					var select = '<select class="'+settings.className+'" name="' + selectName + '">';
					if (settings.emptyValue != null && settings.choose != null) {
						var choose = "";
						if(data.area=="p"){
							choose = "省";
						}else if(data.area=="c"){
							choose = "市";
						}else if(data.area=="d"){
							choose = "区";
						}
						select += '<option class="'+settings.className+'" value="' + settings.emptyValue + '">' + choose + '</option>';
					}
					
					$.each(data, function(value, name) {
						if(value=="area"){
							return;
						}
						if(value == currentId) {
							select += '<option value="' + value + '" selected="selected">' + name + '</option>';
						} else if(value==nowId){
							select += '<option value="' + value + '" selected="selected">' + name + '</option>';
						}else {
							select += '<option value="' + value + '">' + name + '</option>';
						}
					});
					select += '</select>';
					return $(select).css(settings.cssStyle).insertAfter($position).bind("change", function() {
						var $this = $(this);
						if ($this.val() == "") {
							var $prev = $this.prev("select[name=" + selectName + "]");
							if ($prev.size() > 0) {
								$input.val($prev.val());
							} else {
								$input.val(settings.emptyValue);
							}
						} else {
							$input.val($this.val());
						}
						addSelect($this, $this.val(), null);
					});
				}
			});
			
		}
	});
})(jQuery);