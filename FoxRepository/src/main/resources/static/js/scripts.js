	function changeBodyBg(color) {
		document.body.style.background = color;
		setCookie("color", color, 365);

		//document.getElementById(id);
	}

	function setCookie(cname, cvalue, exdays) {
		var d = new Date();
		d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
		var expires = "expires=" + d.toUTCString();
		document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
	}

	function getCookie(cname) {
		var name = cname + "=";
		var decodedCookie = decodeURIComponent(document.cookie);
		var ca = decodedCookie.split(';');

		for (var i = 0; i < ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) == ' ') {
				c = c.substring(1);
			}
			if (c.indexOf(name) == 0) {
				//alert(c.substring(name.length, c.length));
				return c.substring(name.length, c.length);
			}
		}
		return "";
	}

	function changeBackgroundByCookie() {
		//alert(getCookie("color"));
		var value = getCookie("color");
		//console.log(value);
		document.body.style.background = value;

	}
	
	
	function changeColor() {
		document.getElementById("body").onload = changeBackgroundByCookie();
	}
	