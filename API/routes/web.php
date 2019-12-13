<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

Route::get('api/documentation/developers', function () {
    return view('documentation.developers');
})->name('developers');

Route::get('api/documentation/error', function () {
    return view('documentation.error');
})->name('error');

Route::get('api/documentation/groups', function () {
    return view('documentation.groups');
})->name('groups');

Route::get('api/documentation/levels', function () {
    return view('documentation.levels');
})->name('levels');

Route::get('api/documentation/promotions', function () {
    return view('documentation.promotions');
})->name('promotions');

Route::get('api/documentation/rewards', function () {
    return view('documentation.rewards');
})->name('rewards');