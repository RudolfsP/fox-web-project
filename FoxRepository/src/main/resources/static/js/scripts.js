	function changeBodyBg(backGroundColor, navbarColor) {
	
		//document.body.style.background = color;
		setCookie("color", backGroundColor, 365);
		setCookie("navbar", navbarColor, 365);
		alert("New background succesfully applied");
		//document.getElementById(id);
	}
	
	//new navbar function
//	function changeNavbarBg(color) {
//		setCookie("navbar", color, 365);
//		alert("Navbar color changed");
//	}
	
	function changeBc(color) {
		document.body.style.background = color;
	}
	
	function changeNav(color) {
		document.getElementById("navbar").style.background = color;
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
	
	//new navbar function
	function changeNavbarByCookie() {
		//alert(getCookie("color"));
		var value = getCookie("navbar");
		//console.log(value);
		document.getElementById("navbar").style.background = value;
		//document.getElementById("")
	}
		
	function findFox() {
		document.getElementById("body").onload = changeBackgroundByCookie();
		document.getElementById("navbar").onload = changeNavbarByCookie();
	}
	
	function myFunction() {
		
		if (frm1.fname.value === "") {
			window.open("/"
					+ frm1.fname.value, "_self")
		}
		
		else {
			window.open("/find/"
					+ frm1.fname.value, "_self")
		}
		
	}
	
	