/* dwr/interfaces/WebLabshopProperties.js
 *
 */

labshop.getProperties = function(keys, fcallback){
	if( $.isArray(keys) ){
		WebLabshopProperties.getProperties(keys, {
			callback: function(vals){
				fcallback({isErr:false, values:vals});
			},
			errorHandler: function(ms){
				fcallback({isErr:true, errMsg:ms});
			}
		});
	}else{
		WebLabshopProperties.getProperties([keys], {
			callback: function(vals){
				fcallback({isErr:false, value:vals});
			},
			errorHandler: function(ms){
				fcallback({isErr:true, errMsg:ms});
			}
		});
	}
}