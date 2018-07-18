<%@ page import="java.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Freemind Flash Viewer</title>
<script type="text/javascript" src="./flashobject.js"> </script>
<style type="text/css">
	html {
		height: 100%;
		overflow: hidden;
	}
	#flashcontent {
		height: 100%;
	}
	body {
		height: 100%;
		margin: 0;
		padding: 0;
		background-color: #9999ff;
	}
</style>
</head>

<body>
<div id="flashcontent">
		 Flash plugin or Javascript are turned off.
		 Activate both  and reload to view the mindmap
	</div><script type="text/javascript">
		var fo = new FlashObject("./visorFreemind.swf", "visorFreeMind", "100%", "100%", 8, "#9999ff");
		fo.addParam("quality", "high");
		fo.addParam("bgcolor", "#ffffff");
		fo.addParam("allowScriptAccess", "true");
		fo.addVariable("openUrl", "_blank");
		fo.addVariable("initLoadFile", "Main.mm");
		fo.addVariable("startCollapsedToLevel","1");
		fo.write("flashcontent");
	</script>
</body>
</html>