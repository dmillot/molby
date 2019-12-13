<html>

<head>
    <meta charset="utf-8">
    <title>Deezer for developers</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="https://e-cdns-files.dzcdn.net/cache/css/sass_c/deezer-legacy.0ad032f2d55f13474d98.css" rel="stylesheet"
        type="text/css">
    <link href="https://e-cdns-files.dzcdn.net/cache/css/sass_c/app-developers.87ab057dec9647a57531.css"
        rel="stylesheet" type="text/css">

</head>

<body>
    <div id="developers" class="container" style="margin-top:70px;">
        <div class="row">
            <div id="menu_left" class="span3">
                <div class="well">
                <ul class="nav nav-list">
                        <li class="nav-header">API Objects</li>
                        <li id="simpleapi_menuitem_level">
                            <a href="{{ route('levels') }}" >Level</a>
                        </li>
                        <li id="simpleapi_menuitem_reward">
                            <a href="{{ route('rewards') }}">Reward</a>
                        </li>
                        <li id="simpleapi_menuitem_users">
                            <a href="{{ route('developers') }}">Developer</a>
                        </li>
                        <li id="simpleapi_menuitem_promotion">
                            <a href="{{ route('promotions') }}">Year Group</a>
                        </li>
                        <li id="simpleapi_menuitem_group">
                            <a href="{{ route('groups') }}">Group</a>
                        </li>
                        <li class="nav-header">API Errors</li>
                        <li id="simpleapi_menuitem_group">
                            <a href="{{ route('error') }}" class="active">API errors</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div id="content" class="span9">



                <h1>API errors</h1>

                <p class="lead">
                    Molby API returns some error codes if your request failed. Here is the list of all codes and their
                    description you can encounter.
                </p>

                <table class="table table-bordered table-striped">

                    <thead>
                        <tr>
                            <th class="col1">Constant</th>
                            <th class="">Type</th>
                            <th class="">Code</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>PARAMETER_MISSING</td>
                            <td>MissingParameterException</td>
                            <td>501</td>
                        </tr>
                        <tr>
                            <td>DATA_NOT_FOUND</td>
                            <td>DataException</td>
                            <td>800</td>
                        </tr>
                        <tr>
                            <td>INVALID_PARAMETER</td>
                            <td>InvalidParameter</td>
                            <td>900</td>
                        </tr>
                        <tr>
                            <td>DUPLICATE_ENTRY</td>
                            <td>DuplicateEntry</td>
                            <td>901</td>
                        </tr>
                    </tbody>
                </table> <!-- HTML EDITABLE -->

            </div>

        </div>
    </div>
 
</body>

</html>