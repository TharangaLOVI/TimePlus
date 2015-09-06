$(document).ready(
		function() {
			
			$.material.init(); //bootstrap material initialize
			
			$('#dateOfBirth').bootstrapMaterialDatePicker({weekStart : 0,time : false});
			
			// tool tips show
			$('[data-tooltip="tooltip"]').tooltip();
			
			var count = 1;
			$("#float-main").click(function() {
				count += 1
				var childs = $(".child");
				if (count % 2 == 1) {
					count = 1;
					childs.fadeOut("slow");
				} else {
					childs.fadeIn("slow");
				}
				// $.notify("Access granted", { position:"left bottom" });
			});
			
			//slide show
			var images = [ '1.jpg', '2.jpg', '3.jpg' ];
			$("#slide").css(
					'background',
					'url(resources/images/' + images[images.length - 1]
							+ ') no-repeat center');
			var i = 0;
			setInterval(function() {
				var path = encodeURI('resources/images/' + images[i++]);
				$("#slide").css('background',
						'url(' + path + ') no-repeat center');
				$("#slide").css('background-size', 'auto auto');
				if (i == images.length)
					i = 0;
			}, 5000);

		});

function showModal(id) {
	$(id).modal('toggle');
}