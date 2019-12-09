<html>

<head>
    <meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

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
                            <a href="{{ route('rewards') }}" class="active">Reward</a>
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
                            <a href="{{ route('error') }}">API errors</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div id="content" class="span9">


                <!-- HTML EDITABLE -->
                <h1>Reward</h1>
                <div class="tab-content">

                    <div class="tab-pane active" id="tab_infos">
                        <p class="lead">
                            A reward object </p>
                        <h2>Examples</h2>
                        <p>
                            <a href="" title="" class="">api/rewards</a><br></p>
                            <a href="" title="" class="">api/rewards/152</a><br></p>



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
                                    <td>The reward's id.</td>
                                    <td>int</td>
                                </tr>
                                <tr>
                                    <td style="padding-left: 8px">label</td>
                                    <td>The reward's label.</td>
                                    <td>string</td>
                                </tr>
                                <tr>
                                    <td style="padding-left: 8px">cost_xp</td>
                                    <td>The reward's xp cost.</td>
                                    <td>int</td>
                                </tr>
                                <tr>
                                    <td style="padding-left: 8px">nb_available</td>
                                    <td>Number of times the reward is available.</td>
                                    <td>int</td>
                                </tr>
                                <tr>
                                    <td style="padding-left: 8px">description</td>
                                    <td>The reward's description.</td>
                                    <td>string</td>
                                </tr>          
                                <tr>
                                    <td style="padding-left: 8px">users</td>
                                    <td>list <a href="developpers.html" title="users" class="general">developpers</a> object
                                        containing : id, name, firstname, mail, password, picture, xp, id_promo
                                        + date_obtention
                                    </td>
                                    <td>object</td>
                                </tr>                     
                                <tr>
                                    <td style="padding-left: 8px">level</td>
                                    <td><a href="levels.html" title="level" class="general">level</a> object
                                        containing : id, label, description, cost_xp, required_xp, skin</td>
                                    <td>object</td>
                                </tr>
                                <tr>
                                    <td style="padding-left: 8px">skin</td>
                                    <td>URL of reward's picture.</td>
                                    <td>string</td>
                                </tr>
                            </tbody>
                        </table>

                        <br>
                    </div>              
            </div>

        </div>



</html>