<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::get("rewards","ApiController@rewards");
Route::get("rewards/{id}","ApiController@reward");
Route::post("rewards/{id}","ApiController@redeemreward");

Route::get("levels","ApiController@levels");
Route::get("levels/{id}","ApiController@level");
Route::post("levels/{id}","ApiController@unlock");

Route::get("groups","ApiController@groups");
Route::get("groups/{id}","ApiController@group");
Route::post("groups/{id}/points","ApiController@points");

Route::get("promotions","ApiController@promotions");
Route::get("promotions/{id}","ApiController@promotion");

Route::get("developers","ApiController@developers");
Route::get("developers/{id}","ApiController@developer");