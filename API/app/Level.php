<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Level extends Model
{
    protected $table = "level";
    public $timestamps = false;

    public function level()
    {
        return $this->hasMany('App\Reward', 'id_level');
    }

    public function rewards()
    {
        return $this->hasMany('App\Reward', 'id_level');
    }

    public function developers()
    {
        return $this->hasMany('App\UserLevel', 'id_level');
    }
}
