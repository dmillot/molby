<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Reward extends Model
{
    //
    protected $table = "reward";
    public $timestamps = false;

    protected $hidden = [
        'id_level'
    ];

    public function level()
    {
        return $this->belongsTo('App\Level', 'id_level');
    }

    public function developers()
    {
        return $this->hasMany('App\UserReward', 'id_reward');
    }
}
