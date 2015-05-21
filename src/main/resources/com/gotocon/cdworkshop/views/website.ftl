<!DOCTYPE html>
<!--[if IEMobile 7 ]>    <html class="no-js iem7"> <![endif]-->
<!--[if (gt IEMobile 7)|!(IEMobile)]><!-->
<html class="no-js" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html"
      xmlns="http://www.w3.org/1999/html"> <!--<![endif]-->
<head>
    <title>Example Html Page</title>
    <meta charset="ISO-8859-1">
    <meta name="description" content="">
    <meta name="HandheldFriendly" content="True">
    <meta name="MobileOptimized" content="320">
    <meta http-equiv="cleartype" content="on">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <meta name="viewport" content="initial-scale=1.0, width=100%, maximum-scale=1, user-scalable=0">

    <link rel="stylesheet" href="/assets/css/panel_hover.css" media="screen">
</head>
<body>
    <div id="container">
        <div class="panel"><div class="hover"> </div></div>
        <div class="panel"><div class="hover"> </div></div>
        <div class="panel"><div class="hover"> </div></div>
        <div class="panel"><div class="hover"> </div></div>
        <div class="panel"><div class="hover"> </div></div>
        <div class="panel"><div class="hover"> </div></div>
        <div class="panel"><div class="hover"> </div></div>
        <div class="panel"><div class="hover"> </div></div>

        <#list templateData.fragments as fragment>
            <div class="panel">
                <div class="hover">
                    <dl>
                        <dt>Author:</dt><dd>${fragment.author}</dd>
                        <dt>Comment:</dt><dd>${fragment.comment}</dd>
                        <dt>Fragment:</dt><dd>${fragment.htmlPayload}</dd>
                    </dl>
                </div>
            </div>
        </#list>
    </div>
</body>
</html>