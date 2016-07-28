
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>URL Input</title>
    <script src="js/jquery-3.0.0.js"></script>
    <link rel="stylesheet" href="js/jquery-ui-1.11.4/jquery-ui.min.css">
    <script src="js/jquery-ui-1.11.4/jquery-ui.min.js"></script>
    <style type="text/css">
      table {margin:auto}
      button {font-size: medium}
    </style>
  </head>

  <body>
  <fieldset style="border:1px solid lightgray; width: 500px; padding: 10px; border-radius: 5px; margin:auto">
    <legend>Enter URL</legend>
      <table cellpadding=5>
      <tr>
      <td>Url to fetch: </td>
      <td><input type="text" id="url-input"></td>
      </tr>
      <tr>
      <td colspan=2 align=center>
        <button onclick="loadUrlContents()">Get URL</button>
      </td>
      </tr>
      </table>
    </fieldset>

    <fieldset style="border:1px solid lightgray; width: 500px; padding: 10px; border-radius: 5px; margin:auto">
      <legend>URL Contents</legend>
      <div id="url-contents"></div>
    </fieldset>
  </body>

<script>
function loadUrlContents()
{
	var url = $("#url-input").val();
	$.get("/url-reader?url=" + url, displayUrlContents);
}
function displayUrlContents(content)
{
	$("#url-contents").html(content);
}
</script>
</html>
