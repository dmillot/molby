<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Developer extends Model
{
    protected $table = "user";
    public $timestamps = false;

    protected $hidden = [
        'password', 'id_year_group'
    ];

    public function promotion()
    {
        return $this->belongsTo('App\Promotion', 'id_year_group');
    }

    public function rewards()
    {
        return $this->hasMany('App\UserReward', 'id_user');
    }

    public function levels()
    {
        return $this->hasMany('App\UserLevel', 'id_user');
    }

    public function group()
    {
        return $this->hasOne('App\UserGroup', 'id_user');
    }
}
