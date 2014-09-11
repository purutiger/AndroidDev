$(document).ready(function () {
	if(window.chrome) {
		$('.banner li').css('background-size', '100% 100%');
	}

	if ($.fn.unslider) {
		$('.banner').unslider({
			fluid: true,
			dots: true,
			speed: 500
		});
	}
	if ($.fn.bxSlider) {
		$('.work-slider').bxSlider({
			slideWidth: 278,
			minSlides: 2,
			maxSlides: 5,
			slideMargin: 10
		});
		$('.client-slider').bxSlider({
			slideWidth: 100,
			minSlides: 3,
			maxSlides: 10,
			slideMargin:27
		});
	}

	/*usual isotope setup*/
	var $container = $('#gallery_container'),
	    $team_container = $('#team_container'),
              $filters = $("#filters a"),
	      $team_filters = $("#team_filters a");
        
	if ($.fn.isotope) {
		$container.imagesLoaded( function(){
			$container.isotope({
				itemSelector : '.photo',
				masonry: {
					columnWidth: 280
				}
			});
		});

		// filter items when filter link is clicked
		$filters.click(function() {
			$filters.removeClass("active");
			$(this).addClass("active");
			var selector = $(this).data('filter');
			$container.isotope({ filter: selector });
			return false;
		});
		$team_container.imagesLoaded( function(){
			$team_container.isotope({
				itemSelector : '.photo',
				masonry: {
					columnWidth: 162
				}
			});
		});

		// filter items when filter link is clicked
		$team_filters.click(function() {
			$team_filters.removeClass("active");
			$(this).addClass("active");
			var selector = $(this).data('filter');
			$team_container.isotope({ filter: selector });
			return false;
		});
	}

	$('#faq LI h4').click(function(e) {
		e.preventDefault(); // disable text selection
		$(this).parent().find('p').slideToggle();
		return false; // disable text selection
	});

	$('#search-faq').keyup(function(e) {
		var s = $(this).val().trim();
		if (s === '') {
			$('#result LI').show();
			return true;
		}
		$('#result LI:not(:contains(' + s + '))').hide();
		$('#result LI:contains(' + s + ')').show();
		return true;
	});
	if ($.fn.sharrre) {
		$('#twitter').sharrre({
			share: {
				twitter: true
			},
			enableHover: false,
			enableTracking: true,
			buttons: { twitter: {via: 'anujkkk'}},
			click: function(api, options){
			api.simulateClick();
			api.openPopup('twitter');
			}
		});
		$('#facebook').sharrre({
			share: {
				facebook: true
			},
			enableHover: false,
			enableTracking: true,
			click: function(api, options){
			api.simulateClick();
			api.openPopup('facebook');
			}
		});
		$('#googleplus').sharrre({
			share: {
				googlePlus: true
			},
			enableHover: false,
			enableTracking: true,
			click: function(api, options){
			api.simulateClick();
			api.openPopup('googlePlus');
		}
		});
	}
	if ($.fn.magnificPopup) {
		$('.image-link').magnificPopup({type:'image'});
	}
});
