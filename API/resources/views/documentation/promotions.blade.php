<html>

<head>
    <meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Css -->
    <link href="https://e-cdns-files.dzcdn.net/cache/css/sass_c/deezer-legacy.75b56f996efc8597914d.css" rel="stylesheet"
        type="text/css">
    <link href="https://e-cdns-files.dzcdn.net/cache/css/sass_c/app-developers.e972a71e4100629c6936.css"
        rel="stylesheet" type="text/css">

</head>

<body>
    <div id="dz-root"></div>

    <div id="developers" class="container" style="margin-top:70px;">
        <div class="row">
            <div id="menu_left" class="span3">
                <div class="well">
                    <ul class="nav nav-list">
                        <li class="nav-header">API Objects</li>
                        <li id="simpleapi_menuitem_level">
                            <a href="{{ route('levels') }}">Level</a>
                        </li>
                        <li id="simpleapi_menuitem_reward">
                            <a href="{{ route('rewards') }}">Reward</a>
                        </li>
                        <li id="simpleapi_menuitem_users">
                            <a href="{{ route('developers') }}">Developer</a>
                        </li>
                        <li id="simpleapi_menuitem_promotion">
                            <a href="{{ route('promotions') }}" class="active">Year Group</a>
                        </li>
                        <li id="simpleapi_menuitem_group">
                            <a href="{{ route('groups') }}">Group</a>
                        </li>
                        <li class="nav-header">API Errors</li>
                        <li id="simpleapi_menuitem_group">
                            <a href="{{ route('error') }}">API errors</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div id="content" class="span9">


                <!-- HTML EDITABLE -->
                <h1>Year Group</h1>
                <div class="tab-content">

                    <div class="tab-pane active" id="tab_infos">
                        <p class="lead">
                            A year group object </p>
                        <h2>Examples</h2>
                        <p>
                            <a href="" title="" class="">api/promotions</a><br></p>
                            <a href="" title="" class="">api/promotions/35</a><br></p>



                        <h2>Fields</h2>
                        <table class="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th class="col1">Name</th>
                                    <th class="col2">Description</th>
                                    <th class="col3">Type</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td style="padding-left: 8px">id</td>
                                    <td>The promotion's id.</td>
                                    <td>int</td>
                                </tr>
                                <tr>
                                    <td style="padding-left: 8px">name</td>
                                    <td>The promotion's name.</td>
                                    <td>string</td>
                                </tr>  
                                <tr>
                                    <td style="padding-left: 8px">date_start</td>
                                    <td>The promotion's date start.</td>
                                    <td>date</td>
                                </tr>
                                <tr>
                                    <td style="padding-left: 8px">date_end</td>
                                    <td>The promotion's date end.</td>
                                    <td>date</td>
                                </tr>
                                <tr>
                                    <td style="padding-left: 8px">developpers</td>
                                    <td>list <a href="developpers.html" title="developpers" class="general">developpers</a> object
                                        containing : id, name, firstname, mail, password, picture, xp
                                    </td>
                                    <td>object</td>
                                </tr>         
                            </tbody>
                        </table>

                        <br>
                    </div>              
            </div>

        </div>

</html>